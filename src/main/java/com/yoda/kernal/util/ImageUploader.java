package com.yoda.kernal.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;

import com.yoda.util.StringPool;

public class ImageUploader extends FileUploader {
	static Map<String, String> contentTypeImageExtensionMap = new HashMap<String, String>();

	static {
		contentTypeImageExtensionMap.put("image/jpeg", "jpeg");
	}

	private Logger logger = Logger.getLogger(ImageUploader.class);

	private static final int SIZE_M = 400;

	public String resize(InputStream imageInputStream, int targetSize, String contentType) {
		return this.resize(imageInputStream, targetSize, targetSize, contentType);
	}

	public String resize(InputStream imageInputStream, int targetWidth, int targetHeight, String contentType) {
		String imageExtension = contentTypeImageExtensionMap.get(contentType);

		BufferedImage image = null;

		try {
			image = ImageIO.read(imageInputStream);

			if (targetWidth != image.getWidth() || targetHeight != image.getHeight()) {
				image = Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, targetWidth, targetHeight, Scalr.OP_ANTIALIAS);
			}

			String fileName = this.getRandomFileName() + StringPool.PERIOD + imageExtension;

			File file = new File(getRealPath(), fileName);

			//may not happen?
			while (file.exists()) {
				fileName = this.getRandomFileName() + StringPool.PERIOD + imageExtension;

				file = new File(getRealPath(), fileName);
			}

			ImageIO.write(image, imageExtension, file);

			return getUrlPrefix() + file.getName();
		}
		catch (IOException e) {
			logger.error(e.getMessage());

			return null;
		}
		finally {
			image.flush();
		}
	}
}