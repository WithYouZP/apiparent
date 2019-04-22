package com.zp.api.common.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.util.ResourceUtils;

/**
 * Title: FileUtils.java
 *
 * @author zp
 *
 *         Description: 文件上传工具类
 *
 *         Date:2018年1月12日
 */
public abstract class FileUtils {
	
	public static String createFile(String fileName, String fileContent, String path, String fileType) {
		isValidFile(fileName,fileContent, path, fileType);
		if (!fileType.contains(".")){
			fileType = ".".concat(fileType);
		}
		byte[] b = Base64Utils.decodeFromString(fileContent);
		if (ObjectUtils.isWindows()){
			path = getPath(path);
		}
		File file = new File(path.concat(fileName).concat(fileType));
		if (!file.exists())
			file.getParentFile().mkdirs();
		try {
			file.createNewFile();
			String url = writeFile(file, b);
			return url;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成文件
	 * 
	 * @param fileName
	 *            文件名称
	 * @param fileContent
	 *            文件内容
	 * @param path
	 *            文件路径
	 * @param fileType
	 *            文件后缀名
	 * @return
	 */
	public static String createFile(String fileName, byte[] fileContent, String path, String fileType) {
		isValidFile(fileName,fileContent, path, fileType);
		if (ObjectUtils.isWindows()){
			path = getPath(path);
		}
		File file = new File(path.concat(fileName).concat(fileType));
		if (!file.exists())
			file.getParentFile().mkdirs();
		try {
			file.createNewFile();
			String url = writeFile(file, fileContent);
			return url;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 上传文件返回文件
	 * @param fileName
	 * @param fileContent
	 * @param path
	 * @param fileType
	 * @return
	 */
	public static File create(String fileName, byte[] fileContent, String path, String fileType){
		isValidFile(fileName,fileContent, path, fileType);
		if (ObjectUtils.isWindows()){
			path = getPath(path);
		}
		File file = new File(path.concat(fileName).concat(fileType));
		if (!file.exists())
			file.getParentFile().mkdirs();
		try {
			file.createNewFile();
			writeFile(file, fileContent);
			return file;
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	private static void isValidFile(String fileName,String fileContent, String path, String fileType) {
		if (ObjectUtils.isEmpty(fileContent)) {
			throw new RuntimeException("文件名不能空!");
		}
		if (ObjectUtils.isEmpty(fileContent)) {
			throw new RuntimeException("文件内容不能空!");
		}
		if (ObjectUtils.isEmpty(path)) {
			throw new RuntimeException("上传路径不能为空!");
		}
		if (ObjectUtils.isEmpty(fileType)) {
			throw new RuntimeException("文件类型不能为空!");
		}
	}

	private static void isValidFile(String fileName,byte[] fileContent, String path, String fileType) {
		if (ObjectUtils.isEmpty(fileContent)) {
			throw new RuntimeException("文件名不能空!");
		}
		if (ObjectUtils.isEmpty(fileContent)) {
			throw new RuntimeException("文件内容不能空!");
		}
		if (ObjectUtils.isEmpty(path)) {
			throw new RuntimeException("上传路径不能为空!");
		}
		if (ObjectUtils.isEmpty(fileType)) {
			throw new RuntimeException("文件类型不能为空!");
		}
	}

	private static String getPath(String path) {
		if (path.indexOf("/") > 0) {
			path = StringUtils.replace(path, "/", "\\");
		}
		String s = path.substring(path.length() - 1, path.length());
		if (!s.equals("\\")) {
			path = path.concat("\\");
		}
		return path;
	}

	/**
	 * 删除文件
	 * 
	 * @param url
	 */
	public static void delFile(String url) {
		File file = new File(url);
		if (file.exists() && !file.isDirectory()) {
			file.delete();
		}
	}

	/**
	 * 将内容写入文件
	 * 
	 * @param file
	 * @param fileContent
	 * @return
	 */
	public static String writeFile(File file, byte[] fileContent) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(file);
			out.write(fileContent);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return file.getName();
	}
	
	public static String getResourcesRoute() {
		try {
			File path = new File(ResourceUtils.getURL("classpath:").getPath());
			return path.getAbsolutePath();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getFileUploadPath(String url) {
		String path = getResourcesRoute();
		if (ObjectUtils.isEmpty(path)) {
			return null;
		}
		File upload = new File(path,url);
		if (!upload.exists()) {
			upload.mkdirs();
		}
		return upload.getAbsolutePath();
	}
}
