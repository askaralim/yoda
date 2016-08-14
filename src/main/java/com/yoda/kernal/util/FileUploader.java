package com.yoda.kernal.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.user.model.User;
import com.yoda.util.CharPool;
import com.yoda.util.StringPool;
import com.yoda.util.Utility;
import com.yoda.util.Validator;

public class FileUploader {
	Logger logger = Logger.getLogger(FileUploader.class);

	private static final String UPLOAD_FOLDER = "/uploads/";
	private static final String THUMBNAIL_SMALL = "-25-";
	private static final String THUMBNAIL_EXTENSION = "png";
	private static final int THUMBNAIL_SIZE_S = 25;
	private static final int THUMBNAIL_SIZE_M = 75;
	private static final int THUMBNAIL_SIZE_L = 200;

	private static FileUploader instance;

	public static FileUploader getInstance() {
		if (Validator.isNotNull(instance)) {
			return instance;
		}

		instance = new FileUploader();

		return instance;
	}

	public String saveFile(MultipartFile file) {
		String newName = StringPool.BLANK;

		String originalFilename = file.getOriginalFilename();

		originalFilename = originalFilename.replace(CharPool.BACK_SLASH, CharPool.FORWARD_SLASH);

		String[] paths = originalFilename.split(StringPool.FORWARD_SLASH);

		String fileName = paths[paths.length - 1];

		String fileNameWithoutExt = fileName.substring(0, fileName.lastIndexOf(StringPool.PERIOD));

		String ext = fileName.substring(fileName.lastIndexOf(StringPool.PERIOD) + 1);

		String currentDirPath = StringPool.BLANK;

		try {
			currentDirPath = getRealPath();

			File pathToSave = new File(currentDirPath, fileName);

			int counter = 1;

			while (pathToSave.exists()) {
				newName = fileNameWithoutExt + StringPool.OPEN_PARENTHESIS + counter + StringPool.CLOSE_PARENTHESIS + StringPool.PERIOD + ext;

				pathToSave = new File(currentDirPath, newName);

				fileName = newName;

				counter++;
			}

			file.transferTo(pathToSave);
		}
		catch (Exception e) {
			logger.error(e.getMessage());
		}

		return getUrlPrefix() + fileName;
	}

	public String saveThumbnailLarge(MultipartFile file) {
		return this.createThumbnail(file, THUMBNAIL_SIZE_L);
	}

	public String saveThumbnailMedium(MultipartFile file) {
		return this.createThumbnail(file, THUMBNAIL_SIZE_M);
	}

	public String saveThumbnailSmall(MultipartFile file) {
		return this.createThumbnail(file, THUMBNAIL_SIZE_S);
	}

	private String createThumbnail(MultipartFile file, int maxSize) {
		if (!Utility.isImage(file.getOriginalFilename())) {
			return null;
		}

		BufferedImage image = null;

		try {
			String currentDirPath = getRealPath();

			String randomFileName = this.getRandomFileName() + StringPool.UNDERLINE + maxSize +  StringPool.PERIOD + THUMBNAIL_EXTENSION;

			File thumbnail = new File(currentDirPath, randomFileName);

			while (thumbnail.exists()) {
				randomFileName = this.getRandomFileName() + StringPool.UNDERLINE + maxSize + StringPool.PERIOD + THUMBNAIL_EXTENSION;

				thumbnail = new File(currentDirPath, randomFileName);
			}

			image = ImageIO.read(file.getInputStream());

			BufferedImage scaledImg = null;

			int height = image.getHeight();
			int width = image.getWidth();

			if (height > width) {
				image = Scalr.crop(image, 0, (height - width) / 2, width, width);
			}
			else if (height < width) {
				image = Scalr.crop(image, (width - height) / 2, 0, height, height);
			}

			scaledImg = Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, maxSize, Scalr.OP_ANTIALIAS);

			ImageIO.write(scaledImg, THUMBNAIL_EXTENSION, thumbnail);

			return getUrlPrefix() + thumbnail.getName();
		}
		catch (IOException e) {
			logger.error(e.getMessage());

			return null;
		}
		finally {
			image.flush();
		}
	}

	private String createThumbnail(File file) throws IOException {
//		BufferedImage image = ImageIO.read(new File(path));
		BufferedImage image = ImageIO.read(file);

//		BufferedImage scaledImage = Scalr.resize(image, 250);

		BufferedImage scaledImg = Scalr.resize(
			image, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, 32, 32, Scalr.OP_ANTIALIAS);

		String fileName = file.getName().substring(0, file.getName().lastIndexOf(StringPool.PERIOD));

		File thumbnail = new File(getRealPath() + StringPool.SLASH + fileName + THUMBNAIL_SMALL + ".png");

		ImageIO.write(scaledImg, "png", thumbnail);
		
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();

//		ImageIO.write(scaledImg, "png", baos);

//		baos.flush();
//
//		byte[] scaledImageInByte = baos.toByteArray();
//
//		InputStream is = new ByteArrayInputStream(scaledImageInByte);
//
//		baos.close();
		
		
		
		return thumbnail.getName();
	}

	public String createThumbnail(File file, int size)
			throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		BufferedImage thumbnail = ImageIO.read(in);
		BufferedImage thumbnail = ImageIO.read(file);

		int width = thumbnail.getWidth();
		int height = thumbnail.getHeight();

		if (width > size || height > size) {
			if (width > height) {
				thumbnail = Scalr.resize(thumbnail, Scalr.Mode.FIT_TO_HEIGHT, size);
				thumbnail = Scalr.crop(thumbnail, thumbnail.getWidth() / 2 - size / 2, 0, size, size);
			} else {
				thumbnail = Scalr.resize(thumbnail, Scalr.Mode.FIT_TO_WIDTH, size);
				thumbnail = Scalr.crop(thumbnail, 0, thumbnail.getWidth() / 2 - size / 2, size, size);
			}
		}
		try {
			String fileName = file.getName().substring(0, file.getName().lastIndexOf(StringPool.PERIOD));

			File thumbnailFile = new File(getRealPath() + StringPool.SLASH + fileName + THUMBNAIL_SMALL + "2.png");

			ImageIO.write(thumbnail, "png", thumbnailFile);

//			ImageIO.write(thumbnail, "png", baos);
//			baos.flush();
//			return baos.toByteArray();
			return thumbnailFile.getName();
		} finally {
//			baos.close();
		}
	}

	public void deleteFile(String path) {
		if (Validator.isNull(path)) {
			return;
		}

		Resource resource = new ServletContextResource(ServletContextUtil.getServletContext(), path);

		File file = null;

		try {
			if (resource.exists()) {
				file = resource.getFile();
			}
			else {
				if (!path.startsWith(UPLOAD_FOLDER)) {
					path = path.substring(path.indexOf(UPLOAD_FOLDER), path.length());

					resource = new ServletContextResource(ServletContextUtil.getServletContext(), path);

					file = resource.getFile();
				}
			}

			if (file.exists()) {
				file.delete();
			}
		}
		catch (IOException e) {
			logger.error(e.getMessage() + " - File Path:" + path);
		}
	}

	private String getRandomFileName() {
		User user = PortalUtil.getAuthenticatedUser();

		return String.valueOf(System.currentTimeMillis()) + user.getUserId();
	}

	private String getFileNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf(StringPool.PERIOD));
	}

	private String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(StringPool.PERIOD) + 1);
	}
	private String getRealPath() {
		User user = PortalUtil.getAuthenticatedUser();

		String prefix = ServletContextUtil.getServletContext().getRealPath(UPLOAD_FOLDER);

		if (Validator.isNotNull(user)) {
			prefix = prefix.concat(StringPool.FORWARD_SLASH + user.getUserId());
		}

		File baseFile = new File(prefix);

		if (!baseFile.exists())
			baseFile.mkdirs();

		return prefix;
	}

	private String getUrlPrefix() {
		User user = PortalUtil.getAuthenticatedUser();

		String urlPrefix = ServletContextUtil.getContextPath() + UPLOAD_FOLDER;

		if (Validator.isNotNull(user)) {
			urlPrefix = urlPrefix.concat(user.getUserId() + StringPool.FORWARD_SLASH);
		}

		return urlPrefix;
	}
}