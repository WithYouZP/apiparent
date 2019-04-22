
package com.zp.api.common.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *  Title: MD5.java
 *
 *  @author zp
 *
 *  Description: MD5加密
 *  执行速度快，但安全系数较低
 *
 *  Date:2018年1月12日
 */
public abstract class MD5 {

	/**
	 * Encodes a string
	 * MD5加密 32位小写
	 * @param str String to encode
	 * @return Encoded String
	 * @throws NoSuchAlgorithmException
	 */
	public static String encrypt(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("String to encript cannot be null or zero length");
		}
		StringBuffer hexString = new StringBuffer();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();
			for (int i = 0; i < hash.length; i++) {
				if ((0xff & hash[i]) < 0x10) {
					hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
				} else {
					hexString.append(Integer.toHexString(0xFF & hash[i]));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}

	
	private static final char HEX_DIGITS[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',  
	        'A', 'B', 'C', 'D', 'E', 'F' };  
	
	private static String toHexString(byte[] b) {  
	    StringBuffer sb = new StringBuffer(b.length * 2);    
	    for (int i = 0; i < b.length; i++) {    
	        sb.append(HEX_DIGITS[(b[i] & 0xf0) >>> 4]);    
	        sb.append(HEX_DIGITS[b[i] & 0x0f]);    
	    }    
	    return sb.toString();    
	}
	
	/**
	 * MD5加密 32位大写
	 */
	public static String encryptToUp(String s) {  
	    try {  
	        MessageDigest digest = MessageDigest.getInstance("MD5");
	        digest.update(s.getBytes());  
	        byte messageDigest[] = digest.digest();  
	                                  
	        return toHexString(messageDigest);  
	    } catch (NoSuchAlgorithmException e) {  
	        e.printStackTrace();  
	    }              
	    return "";  
	}
	
	public static void main(String[] args) {
		String pass = "adadadwwa13245";
		String encry = MD5.encrypt(pass);
		System.out.println("加密后:"+encry);
		
		String dencry = MD5.encryptToUp(pass);
		System.out.println("加密后:"+dencry);
	}
}
