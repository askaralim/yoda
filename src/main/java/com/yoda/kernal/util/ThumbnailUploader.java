package com.yoda.kernal.util;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import com.yoda.util.StringPool;
import com.yoda.util.Utility;

public class ThumbnailUploader extends FileUploader {
	private static final String THUMBNAIL_SMALL = "-25-";
	private static final String THUMBNAIL_EXTENSION = "png";
	private static final int THUMBNAIL_SIZE_S = 25;
	private static final int THUMBNAIL_SIZE_M = 75;
	private static final int THUMBNAIL_SIZE_L = 200;

	public String createThumbnailLarge(MultipartFile file) {
		return createThumbnail(file, THUMBNAIL_SIZE_L);
	}

	public String createThumbnailMedium(MultipartFile file) {
		return createThumbnail(file, THUMBNAIL_SIZE_M);
	}

	@SuppressWarnings("unused")
	private String createThumbnailSmall(MultipartFile file) {
		return createThumbnail(file, THUMBNAIL_SIZE_S);
	}

	private String createThumbnail(MultipartFile file, int maxSize) {
		if (!Utility.isImage(file.getOriginalFilename())) {
			return null;
		}

		BufferedImage image = null;

		try {
			String fileName = this.getRandomFileName() + StringPool.UNDERLINE + maxSize + StringPool.PERIOD + THUMBNAIL_EXTENSION;

			File thumbnail = new File(getRealPath(), fileName);

			//may not happen?
			while (thumbnail.exists()) {
				fileName = this.getRandomFileName() + StringPool.UNDERLINE + maxSize + StringPool.PERIOD + THUMBNAIL_EXTENSION;

				thumbnail = new File(getRealPath(), fileName);
			}

			image = ImageIO.read(file.getInputStream());

			int height = image.getHeight();
			int width = image.getWidth();

			if (height > width) {
				image = Scalr.crop(image, 0, (height - width) / 2, width, width);
			}
			else if (height < width) {
				image = Scalr.crop(image, (width - height) / 2, 0, height, height);
			}

			BufferedImage scaledImg = Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, maxSize, Scalr.OP_ANTIALIAS);

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

	@SuppressWarnings("unused")
	private String createThumbnail(File file, int size)
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

	@SuppressWarnings("unused")
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
}