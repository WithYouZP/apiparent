package com.zp.api.common.parameter;

/**
 *  Title: ResponseCode.java
 *
 *  @author zp
 *
 *  Description: http请求 返回状态
 *
 *  Date:2018年1月31日
 */
public enum ResponseCode {

	SUCCESS("01"), FAIL("02"), INVALID("0003");

	private String value;

	private ResponseCode(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
