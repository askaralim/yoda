package com.yoda.kernal.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import com.yoda.util.StringPool;

public class ImageUploader extends FileUploader {
	private Logger logger = Logger.getLogger(ImageUploader.class);

	private static final int SIZE_M = 600;
	private static final String LARGE_IMAGE_SUFFIX = "_L";

	public String upload(InputStream imageInputStream, String filename) {
		return upload(imageInputStream, SIZE_M, SIZE_M, filename);
	}

	public String upload(InputStream imageInputStream, int targetWidth, int targetHeight, String filename) {
		String imageExtension = getFileExtension(filename);
		String randomFileName = getRandomFileName();

		String imageName = StringPool.BLANK;
		String imageNameLarge = StringPool.BLANK;

		try {
			BufferedImage imageLarge = ImageIO.read(imageInputStream);

			imageNameLarge = randomFileName + LARGE_IMAGE_SUFFIX + StringPool.PERIOD + imageExtension;

			File imageFile = new File(getRealPath(), imageNameLarge);

			ImageIO.write(imageLarge, imageExtension, imageFile);

			imageName = imageNameLarge;

			if (targetWidth <= imageLarge.getWidth() || targetHeight <= imageLarge.getHeight()) {
				BufferedImage imageMedium = Scalr.resize(imageLarge, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight);

				imageName = randomFileName +  StringPool.PERIOD + imageExtension;

				imageFile = new File(getRealPath(), imageName);

				ImageIO.write(imageMedium, imageExtension, imageFile);
			}

			return getUrlPrefix() + imageFile.getName();
		}
		catch (IOException e) {
			logger.error(e.getMessage());

			return null;
		}
	}

	public void deleteImage(String path) {
		String imageExtension = path.substring(path.lastIndexOf(StringPool.PERIOD) + 1);
		String largeImagePath = path.substring(0, path.lastIndexOf(StringPool.PERIOD)) + LARGE_IMAGE_SUFFIX + StringPool.PERIOD + imageExtension;

		super.deleteFile(path);
		super.deleteFile(largeImagePath);
	}
}