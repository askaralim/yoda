//package com.taklip.yoda.controller;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AnonymousAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.multipart.MultipartFile;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//
//import com.taklip.yoda.model.ImageFile;
//import com.taklip.yoda.model.User;
//import com.taklip.yoda.tool.FileUploader;
//import com.taklip.yoda.tool.ImageUploader;
//import com.taklip.yoda.tool.StringPool;
//import com.taklip.yoda.util.PortalUtil;
//
///**
// * Servlet to upload and browse files.<br>
// * 
// * This servlet accepts 4 commands used to retrieve and create files and folders
// * from a server directory. The allowed commands are:
// * <ul>
// * <li>GetFolders: Retrive the list of directory under the current folder
// * <li>GetFoldersAndFiles: Retrive the list of files and directory under the
// * current folder
// * <li>CreateFolder: Create a new directory under the current folder
// * <li>FileUpload: Send a new file to the server (must be sent with a POST)
// * </ul>
// * 
// * @author Simone Chiaretta (simo@users.sourceforge.net)
// */
//
//@Controller
//@RequestMapping("/FCKeditor/editor/filemanager/browser/default/connectors/jsp/connector")
//public class ConnectorController {
//	private final Logger logger = LoggerFactory.getLogger(ConnectorController.class);
//
//	private static final String UPLOAD_FOLDER = "/uploads/";
//
////	private static String baseDir;
//	private static boolean debug = false;
//	@RequestMapping(method = RequestMethod.GET)
//	public void setup(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
////		String realBaseDir = ServletContextUtil.getServletContext().getRealPath(UPLOAD_FOLDER);
////
////		File baseFile = new File(realBaseDir);
////
////		if(!baseFile.exists()){
////			baseFile.mkdir();
////		}
//
//		String fckBaseDir = StringPool.BLANK;
//
//		if (logger.isDebugEnabled()) {
//			logger.debug("--- BEGIN DOGET ---");
//		}
//
//		try {
//			fckBaseDir = this.getBaseDir(request);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return;
//		}
//
//		response.setContentType("text/xml; charset=UTF-8");
//		response.setHeader("Cache-Control", "no-cache");
//
//		PrintWriter out = response.getWriter();
//
//		String commandStr = request.getParameter("Command");
//		String typeStr = request.getParameter("Type");
//		String currentFolderStr = request.getParameter("CurrentFolder");
//
//		String currentDirPath = fckBaseDir + currentFolderStr;
//
//		File currentDir = new File(currentDirPath);
//
//		if (!currentDir.exists()) {
//			boolean success = currentDir.mkdir();
//			System.out.println(success);
//		}
//
//		Document document = null;
//
//		try {
//			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = factory.newDocumentBuilder();
//			document = builder.newDocument();
//		} catch (ParserConfigurationException pce) {
//			pce.printStackTrace();
//		}
//
//		Node root = null;
//		try {
//			root = createCommonXml(request, document, commandStr, typeStr,
//				currentFolderStr, currentFolderStr);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		if (debug)
//			System.out.println("Command = " + commandStr);
//
//		if (commandStr.equals("GetFolders")) {
//			getFolders(currentDir, root, document);
//		}
//		else if (commandStr.equals("GetFoldersAndFiles")) {
//			getFolders(currentDir, root, document);
//			getFiles(currentDir, root, document);
//		}
//		else if (commandStr.equals("CreateFolder")) {
//			String newFolderStr = request.getParameter("NewFolderName");
//			File newFolder = new File(currentDir, newFolderStr);
//			String retValue = "110";
//
//			if (newFolder.exists()) {
//				retValue = "101";
//			}
//			else {
//				try {
//					boolean dirCreated = newFolder.mkdir();
//					if (dirCreated)
//						retValue = "0";
//					else
//						retValue = "102";
//				}
//				catch (SecurityException sex) {
//					retValue = "103";
//				}
//			}
//
//			setCreateFolderResponse(retValue, root, document);
//		}
//
//		document.getDocumentElement().normalize();
//
//		try {
//			TransformerFactory tFactory = TransformerFactory.newInstance();
//			Transformer transformer = tFactory.newTransformer();
//
//			DOMSource source = new DOMSource(document);
//
//			StreamResult result = new StreamResult(out);
//
//			transformer.transform(source, result);
//
//			if (logger.isDebugEnabled()) {
//				StreamResult dbgResult = new StreamResult(System.out);
//
//				transformer.transform(source, dbgResult);
//
//				logger.debug("--- END DOGET ---");
//			}
//
//		}
//		catch (Exception ex) {
//			ex.printStackTrace();
//		}
//
//		out.flush();
//		out.close();
//	}
//
//	/**
//	 * Manage the Post requests (FileUpload).<br>
//	 * 
//	 * The servlet accepts commands sent in the following format:<br>
//	 * connector?Command=FileUpload&Type=ResourceType&CurrentFolder=FolderPath<br>
//	 * <br>
//	 * It store the file (renaming it in case a file with the same name exists)
//	 * and then return an HTML file with a javascript command in it.
//	 * 
//	 */
//	@RequestMapping(method = RequestMethod.POST)
//	public void process(
//			@RequestParam("NewFile") MultipartFile file,
//			HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//
//		String fckBaseDir;
//		if (logger.isDebugEnabled()) {
//			logger.debug("--- BEGIN DOPOST ---");
//		}
//
//		try {
//			fckBaseDir = this.getBaseDir(request);
//		}
//		catch (Exception e) {
//			e.printStackTrace();
//			return;
//		}
//
//		response.setContentType("text/html; charset=UTF-8");
//		response.setHeader("Cache-Control", "no-cache");
//
//		PrintWriter out = response.getWriter();
//
//		String commandStr = request.getParameter("Command");
////		String typeStr = request.getParameter("Type");
//		String currentFolderStr = request.getParameter("CurrentFolder");
//
//		String currentDirPath = fckBaseDir;
//
//		if (currentFolderStr.length() > 1) {
//			currentDirPath += currentFolderStr;
//		}
//
//		if (logger.isDebugEnabled()) {
//			logger.debug(currentDirPath);
//		}
//
//		String retVal = "0";
//		String newName = StringPool.BLANK;
//
//		if (!commandStr.equals("FileUpload"))
//			retVal = "203";
//		else {
//			try {
////				String fileNameLong = file.getOriginalFilename();
//
////				fileNameLong = fileNameLong.replace('\\', '/');
//
//				ImageUploader imageUpload = new ImageUploader(null);
//
//				ImageFile uploadFile = imageUpload.uploadImage(file, currentDirPath);
//				newName = uploadFile.getFileName();
//
////				while (pathToSave.exists()) {
////					newName = nameWithoutExt + "(" + counter + ")" + "." + ext;
////					retVal = "201";
////					pathToSave = new File(currentDirPath, newName);
////					counter++;
////				}
//			}
//			catch (Exception ex) {
//				retVal = "203";
//			}
//
//		}
//
//		out.println("<script type=\"text/javascript\">");
//		out.println("window.parent.frames['frmUpload'].OnUploadCompleted(" + retVal + ",'" + newName + "');");
//		out.println("</script>");
//		out.flush();
//		out.close();
//
//		if (logger.isDebugEnabled()) {
//			logger.debug("--- END DOPOST ---");
//		}
//	}
//
//	private void setCreateFolderResponse(String retValue, Node root,
//			Document doc) {
//		Element myEl = doc.createElement("Error");
//		myEl.setAttribute("number", retValue);
//		root.appendChild(myEl);
//	}
//
//	private void getFolders(File dir, Node root, Document doc) {
//		Element folders = doc.createElement("Folders");
//		root.appendChild(folders);
//		File[] fileList = dir.listFiles();
//		for (int i = 0; i < fileList.length; ++i) {
//			if (fileList[i].isDirectory()) {
//				Element myEl = doc.createElement("Folder");
//				myEl.setAttribute("name", fileList[i].getName());
//				folders.appendChild(myEl);
//			}
//		}
//	}
//
//	private void getFiles(File dir, Node root, Document doc) {
//		Element files = doc.createElement("Files");
//
//		root.appendChild(files);
//
//		File[] fileList = dir.listFiles();
//
//		for (int i = 0; i < fileList.length; ++i) {
//			if (fileList[i].isFile()) {
//				Element myEl = doc.createElement("File");
//				myEl.setAttribute("name", fileList[i].getName());
//				myEl.setAttribute("size", "" + fileList[i].length() / 1024);
//				files.appendChild(myEl);
//			}
//		}
//	}
//
//	private Node createCommonXml(HttpServletRequest request, Document doc,
//			String commandStr, String typeStr, String currentPath,
//			String currentUrl) throws Exception {
//
//		Element root = doc.createElement("Connector");
//		doc.appendChild(root);
//		root.setAttribute("command", commandStr);
//		root.setAttribute("resourceType", typeStr);
//
//		Element myEl = doc.createElement("CurrentFolder");
//		myEl.setAttribute("path", currentPath);
//		// myEl.setAttribute("url",currentUrl);
//		myEl.setAttribute("url", this.getUrlPrefix(request) + currentUrl);
//		root.appendChild(myEl);
//
//		return root;
//
//	}
//
//	private String getBaseDir(HttpServletRequest request) throws Exception {
//		SecurityContext context = (SecurityContext)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
//
//		Authentication auth = context.getAuthentication();
//
//		User user = new User();
//
//		if (!(auth instanceof AnonymousAuthenticationToken)) {
//			user = (User)auth.getPrincipal();
//		}
//
//		String prefix = PortalUtil.getRequest().getServletContext().getRealPath(UPLOAD_FOLDER);
//
//		if (null != user) {
//			prefix = prefix.concat("/" + user.getUserId());
//		}
//
//		prefix = prefix.concat(FileUploader.UPLOAD_CONTENT_FOLDER);
//
//		File baseFile = new File(prefix);
//
//		if (!baseFile.exists())
//			baseFile.mkdirs();
//
//		return prefix;
//	}
//
//	private String getUrlPrefix(HttpServletRequest request) throws Exception {
//		SecurityContext context = (SecurityContext)request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
//
//		Authentication auth = context.getAuthentication();
//
//		User user = new User();
//
//		if (!(auth instanceof AnonymousAuthenticationToken)) {
//			user = (User)auth.getPrincipal();
//		}
//
//		String urlPrefix = PortalUtil.getRequest().getContextPath() + UPLOAD_FOLDER;
//
//		if (null != user) {
//			urlPrefix = urlPrefix.concat(StringPool.BLANK + user.getUserId());
//		}
//
//		urlPrefix = urlPrefix.concat(FileUploader.UPLOAD_CONTENT_FOLDER);
//
//		return urlPrefix;
//	}
//}