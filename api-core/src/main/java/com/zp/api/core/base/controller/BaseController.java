package com.zp.api.core.base.controller;

import com.zp.api.common.constant.ConfigConstant;
import com.zp.api.common.exception.BusinessException;
import com.zp.api.common.parameter.ResponseCode;
import com.zp.api.common.parameter.ResponseEntity;
import com.zp.api.common.util.MD5;
import com.zp.api.common.util.ResponseUtils;
import com.zp.api.core.base.entity.BaseUser;
import com.zp.api.core.context.BussinessContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class BaseController {

	private Logger logger = LoggerFactory.getLogger(BaseController.class);

	public <T> ResponseEntity<T> handException(RuntimeException e) {
		if (e instanceof BusinessException) {
			logger.error(e.getMessage());
			return ResponseUtils.createRet(ResponseCode.FAIL, e.getMessage());
		}
		logger.error(e.getCause().getMessage());
		return ResponseUtils.createRet(ResponseCode.FAIL, ConfigConstant.SYSTEM_MSG_ERROR);
	}
	
	public String gerRedisKey(String code) {
		BaseUser user = BussinessContextHolder.getUser();
		String username = user.getUsername();
		String key = MD5.encrypt(username.concat(code));
		return key;
	}
	
	public String gerRedisKey(String key,String code) {
		return MD5.encrypt(key.concat(code));
	}
}
