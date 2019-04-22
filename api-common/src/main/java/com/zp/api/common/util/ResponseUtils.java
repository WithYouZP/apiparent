package com.zp.api.common.util;

import com.zp.api.common.parameter.Page;
import com.zp.api.common.parameter.ResponseCode;
import com.zp.api.common.parameter.ResponseEntity;

import java.util.List;



/**
 *  Title: ResponseUtils.java
 *
 *  @author zp
 *
 *  Description: 接口返回类型工具类
 *
 *  Date:2018年1月12日
 */
public abstract class ResponseUtils {
	
	/**
	 * 返回分页数据
	 * @param code 请求状态
	 * @param message   消息提示
	 * @param page   分页对象
	 * @return
	 */
	public static <T> ResponseEntity<List<T>> createRet(ResponseCode code, String message, Page<T> page) {
		ResponseEntity<List<T>> responseEntity = new ResponseEntity<List<T>>(code.getValue(), message,null);
		if (page == null) {
			return responseEntity;
		}
		responseEntity.setRows(page.getData());
		responseEntity.setTotal(ObjectUtils.convertToLong(page.getTotalRecord()));
		responseEntity.setPageNo(page.getPageNo());
		responseEntity.setPageSize(page.getPageSize());
		return responseEntity;
	}
	
	public static <T> ResponseEntity<T> createRet(ResponseCode code, String message, T data) {
		ResponseEntity<T> responseEntity = new ResponseEntity<T>(code.getValue(), message, data);
		return responseEntity;
	}
	
	public static <T> ResponseEntity<List<T>> createRet(ResponseCode code, String message, List<T> data) {
		ResponseEntity<List<T>> responseEntity = new ResponseEntity<List<T>>(code.getValue(), message, data);
		return responseEntity;
	}
	
	public static <T> ResponseEntity<T> createRet(ResponseCode code, String message) {
		ResponseEntity<T> responseEntity = new ResponseEntity<T>(code.getValue(), message);
		return responseEntity;
	}
}
