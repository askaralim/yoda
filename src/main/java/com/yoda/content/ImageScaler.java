//package com.yoda.content;
//
//import java.awt.RenderingHints;
//import java.awt.RenderingHints.Key;
//import java.awt.Toolkit;
//import java.awt.geom.AffineTransform;
//import java.awt.image.BufferedImage;
//import java.io.ByteArrayOutputStream;
//import java.io.IOException;
//import java.util.HashMap;
//
//import javax.media.jai.Interpolation;
//import javax.media.jai.JAI;
//import javax.media.jai.RenderedOp;
//import javax.media.jai.operator.AffineDescriptor;
//
//import com.sun.media.jai.codec.ByteArraySeekableStream;
//import com.sun.media.jai.codec.ImageCodec;
//import com.sun.media.jai.codec.ImageEncoder;
//import com.sun.media.jai.codec.JPEGEncodeParam;
//
//public class ImageScaler {
//	private static final RenderingHints scaleHints;
//	private static final JPEGEncodeParam jpegHints = new JPEGEncodeParam();
//
//	byte data[] = null;
//	String mimeType = null;
//
//	static {
//		scaleHints = new RenderingHints(new HashMap<Key, Object>());
//		scaleHints.put(
//			RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//		scaleHints.put(
//			RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
//		scaleHints.put(
//			RenderingHints.KEY_ALPHA_INTERPOLATION, RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY);
//		scaleHints.put(
//			RenderingHints.KEY_COLOR_RENDERING, RenderingHints.VALUE_COLOR_RENDER_QUALITY);
//		scaleHints.put(
//			RenderingHints.KEY_DITHERING, RenderingHints.VALUE_DITHER_ENABLE);
//		scaleHints.put(
//			RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
//
//		jpegHints.setQuality(0.75f);
//	}
//
//	public ImageScaler(byte[] data, String mimeType) {
//		System.getProperties().setProperty(
//			"com.sun.media.jai.disableMediaLib", "true");
//
//		this.data = data;
//		this.mimeType = mimeType;
//	}
//
//	public void resize(int maxsize) throws IOException {
//		BufferedImage bufferedimage;
//		RenderedOp renderedop = 
//			JAI.create("stream", new ByteArraySeekableStream(data));
//
//		bufferedimage = renderedop.getAsBufferedImage();
//
//		int sourceWidth = bufferedimage.getWidth();
//		int sourceHeight = bufferedimage.getHeight();
//		int resultWidth = sourceWidth;
//		int resultHeight = sourceHeight;
//
//		if (maxsize < resultWidth) {
//			resultWidth = maxsize;
//			resultHeight = (int) ((resultWidth * sourceHeight) / sourceWidth);
//		}
//
//		if (maxsize < resultHeight) {
//			resultHeight = maxsize;
//			resultWidth = (int) ((resultHeight * sourceWidth) / sourceHeight);
//		}
//
//		float xScale = (float) resultWidth / sourceWidth;
//		float yScale = (float) resultHeight / sourceHeight;
//
//		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
//
//		RenderedOp op = AffineDescriptor.create(
//			bufferedimage, AffineTransform.getScaleInstance(xScale, yScale),
//			Interpolation.getInstance(Interpolation.INTERP_BILINEAR), null,
//			scaleHints);
//
//		bufferedimage = op.getAsBufferedImage();
//
//		ImageEncoder encoder =
//			ImageCodec.createImageEncoder("jpeg", outputStream, jpegHints);
//
//		encoder.encode(bufferedimage);
//		outputStream.close();
//		data = outputStream.toByteArray();
//	}
//
//	public byte[] getBytes() {
//		return data;
//	}
//
//	public int getHeight() {
//		return Toolkit.getDefaultToolkit().createImage(data).getHeight(null);
//	}
//
//	public int getWidth() {
//		return Toolkit.getDefaultToolkit().createImage(data).getWidth(null);
//	}
//}