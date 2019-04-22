package com.zp.api.core.base.entity;

import io.swagger.annotations.ApiModelProperty;

public class BaseUser {
	@ApiModelProperty(value = "id", dataType = "Integer", required = false)
	private Integer id;
	@ApiModelProperty(value = "登录名", dataType = "String", required = false)
	private String username; // 登录名，等同于手机号码
	@ApiModelProperty(value = "x-access-token-session", dataType = "String", required = false)
	private String accessTokenSession;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAccessTokenSession() {
		return accessTokenSession;
	}

	public void setAccessTokenSession(String accessTokenSession) {
		this.accessTokenSession = accessTokenSession;
	}

}
