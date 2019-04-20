package com.taklip.yoda.tool;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImageUploader extends FileUploader {
	private final Logger logger = LoggerFactory.getLogger(ImageUploader.class);

	private static final int SIZE_M = 600;
	private static final String LARGE_IMAGE_SUFFIX = "_L";

	public String uploadBrandImage(InputStream imageInputStream, String originalFilename) {
		return upload(imageInputStream, SIZE_M, SIZE_M, originalFilename, UPLOAD_BRAND_FOLDER);
	}

	public String uploadContentImage(InputStream imageInputStream, String originalFilename) {
		return upload(imageInputStream, SIZE_M, SIZE_M, originalFilename, UPLOAD_CONTENT_FOLDER);
	}

	public String uploadImage(InputStream imageInputStream, String filename, String path) {
		String imageExtension = getFileExtension(filename);
		String imageNameWithoutExt = getFileNameWithoutExtension(filename);

		String imageName = StringPool.BLANK;
		String imageNameLarge = StringPool.BLANK;

		try {
			BufferedImage imageLarge = ImageIO.read(imageInputStream);

			imageNameLarge = imageNameWithoutExt + LARGE_IMAGE_SUFFIX + StringPool.PERIOD + imageExtension;

			File imageFile = new File(path, imageNameLarge);

			int counter = 1;

			while (imageFile.exists()) {
				imageNameLarge = imageNameWithoutExt + LARGE_IMAGE_SUFFIX + StringPool.OPEN_PARENTHESIS + counter + StringPool.CLOSE_PARENTHESIS + StringPool.PERIOD + imageExtension;
				imageFile = new File(path, imageNameLarge);
				counter++;
			}

			ImageIO.write(imageLarge, imageExtension, imageFile);

			imageName = imageNameLarge;
		}
		catch (IOException e) {
			logger.error(e.getMessage());
		}

		return imageName;
	}


	public String uploadItemImage(InputStream inputStream, String originalFilename) {
		return upload(inputStream, SIZE_M, SIZE_M, originalFilename, UPLOAD_ITEM_FOLDER);
	}

	public String upload(InputStream imageInputStream, String filename) {
		return upload(imageInputStream, SIZE_M, SIZE_M, filename, StringPool.BLANK);
	}

	public String upload(InputStream imageInputStream, int targetWidth, int targetHeight, String filename, String folder) {
		String imageExtension = getFileExtension(filename);
		String randomFileName = getRandomFileName();

		String imageName = StringPool.BLANK;
		String imageNameLarge = StringPool.BLANK;

		try {
			BufferedImage imageLarge = ImageIO.read(imageInputStream);

			imageNameLarge = randomFileName + LARGE_IMAGE_SUFFIX + StringPool.PERIOD + imageExtension;

			File imageFile = new File(getRealPath(folder), imageNameLarge);

			ImageIO.write(imageLarge, imageExtension, imageFile);

			imageName = imageNameLarge;

			if (targetWidth <= imageLarge.getWidth() || targetHeight <= imageLarge.getHeight()) {
				BufferedImage imageMedium = Scalr.resize(imageLarge, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight);

				imageName = randomFileName +  StringPool.PERIOD + imageExtension;

				imageFile = new File(getRealPath(folder), imageName);

				ImageIO.write(imageMedium, imageExtension, imageFile);
			}

			return getUrlPrefix(folder) + imageFile.getName();
		}
		catch (IOException e) {
			logger.error(e.getMessage());

			return null;
		}
	}

	public void deleteImage(String path) {
		if (StringUtils.isBlank(path)) {
			return;
		}

		String imageExtension = path.substring(path.lastIndexOf(StringPool.PERIOD) + 1);
		String largeImagePath = path.substring(0, path.lastIndexOf(StringPool.PERIOD)) + LARGE_IMAGE_SUFFIX + StringPool.PERIOD + imageExtension;

		deleteFile(path);
		deleteFile(largeImagePath);
	}
}