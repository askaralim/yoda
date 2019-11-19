package com.taklip.yoda.tool;

import java.io.File;

import com.taklip.yoda.properties.YodaProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileUrlResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taklip.yoda.model.User;
import com.taklip.yoda.util.AuthenticatedUtil;

@Service
public class FileUploader {
	protected final YodaProperties properties;

	@Autowired
	public FileUploader(YodaProperties properties) {
		this.properties = properties;
	}

	private final Logger logger = LoggerFactory.getLogger(FileUploader.class);

	public static final String UPLOAD_BASE_FOLDER = "/uploads/";
	public static final String UPLOAD_BRAND_FOLDER = "/brand/";
	public static final String UPLOAD_ITEM_FOLDER = "/item/";
	public static final String UPLOAD_CONTENT_FOLDER = "/content/";

	public String saveFile(MultipartFile file) {
		String newName = StringUtils.EMPTY;

		String originalFilename = file.getOriginalFilename();

		originalFilename = originalFilename.replace(CharPool.BACK_SLASH, CharPool.FORWARD_SLASH);

		String[] paths = originalFilename.split(StringPool.FORWARD_SLASH);

		String fileName = paths[paths.length - 1];

		String fileNameWithoutExt = getFileNameWithoutExtension(fileName);

		String ext = getFileExtension(fileName);

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

	public void deleteFile(String path) {
		if (StringUtils.isEmpty(path)) {
			return;
		}

		try {
//			Resource resource = new ServletContextResource(ServletContextUtil.getServletContext(), path);
			Resource resource = new FileUrlResource(path);

			File file = null;

			if (resource.exists()) {
				file = resource.getFile();
			}
			else {
				if (!path.startsWith(UPLOAD_BASE_FOLDER)) {
					path = path.substring(path.indexOf(UPLOAD_BASE_FOLDER), path.length());

//					resource = new ServletContextResource(ServletContextUtil.getServletContext(), path);
					resource = new FileUrlResource(path);

					file = resource.getFile();
				}
			}

			if (file.exists()) {
				file.delete();
			}
		}
		catch (Exception e) {
			logger.error(e.getMessage() + " - File Path:" + path);
		}
	}

	public String getFileNameWithoutExtension(String fileName) {
		return fileName.substring(0, fileName.lastIndexOf(StringPool.PERIOD));
	}

	public String getFileExtension(String fileName) {
		return fileName.substring(fileName.lastIndexOf(StringPool.PERIOD) + 1);
	}

	protected String getRealPath() {
		return this.getRealPath(StringPool.BLANK);
	}

	protected String getRealPath(String folder) {
		User user = AuthenticatedUtil.getAuthenticatedUser();

//		String prefix = ServletContextUtil.getServletContext().getRealPath(UPLOAD_BASE_FOLDER);
		String prefix = "";

		if (null != user) {
			prefix = prefix.concat(StringPool.FORWARD_SLASH + user.getId() + StringPool.FORWARD_SLASH);
		}

		prefix = prefix.concat(folder);

		File baseFile = new File(prefix);

		if (!baseFile.exists())
			baseFile.mkdirs();

		return prefix;
	}

	protected String getUrlPrefix() {
		return this.getUrlPrefix(StringPool.BLANK);
	}

	protected String getUrlPrefix(String folder) {
		User user = AuthenticatedUtil.getAuthenticatedUser();

//		String prefix = ServletContextUtil.getContextPath() + UPLOAD_BASE_FOLDER;
		String prefix = "";

		if (null != user) {
			prefix = prefix.concat(user.getId() + StringPool.FORWARD_SLASH);
		}

		prefix = prefix.concat(folder);

		return prefix;
	}

	protected String getRandomFileName() {
		User user = AuthenticatedUtil.getAuthenticatedUser();

		return String.valueOf(System.currentTimeMillis()) + user.getId();
	}
}