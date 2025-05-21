package com.taklip.yoda.common.tools;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.taklip.yoda.properties.YodaProperties;
import org.imgscalr.Scalr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.taklip.yoda.common.contant.StringPool;
import com.taklip.yoda.common.util.AuthenticatedUtil;

@Service
public class ThumbnailUploader extends FileUploader {
    public ThumbnailUploader(YodaProperties properties) {
        super(properties);
    }

    private final Logger logger = LoggerFactory.getLogger(ThumbnailUploader.class);

    private static final String THUMBNAIL_SMALL = "-25-";
    private static final String THUMBNAIL_EXTENSION = "png";
    private static final int THUMBNAIL_SIZE_S = 25;
    private static final int THUMBNAIL_SIZE_M = 75;
    private static final int THUMBNAIL_SIZE_L = 200;

    public String createThumbnailLarge(InputStream imageInputStream) {
        return createThumbnail(imageInputStream, THUMBNAIL_SIZE_L);
    }

    public String createThumbnailMedium(InputStream imageInputStream) {
        return createThumbnail(imageInputStream, THUMBNAIL_SIZE_M);
    }

    @SuppressWarnings("unused")
    private String createThumbnailSmall(InputStream imageInputStream) {
        return createThumbnail(imageInputStream, THUMBNAIL_SIZE_S);
    }

    private String createThumbnail(InputStream imageInputStream, int maxSize) {
        BufferedImage image = null;

        try {
            String userHome = System.getProperties().getProperty("user.home");
            String abolutePath = userHome + properties.getFileLocation()
                    + AuthenticatedUtil.getAuthenticatedUser().getId() + StringPool.FORWARD_SLASH;
            String imagePath = UPLOAD_BASE_FOLDER + AuthenticatedUtil.getAuthenticatedUser().getId()
                    + StringPool.FORWARD_SLASH;

            image = ImageIO.read(imageInputStream);

            int height = image.getHeight();
            int width = image.getWidth();

            if (height > width) {
                image = Scalr.crop(image, 0, (height - width) / 2, width, width);
            } else if (height < width) {
                image = Scalr.crop(image, (width - height) / 2, 0, height, height);
            }

            BufferedImage scaledImg = Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.AUTOMATIC, maxSize,
                    Scalr.OP_ANTIALIAS);

            String fileName = this.getRandomFileName() + StringPool.UNDERLINE + maxSize + StringPool.PERIOD
                    + THUMBNAIL_EXTENSION;

            File thumbnail = new File(abolutePath, fileName);

            // may not happen?
            while (thumbnail.exists()) {
                fileName = this.getRandomFileName() + StringPool.UNDERLINE + maxSize + StringPool.PERIOD
                        + THUMBNAIL_EXTENSION;

                thumbnail = new File(getRealPath(), fileName);
            }

            ImageIO.write(scaledImg, THUMBNAIL_EXTENSION, thumbnail);

            return imagePath + thumbnail.getName();
        } catch (IOException e) {
            logger.error(e.getMessage());

            return null;
        } finally {
            image.flush();
        }
    }

    @SuppressWarnings("unused")
    private String createThumbnail(File file, int size)
            throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // BufferedImage thumbnail = ImageIO.read(in);
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

            // ImageIO.write(thumbnail, "png", baos);
            // baos.flush();
            // return baos.toByteArray();
            return thumbnailFile.getName();
        } finally {
            // baos.close();
        }
    }

    @SuppressWarnings("unused")
    private String createThumbnail(File file) throws IOException {
        // BufferedImage image = ImageIO.read(new File(path));
        BufferedImage image = ImageIO.read(file);

        // BufferedImage scaledImage = Scalr.resize(image, 250);

        BufferedImage scaledImg = Scalr.resize(
                image, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, 32, 32, Scalr.OP_ANTIALIAS);

        String fileName = file.getName().substring(0, file.getName().lastIndexOf(StringPool.PERIOD));

        File thumbnail = new File(getRealPath() + StringPool.SLASH + fileName + THUMBNAIL_SMALL + ".png");

        ImageIO.write(scaledImg, "png", thumbnail);

        // ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // ImageIO.write(scaledImg, "png", baos);

        // baos.flush();
        //
        // byte[] scaledImageInByte = baos.toByteArray();
        //
        // InputStream is = new ByteArrayInputStream(scaledImageInByte);
        //
        // baos.close();

        return thumbnail.getName();
    }
}