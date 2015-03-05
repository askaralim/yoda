//package com.yoda.content.controller;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.OutputStream;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.yoda.content.ImageScaler;
//import com.yoda.content.model.ContentImage;
//import com.yoda.content.service.ContentImageService;
//import com.yoda.kernal.servlet.ServletContextUtil;
//import com.yoda.kernal.util.PortalUtil;
//import com.yoda.site.model.Site;
//import com.yoda.site.service.SiteService;
//import com.yoda.user.model.User;
//import com.yoda.util.Constants;
//import com.yoda.util.Format;
//import com.yoda.util.Utility;
//
//@Controller
//@RequestMapping("/images")
//public class SecureImageController {
//	@Autowired
//	ContentImageService contentImageService;
//
//	@Autowired
//	SiteService siteService;
//
//	Logger logger = Logger.getLogger(SecureImageController.class);
//
//	@RequestMapping(method = RequestMethod.GET)
//	public void process(
//			@RequestParam("type") String type,
//			@RequestParam(value = "imageId", required = false) String id,
//			@RequestParam(value = "maxsize", required = false, defaultValue = "0") String maxsize,
//			HttpServletRequest request, HttpServletResponse response) {
//		response.setHeader("Cache-Control", "no-cache");
//		response.setHeader("Expires", "0");
//		response.setHeader("Pragma", "no-cache");
//
//		try {
////			String type = (String) request.getParameter("type");
////			String id = (String) request.getParameter("imageId");
////			String size = (String) request.getParameter("maxsize");
//
////			int maxsize = 0;
//
////			if (size != null) {
////				maxsize = Format.getInt(size);
////			}
//
//			byte data[] = getImage(request, type, Long.valueOf(id), Format.getInt(maxsize));
//
//			response.setContentType("image/jpeg");
//			response.setContentLength(data.length);
//
//			OutputStream outputStream = response.getOutputStream();
//
//			outputStream.write(data);
//			outputStream.flush();
//		}
//		catch (Exception e) {
//			logger.error("Unable to service request", e);
//		}
//	}
//
//	public byte[] getImage(
//			HttpServletRequest request, String type, long id, int maxsize)
//		throws Exception {
//
//		final byte transparent[] =
//			{0x47, 0x49, 0x46, 0x38, 0x39, 0x61, 0x01, 0x00, 0x01, 0x00,
//			(byte) 0x80, 0x00, 0x00, (byte) 0xff, (byte) 0xff, (byte) 0xff,
//			0x00, 0x00, 0x00, 0x21, (byte) 0xf9, 0x04, 0x01, 0x00, 0x00, 0x00,
//			0x00, 0x2c, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00,
//			0x02, 0x02, 0x44, 0x01, 0x00, 0x3b };
//
////		Session session = HibernateConnection.getInstance().getCurrentSession();
//		String contentType = null;
//		byte data[] = null;
////		session.beginTransaction();
//
//		try {
//			if (type.equals(Constants.IMAGEPROVIDER_CONTENT)) {
//				ContentImage image = new ContentImage();
//				Long imageId = id;
////				Long imageId = Format.getLong(id);
//				image = contentImageService.getContentImage(imageId);
////				image = ContentImageDAO.load(imageId);
//				contentType = image.getContentType();
//				data = image.getImageValue();
//
//				if (maxsize != 0) {
//					ImageScaler scaler = new ImageScaler(data, image.getContentType());
//					scaler.resize(maxsize);
//					data = scaler.getBytes();
//				}
//
//			}
//			else if (type.equals(Constants.IMAGEPROVIDER_SITE)) {
//				Site site = siteService.getSite(id);
//				//Site site = SiteCache.getSiteById(id);
//				data = site.getLogoValue();
//
//				if (data == null) {
//					data = transparent;
//				}
//
//				if (maxsize != 0) {
//					ImageScaler scaler = new ImageScaler(data, site.getLogoContentType());
//					scaler.resize(maxsize);
//					data = scaler.getBytes();
//				}
//
//			}
////			else if (type.equals(Constants.IMAGEPROVIDER_ITEM)) {
////				ItemImage image = new ItemImage();
//////				Long imageId = Format.getLong(id);
////				Long imageId = id;
////				image = ItemImageDAO.load(imageId);
////				contentType = image.getContentType();
////				data = image.getImageValue();
////
////				if (maxsize != 0) {
////					ImageScaler scaler = new ImageScaler(data, image.getContentType());
////					scaler.resize(maxsize);
////					data = scaler.getBytes();
////				}
////
////			}
//			else if (type.equals(Constants.IMAGEPROVIDER_URL)) {
//				String url = request.getRequestURL().toString();
//
//				int index = url.indexOf("//");
//
//				index = url.indexOf("/", index + 2);
//
//				String prefix = url.substring(0, index) + ServletContextUtil.getContextPath();
//
//				data = Utility.httpGet(prefix + id);
//
//				contentType = "image/jpeg";
//
//				if (maxsize != 0) {
//					ImageScaler scaler = new ImageScaler(data, contentType);
//					scaler.resize(maxsize);
//					data = scaler.getBytes();
//				}
//
//			}
//			else if (type.equals(Constants.IMAGEPROVIDER_TEMPLATE)) {
//				// template preview image
//				contentType = "image/jpeg";
////				Site site = AdminAction.getAdminBean(request).getSite();
//
//				User user = PortalUtil.getAuthenticatedUser();
//
//				String templatePrefix = Utility.getTemplatePrefix(user.getLastVisitSiteId(), String.valueOf(id));
//
//				if (templatePrefix.trim().length() == 0 || id == 0
////						|| id.trim().length() == 0
//					){
//					contentType = "image/gif";
//					data = transparent;
//				}
//				else {
//					String filename = templatePrefix + "preview.jpg";
//					File file = new File(filename);
//
//					if (file.exists()) {
//						FileInputStream fr = new FileInputStream(new File(filename));
//
//						data = new byte[(int) file.length()];
//						fr.read(data, 0, (int) file.length());
//
//						if (maxsize != 0) {
//							ImageScaler scaler = new ImageScaler(data, contentType);
//							scaler.resize(maxsize);
//							data = scaler.getBytes();
//						}
//
//						fr.close();
//					}
//					else {
//						data = transparent;
//					}
//				}
//			}
//		}
//		finally {
////			session.getTransaction().commit();
//		}
//		return data;
//	}
//}