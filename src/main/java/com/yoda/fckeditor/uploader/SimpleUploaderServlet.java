package com.yoda.fckeditor.uploader;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.yoda.kernal.util.PortalUtil;
import com.yoda.util.Utility;

public class SimpleUploaderServlet extends HttpServlet {

	public SimpleUploaderServlet() {
	}

	public void init() throws ServletException {
		debug = (new Boolean(getInitParameter("debug"))).booleanValue();
		enabled = (new Boolean(getInitParameter("enabled"))).booleanValue();
		if (debug)
			System.out.println("\r\n---- SimpleUploaderServlet initialization started ----");

/*		baseDir = getInitParameter("baseDir");
		enabled = (new Boolean(getInitParameter("enabled"))).booleanValue();
		if(baseDir == null)
		baseDir = "/UserFiles/";
		String realBaseDir = getServletContext().getRealPath(baseDir);
		File baseFile = new File(realBaseDir);
		if(!baseFile.exists())
		baseFile.mkdir();
*/		

		allowedExtensions = new Hashtable<String, ArrayList<String>>(3);
		deniedExtensions = new Hashtable<String, ArrayList<String>>(3);

		allowedExtensions.put("File", stringToArrayList(getInitParameter("AllowedExtensionsFile")));
		deniedExtensions.put("File", stringToArrayList(getInitParameter("DeniedExtensionsFile")));

		allowedExtensions.put("Image", stringToArrayList(getInitParameter("AllowedExtensionsImage")));
		deniedExtensions.put("Image", stringToArrayList(getInitParameter("DeniedExtensionsImage")));

		allowedExtensions.put("Flash", stringToArrayList(getInitParameter("AllowedExtensionsFlash")));
		deniedExtensions.put("Flash", stringToArrayList(getInitParameter("DeniedExtensionsFlash")));

		if (debug) {
			System.out.println("---- SimpleUploaderServlet initialization completed ----\r\n");
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
		{
//		Session session = null;
		try {
//			session = HibernateConnection.getInstance().getCurrentSession();
//			session.beginTransaction();
			if (debug)
				System.out.println("--- BEGIN DOPOST ---");
			response.setContentType("text/html; charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			PrintWriter out = response.getWriter();
			String typeStr = request.getParameter("Type");
			String currentPath = null;
			currentPath = this.getBaseDir(request) + typeStr;
			File file = new File(currentPath);
			if (!file.exists()) {
				file.mkdir();
			}

			// String currentDirPath =
			// getServletContext().getRealPath(currentPath);
			String currentDirPath = currentPath;
			// currentPath = request.getContextPath() + currentPath;
			if (debug)
				System.out.println(currentDirPath);
			String retVal = "0";
			String newName = "";
			String fileUrl = "";
			String errorMessage = "";
			if (enabled) {
				FileItemFactory factory = new DiskFileItemFactory();
				ServletFileUpload upload = new ServletFileUpload(factory);
				try {
					List items = upload.parseRequest(request);

					Map fields = new HashMap();

					for (Iterator iter = items.iterator(); iter.hasNext();) {
						FileItem item = (FileItem) iter.next();
						if (item.isFormField())
							fields.put(item.getFieldName(), item.getString());
						else
							fields.put(item.getFieldName(), item);
					}

					FileItem uplFile = (FileItem) fields.get("NewFile");

					String fileNameLong = uplFile.getName();

					fileNameLong = fileNameLong.replace('\\', '/');

					String pathParts[] = fileNameLong.split("/");
					String fileName = pathParts[pathParts.length - 1];
					String nameWithoutExt = getNameWithoutExtension(fileName);
					String ext = getExtension(fileName);

					File pathToSave = new File(currentDirPath, fileName);

					fileUrl = getUrlPrefix(request) + typeStr + "/" + fileName;

					if (extIsAllowed(typeStr, ext)) {
						for (int counter = 1; pathToSave.exists(); counter++) {
							newName = nameWithoutExt + "(" + counter + ")"
									+ "." + ext;
							fileUrl = getUrlPrefix(request) + typeStr + "/"
									+ newName;
							retVal = "201";
							pathToSave = new File(currentDirPath, newName);
						}

						uplFile.write(pathToSave);
					} else {
						retVal = "202";
						errorMessage = "";
						if (debug)
							System.out.println("Invalid file type: " + ext);
					}
				} catch (Exception ex) {
					if (debug)
						ex.printStackTrace();
					retVal = "203";
				}
			} else {
				retVal = "1";
				errorMessage = "This file uploader is disabled. Please check the WEB-INF/web.xml file";
			}
			out.println("<script type=\"text/javascript\">");
			out.println("window.parent.OnUploadCompleted(" + retVal + ",'"
					+ fileUrl + "','" + newName + "','" + errorMessage + "');");
			out.println("</script>");
			out.flush();
			out.close();
			if (debug)
				System.out.println("--- END DOPOST ---");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
//		finally {
//			if (session.isOpen()) {
//				session.getTransaction().rollback();
//			}
//		}
	}

	private static String getNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf("."));
	}

	private String getExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(".") + 1);
	}

	private ArrayList<String> stringToArrayList(String str) {
		if (debug)
			System.out.println(str);
		String strArr[] = str.split("\\|");
		ArrayList<String> tmp = new ArrayList<String>();
		if (str.length() > 0) {
			for (int i = 0; i < strArr.length; i++) {
				if (debug)
					System.out.println(i + " - " + strArr[i]);
				tmp.add(strArr[i].toLowerCase());
			}

		}
		return tmp;
	}

	private boolean extIsAllowed(String fileType, String ext) {
		ext = ext.toLowerCase();
		ArrayList<String> allowList = (ArrayList<String>)allowedExtensions.get(fileType);

		ArrayList<String> denyList = (ArrayList<String>)deniedExtensions.get(fileType);

		if (allowList.size() == 0)
			return !denyList.contains(ext);

		if (denyList.size() == 0)
			return allowList.contains(ext);
		else
			return false;
	}

	private String getBaseDir(HttpServletRequest request) throws Exception {
//		AdminBean adminBean = AdminLookupDispatchAction.getAdminBean(request);
//		Admin admin = (Admin)request.getSession().getAttribute("admin");

//		User user = PortalUtil.getAuthenticatedUser();

//		long siteId = user.getLastVisitSiteId();

		long siteId = PortalUtil.getSiteId(request);

		String baseDir = Utility.getResourcePrefix(siteId);

		File baseFile = new File(baseDir);

		if (!baseFile.exists())
			baseFile.mkdirs();

		return baseDir;
	}

	private String getUrlPrefix(HttpServletRequest request) throws Exception {
//		AdminBean adminBean = AdminLookupDispatchAction.getAdminBean(request);
//		Admin admin = (Admin)request.getSession().getAttribute("admin");

//		User user = PortalUtil.getAuthenticatedUser();

//		Site site = adminBean.getSite();

//		long siteId = user.getLastVisitSiteId();
		long siteId = PortalUtil.getSiteId(request);

		String baseDir = Utility.getResourceUrlPrefix(siteId);

		File baseFile = new File(baseDir);

		if (!baseFile.exists())
			baseFile.mkdirs();

		return baseDir;
	}

	private static String baseDir;
	private static boolean debug = false;
	private static boolean enabled = false;
	private static Hashtable<String, ArrayList<String>> allowedExtensions;
	private static Hashtable<String, ArrayList<String>> deniedExtensions;
}