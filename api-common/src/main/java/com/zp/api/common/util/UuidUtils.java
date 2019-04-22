package com.zp.api.common.util;

import java.util.Random;
import java.util.UUID;

/**
 *  Title: UuidUtils.java
 *
 *  @author zp
 *
 *  Description: 系统唯一号
 *
 *  Date:2018年1月12日
 */
public abstract class UuidUtils {

	public static String getUuid(){
		 String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
	        String res = "";
	        for (int i = 0; i < 16; i++)
	        {
	            Random rd = new Random();
	            res += chars.charAt(rd.nextInt(chars.length() - 1));
	        }
	        return res;
	}

}

