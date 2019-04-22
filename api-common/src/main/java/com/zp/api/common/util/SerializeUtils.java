package com.zp.api.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Title: SerializeUtils.java
 *
 * @author zp
 *
 *         Description: 序列化、反序列化
 *
 *         Date:2018年1月12日
 */
public abstract class SerializeUtils {

	/**
	 * 序列化
	 * 
	 * @param object
	 * @return
	 */
	public static byte[] serialize(Object object) {
		if (ObjectUtils.isEmpty(object)) {
			return null;
		}
		ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(baos);
			oos.writeObject(object);
			oos.flush();
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to serialize object of type: " + object.getClass(), e);
		}
		return baos.toByteArray();
	}

	/**
	 * 反序列化
	 * 
	 * @param bytes
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		if (ObjectUtils.isEmpty(bytes)) {
			return null;
		}
		try {
			ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(bytes));
			return ois.readObject();
		} catch (IOException e) {
			throw new IllegalArgumentException("Failed to deserialize object", e);
		} catch (ClassNotFoundException e) {
			throw new IllegalStateException("Failed to deserialize object type", e);
		}
	}
}
