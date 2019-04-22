package com.zp.api.common.util;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

/**
 * Title: ImageUtils.java
 *
 * @author zp
 *
 *         Description: 图片上传工具类
 *
 *         Date:2018年1月12日
 */
public abstract class ImageUtils {

	private static final String IMAGE_DATA = "data:image/";
	private static final String BASE64 = "base64,";

	public static String upload(String fileName, String path, String content) {
		if (ObjectUtils.isEmpty(content)) {
			throw new RuntimeException("image content nust be null.");
		}
		if (isValidImageContent(content)) {
			String url = imageUpload(fileName, content, path);
			return url;
		}
		String url = imageUpload(fileName, content, ".jpg", path);
		return url;
	}

	public static boolean isValidImageContent(String memberImageContent) {
		if (memberImageContent.contains("base64")) {
			return true;
		}
		return false;
	}

	/**
	 * 图片上传
	 * 
	 * @param fileName
	 *            图片名称
	 * @param imageContent
	 *            图片内容
	 * @param path
	 *            图片路径
	 * @return 返回的图片url
	 */
	public static String imageUpload(String name, String content, String path) {
		String code = getImageContent(content, 0);
		String ct = getImageContent(content, 1);
		String imageType = getImageType(code);
		String url = imageUpload(name, ct, imageType, path);
		return url;
	}

	public static String imageUpload(String name, String content, String type, String path) {
		if (ObjectUtils.isEmpty(type)) {
			throw new RuntimeException("image type is null.");
		}
		byte[] b = decoderImage(content);
		if (ObjectUtils.isEmpty(b)) {
			throw new RuntimeException("image content is null.");
		}
		String url = FileUtils.createFile(name, b, path, type);
		return url;
	}

	/**
	 * 图片解密
	 * 
	 * @param imageContent
	 *            图片内容
	 * @return
	 */
	public static byte[] decoderImage(String imageContent) {
		byte[] b = Base64Utils.decodeFromString(imageContent);
		return b;
	}

	/**
	 * 图片base64位加密
	 * 
	 * @param path
	 * @return
	 */
	public static String encoderImage(String path) {
		if (ObjectUtils.isEmpty(path)) {
			throw new RuntimeException("image path is not can be null.");
		}
		InputStream inputStream = null;
		byte[] data = null;
		try {
			File file = new File(path);
			if (!file.exists()) {
				return null;
			}
			inputStream = new FileInputStream(path);
			data = new byte[inputStream.available()];
			inputStream.read(data);
			inputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		String imageContent = Base64Utils.encodeToString(data);
		return IMAGE_DATA.concat("png").concat(";").concat(BASE64).concat(imageContent);
	}

	/**
	 * 获取图片内容
	 * 
	 * @param content
	 * @return
	 */
	private static String getImageContent(String content, int i) {
		String[] contents = content.split(",");
		return contents[i];
	}

	/**
	 * 获取图片后缀
	 * 
	 * @param code
	 * @return
	 */
	private static String getImageType(String code) {
		String type = code.substring(code.indexOf("/") + 1, code.indexOf(";"));
		return ".".concat(type);
	}

	/**
	 * 删除图片
	 * 
	 * @param url
	 */
	public static void delImage(String url) {
		FileUtils.delFile(url);
	}
	
	/**
	 * 跟图片添加文字水印
	 * @param text 水印内容
	 * @param file  添加水印的图片
	 * @param target 添加水印后重新生成图片的路径
	 * @param color	 水印颜色
	 * @param font  水印字体
	 */
	public static void addTextWaterMark(String text,File file,String target,Color color,Font font){
		try {
			Image image = ImageIO.read(file);
			int imgWidth = image.getWidth(null);
			int imgHeight = image.getHeight(null);
			BufferedImage bufImg = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();  
            g.drawImage(image, 0, 0, imgWidth, imgHeight, null);  
            g.setColor(color); //根据图片的背景设置水印颜色  
            g.setFont(font); 
            int x = imgWidth - 2 * getWatermarkLength(text, g);    
            int y = imgHeight - getWatermarkLength(text, g);    
            g.drawString(text, x, y);
            g.dispose();    
            FileOutputStream outImgStream = new FileOutputStream(target);    
            ImageIO.write(bufImg, "jpg", outImgStream); 
            outImgStream.flush();    
            outImgStream.close();
            file.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static int getWatermarkLength(String waterMarkContent, Graphics2D g) {    
	    return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());    
	}
}
