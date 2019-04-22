package com.zp.api.common.util;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * Title: XmlUtils.java
 *
 * @author zp
 *
 *         Description: Xml、javaBean操作工具类
 *
 *         Date:2018年1月12日
 */
public abstract class XmlUtils {

	/**
	 * 将javaBean序列化为xml
	 * 
	 * @param clz
	 * @param obj
	 * @param encoding
	 * @return
	 */
	public static String toXml(Class<?> clz, Object obj, String encoding) {
		try {
			StringWriter writer = new StringWriter();
			createMarshaller(clz, encoding).marshal(obj, writer);
			return writer.toString();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将javaBean序列化为xml,默认utf8编码
	 * 
	 * @param clz
	 * @param obj
	 * @return
	 */
	public static String toXml(Class<?> clz, Object obj) {
		try {
			return toXml(clz, obj, null);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将Xml字符串序列化为javaBean
	 * 
	 * @param clz
	 * @param xml
	 * @return
	 */
	public static Object fromXml(Class<?> clz, String xml) {
		try {
			StringReader reader = new StringReader(xml);
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { clz });
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			return unmarshaller.unmarshal(reader);
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 将xml文件转为对象
	 * 
	 * @param clz
	 * @param xmlPath
	 *            xml文件路径
	 * @return
	 */
	public static Object xmlToBean(Class<?> clz, String xmlPath) {
		try {
			JAXBContext context = JAXBContext.newInstance(clz);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Object object = unmarshaller.unmarshal(new File(xmlPath));
			return object;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}

	}

	public static Marshaller createMarshaller(Class<?> clz, String encoding) {
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(new Class[] { clz });

			Marshaller marshaller = jaxbContext.createMarshaller();

			marshaller.setProperty("jaxb.formatted.output", Boolean.TRUE);
			if (ObjectUtils.isNotEmpty(encoding)) {
				marshaller.setProperty("jaxb.encoding", encoding);
			} else {
				marshaller.setProperty("jaxb.encoding", "utf-8");
			}
			return marshaller;
		} catch (JAXBException e) {
			throw new RuntimeException(e);
		}
	}
}
