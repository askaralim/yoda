package com.yoda.fckeditor.connector;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.log4j.Logger;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.yoda.fckeditor.uploader.SimpleUploaderServlet;
import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.user.model.User;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

/**
 * Servlet to upload and browse files.<br>
 * 
 * This servlet accepts 4 commands used to retrieve and create files and folders
 * from a server directory. The allowed commands are:
 * <ul>
 * <li>GetFolders: Retrive the list of directory under the current folder
 * <li>GetFoldersAndFiles: Retrive the list of files and directory under the
 * current folder
 * <li>CreateFolder: Create a new directory under the current folder
 * <li>FileUpload: Send a new file to the server (must be sent with a POST)
 * </ul>
 * 
 * @author Simone Chiaretta (simo@users.sourceforge.net)
 */

@Controller
@RequestMapping("/FCKeditor/editor/filemanager/browser/default/connectors/jsp/connector")
public class ConnectorController {
	Logger logger = Logger.getLogger(ConnectorController.class);

	private static final String UPLOAD_FOLDER = "/uploads/";

	private static String baseDir;
	private static boolean debug = false;

	/**
	 * Initialize the servlet.<br>
	 * Retrieve from the servlet configuration the "baseDir" which is the root
	 * of the file repository:<br>
	 * If not specified the value of "/UserFiles/" will be used.
	 * 
	 */
//	public void init() throws ServletException {
//		baseDir=getInitParameter("baseDir");
//
//		if(baseDir==null)
//		baseDir="/data/";
//
//		String realBaseDir = getServletContext().getRealPath(baseDir);
//		File baseFile=new File(realBaseDir);
//
//		if(!baseFile.exists()){
//			baseFile.mkdir();
//		}
//
//	}

	/**
	 * Manage the Get requests (GetFolders, GetFoldersAndFiles, CreateFolder).<br>
	 * 
	 * The servlet accepts commands sent in the following format:<br>
	 * connector?Command=CommandName&Type=ResourceType&CurrentFolder=FolderPath<br>
	 * <br>
	 * It execute the command and then return the results to the client in XML
	 * format.
	 * 
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void setup(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String realBaseDir = ServletContextUtil.getServletContext().getRealPath(UPLOAD_FOLDER);

		File baseFile = new File(realBaseDir);

		if(!baseFile.exists()){
			baseFile.mkdir();
		}

		String fckBaseDir = StringPool.BLANK;

		if (logger.isDebugEnabled()) {
			logger.debug("--- BEGIN DOGET ---");
		}

		try {
			fckBaseDir = this.getBaseDir(request);
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}

		response.setContentType("text/xml; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out = response.getWriter();

		String commandStr = request.getParameter("Command");
		String typeStr = request.getParameter("Type");
		String currentFolderStr = request.getParameter("CurrentFolder");

		String currentDirPath = fckBaseDir + currentFolderStr;

		File currentDir = new File(currentDirPath);

		if (!currentDir.exists()) {
			boolean success = currentDir.mkdir();
			System.out.println(success);
		}

		Document document = null;

		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		}

		Node root = null;
		try {
			root = createCommonXml(request, document, commandStr, typeStr,
				currentFolderStr, currentFolderStr);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		if (debug)
			System.out.println("Command = " + commandStr);

		if (commandStr.equals("GetFolders")) {
			getFolders(currentDir, root, document);
		}
		else if (commandStr.equals("GetFoldersAndFiles")) {
			getFolders(currentDir, root, document);
			getFiles(currentDir, root, document);
		}
		else if (commandStr.equals("CreateFolder")) {
			String newFolderStr = request.getParameter("NewFolderName");
			File newFolder = new File(currentDir, newFolderStr);
			String retValue = "110";

			if (newFolder.exists()) {
				retValue = "101";
			}
			else {
				try {
					boolean dirCreated = newFolder.mkdir();
					if (dirCreated)
						retValue = "0";
					else
						retValue = "102";
				}
				catch (SecurityException sex) {
					retValue = "103";
				}
			}

			setCreateFolderResponse(retValue, root, document);
		}

		document.getDocumentElement().normalize();

		try {
			TransformerFactory tFactory = TransformerFactory.newInstance();
			Transformer transformer = tFactory.newTransformer();

			DOMSource source = new DOMSource(document);

			StreamResult result = new StreamResult(out);

			transformer.transform(source, result);

			if (logger.isDebugEnabled()) {
				StreamResult dbgResult = new StreamResult(System.out);

				transformer.transform(source, dbgResult);

				logger.debug("--- END DOGET ---");
			}

		}
		catch (Exception ex) {
			ex.printStackTrace();
		}

		out.flush();
		out.close();
	}

	/**
	 * Manage the Post requests (FileUpload).<br>
	 * 
	 * The servlet accepts commands sent in the following format:<br>
	 * connector?Command=FileUpload&Type=ResourceType&CurrentFolder=FolderPath<br>
	 * <br>
	 * It store the file (renaming it in case a file with the same name exists)
	 * and then return an HTML file with a javascript command in it.
	 * 
	 */
	@RequestMapping(method = RequestMethod.POST)
	public void process(
			@RequestParam("NewFile") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String fckBaseDir;
		if (logger.isDebugEnabled()) {
			logger.debug("--- BEGIN DOPOST ---");
		}

		try {
			fckBaseDir = this.getBaseDir(request);
		}
		catch (Exception e) {
			e.printStackTrace();
			return;
		}

		response.setContentType("text/html; charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");

		PrintWriter out = response.getWriter();

		String commandStr = request.getParameter("Command");
		String typeStr = request.getParameter("Type");
		String currentFolderStr = request.getParameter("CurrentFolder");

		String currentDirPath = fckBaseDir + currentFolderStr;

		if (logger.isDebugEnabled()) {
			logger.debug(currentDirPath);
		}

		String retVal = "0";
		String newName = "";

		if (!commandStr.equals("FileUpload"))
			retVal = "203";
		else {
//			FileItemFactory factory = new DiskFileItemFactory();
//			ServletFileUpload upload = new ServletFileUpload(factory);
//
//			upload.setSizeMax(5242880);

			try {
//				List items = upload.parseRequest(request);

//				Map fields = new HashMap();

//				Iterator iter = items.iterator();

//				while (iter.hasNext()) {
//					FileItem item = (FileItem) iter.next();
//					if (item.isFormField())
//						fields.put(item.getFieldName(), item.getString());
//					else
//						fields.put(item.getFieldName(), item);
//				}
//				FileItem uplFile = (FileItem) fields.get("NewFile");

				String fileNameLong = file.getOriginalFilename();

				fileNameLong = fileNameLong.replace('\\', '/');

				String[] pathParts = fileNameLong.split("/");
				String fileName = pathParts[pathParts.length - 1];

				String nameWithoutExt = getNameWithoutExtension(fileName);
				String ext = getExtension(fileName);

				File pathToSave = new File(currentDirPath, fileName);

				int counter = 1;

				while (pathToSave.exists()) {
					newName = nameWithoutExt + "(" + counter + ")" + "." + ext;
					retVal = "201";
					pathToSave = new File(currentDirPath, newName);
					counter++;
				}

				file.transferTo(pathToSave);
//				uplFile.write(pathToSave);
			} catch (Exception ex) {
				retVal = "203";
			}

		}

		out.println("<script type=\"text/javascript\">");
		out.println("window.parent.frames['frmUpload'].OnUploadCompleted(" + retVal + ",'" + newName + "');");
		out.println("</script>");
		out.flush();
		out.close();

		if (logger.isDebugEnabled()) {
			logger.debug("--- END DOPOST ---");
		}
	}

	private void setCreateFolderResponse(String retValue, Node root,
			Document doc) {
		Element myEl = doc.createElement("Error");
		myEl.setAttribute("number", retValue);
		root.appendChild(myEl);
	}

	private void getFolders(File dir, Node root, Document doc) {
		Element folders = doc.createElement("Folders");
		root.appendChild(folders);
		File[] fileList = dir.listFiles();
		for (int i = 0; i < fileList.length; ++i) {
			if (fileList[i].isDirectory()) {
				Element myEl = doc.createElement("Folder");
				myEl.setAttribute("name", fileList[i].getName());
				folders.appendChild(myEl);
			}
		}
	}

	private void getFiles(File dir, Node root, Document doc) {
		Element files = doc.createElement("Files");

		root.appendChild(files);

		File[] fileList = dir.listFiles();

		for (int i = 0; i < fileList.length; ++i) {
			if (fileList[i].isFile()) {
				Element myEl = doc.createElement("File");
				myEl.setAttribute("name", fileList[i].getName());
				myEl.setAttribute("size", "" + fileList[i].length() / 1024);
				files.appendChild(myEl);
			}
		}
	}

	private Node createCommonXml(HttpServletRequest request, Document doc,
			String commandStr, String typeStr, String currentPath,
			String currentUrl) throws Exception {

		Element root = doc.createElement("Connector");
		doc.appendChild(root);
		root.setAttribute("command", commandStr);
		root.setAttribute("resourceType", typeStr);

		Element myEl = doc.createElement("CurrentFolder");
		myEl.setAttribute("path", currentPath);
		// myEl.setAttribute("url",currentUrl);
		myEl.setAttribute("url", this.getUrlPrefix(request) + currentUrl);
		root.appendChild(myEl);

		return root;

	}

	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF
	 * bug #991489
	 */
	private static String getNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	/*
	 * This method was fixed after Kris Barnhoorn (kurioskronic) submitted SF
	 * bug #991489
	 */
	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	private String getBaseDir(HttpServletRequest request) throws Exception {
		SecurityContext context = (SecurityContext)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

		Authentication auth = context.getAuthentication();

		User user = new User();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			user = (User)auth.getPrincipal();
		}

		String prefix = ServletContextUtil.getServletContext().getRealPath(UPLOAD_FOLDER);

		if (Validator.isNotNull(user)) {
			prefix = prefix.concat("/" + user.getUserId());
		}

		File baseFile = new File(prefix);

		if (!baseFile.exists())
			baseFile.mkdirs();

		return prefix;
	}

	private String getUrlPrefix(HttpServletRequest request) throws Exception {
		SecurityContext context = (SecurityContext)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");

		Authentication auth = context.getAuthentication();

		User user = new User();

		if (!(auth instanceof AnonymousAuthenticationToken)) {
			user = (User)auth.getPrincipal();
		}

		String urlPrefix = ServletContextUtil.getContextPath() + UPLOAD_FOLDER;

		if (Validator.isNotNull(user)) {
			urlPrefix = urlPrefix.concat("" + user.getUserId());
		}

		return urlPrefix;
	}
}