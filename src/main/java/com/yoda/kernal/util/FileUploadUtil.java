package com.yoda.kernal.util;

import java.io.File;

import org.springframework.web.multipart.MultipartFile;

import com.yoda.kernal.servlet.ServletContextUtil;
import com.yoda.user.model.User;
import com.yoda.util.StringPool;
import com.yoda.util.Validator;

public class FileUploadUtil {
	public String saveImage(MultipartFile file) {
		String newName = "";

		String fileNameLong = file.getOriginalFilename();

		fileNameLong = fileNameLong.replace('\\', '/');

		String[] pathParts = fileNameLong.split("/");

		String fileName = pathParts[pathParts.length - 1];

		String nameWithoutExt = fileName.substring(0, fileName.lastIndexOf("."));

		String ext = fileName.substring(fileName.lastIndexOf(".") + 1);

		String currentDirPath = StringPool.BLANK;

		try {
			currentDirPath = getBaseDir();

			File pathToSave = new File(currentDirPath, fileName);

			int counter = 1;

			while (pathToSave.exists()) {
				newName = nameWithoutExt + "(" + counter + ")" + "." + ext;

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

	private String getBaseDir() {
		User user = PortalUtil.getAuthenticatedUser();

		String prefix = ServletContextUtil.getServletContext().getRealPath("/uploads/");

		if (Validator.isNotNull(user)) {
			prefix = prefix.concat("/" + user.getUserId());
		}

		File baseFile = new File(prefix);

		if (!baseFile.exists())
			baseFile.mkdirs();

		return prefix;
	}

	private String getUrlPrefix() {
		User user = PortalUtil.getAuthenticatedUser();

		String urlPrefix = ServletContextUtil.getContextPath() + "/uploads/";

		if (Validator.isNotNull(user)) {
			urlPrefix = urlPrefix.concat(user.getUserId() + "/");
		}

		return urlPrefix;
	}
}