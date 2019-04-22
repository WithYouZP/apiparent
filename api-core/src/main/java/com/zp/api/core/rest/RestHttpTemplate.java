package com.zp.api.core.rest;

import java.util.Map;

import com.zp.api.common.constant.ConfigConstant;
import com.zp.api.common.exception.BusinessException;
import com.zp.api.common.util.JsonUtils;
import com.zp.api.common.util.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;


@Configuration
public class RestHttpTemplate {

	@Autowired
	private RestTemplate restTemplate;

	private Map<String, Object> value;

	private HttpHeaders headers;

	private ResponseEntity<String> results;

	public RestHttpTemplate createGetHttp(String url, Object json) {
		if (ObjectUtils.isEmpty(url)) {
			throw new RuntimeException("http url is not can be null.");
		}
		if (ObjectUtils.isNotEmpty(json)) {
			url = url.concat("/").concat(ObjectUtils.convertToString(json));
		}

		HttpEntity<String> httpEntity = new HttpEntity<String>(getHeaders());
		results = restTemplate.exchange(url, HttpMethod.GET, httpEntity, String.class);
		return this;
	}

	public RestHttpTemplate createPostHttp(String url, String json) {
		if (ObjectUtils.isEmpty(url)) {
			throw new RuntimeException("http url is not can be null.");
		}
		HttpEntity<String> httpEntity = new HttpEntity<String>(json, getHeaders());
		results = restTemplate.exchange(url, HttpMethod.POST, httpEntity, String.class);
		return this;
	}

	public RestHttpTemplate createHeaders(String tokenValue) {
		this.headers = new HttpHeaders();
		this.headers.setContentType(MediaType.valueOf(ConfigConstant.CONTENT_TYPE));
		this.headers.add(ConfigConstant.SESSION_TOKEN, tokenValue);
		return this;
	}

	public RestHttpTemplate createHeaders(String contentType, String tokenValue) {
		this.headers = new HttpHeaders();
		this.headers.setContentType(MediaType.valueOf(contentType));
		if (ObjectUtils.isNotEmpty(tokenValue)){
			this.headers.add(ConfigConstant.SESSION_TOKEN, tokenValue);
		}
		return this;
	}

	public RestHttpTemplate createHeaders(String contentType, String tokenKey, String tokenValue) {
		this.headers = new HttpHeaders();
		this.headers.setContentType(MediaType.valueOf(contentType));
		this.headers.add(tokenKey, tokenValue);
		return this;
	}

	public HttpHeaders getHeaders() {
		if (ObjectUtils.isNotEmpty(headers)) {
			return this.headers;
		}
		return this.getHeaders(ConfigConstant.CONTENT_TYPE);
	}

	private HttpHeaders getHeaders(String contentType) {
		this.headers = new HttpHeaders();
		this.headers.setContentType(MediaType.valueOf(contentType));
		return this.headers;
	}

	public Map<String, Object> getValue() {
		if (ObjectUtils.isEmpty(results)) {
			throw new BusinessException("http response is null.");
		}
		this.value = JsonUtils.toMap(results.getBody());
		return this.value;
	}

	public String getResult() {
		if (ObjectUtils.isEmpty(results)) {
			throw new BusinessException("http response is null.");
		}
		return results.getBody();
	}
}
