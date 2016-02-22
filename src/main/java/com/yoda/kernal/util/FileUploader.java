package com.yoda.kernal.util;

import java.io.File;
import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.user.model.User;
import com.yoda.util.CharPool;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

public class FileUploader {
	private static final String UPLOAD_FOLDER = "/uploads/";

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
			e.printStackTrace();
		}

		return getUrlPrefix() + fileName;
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
			e.printStackTrace();
		}
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