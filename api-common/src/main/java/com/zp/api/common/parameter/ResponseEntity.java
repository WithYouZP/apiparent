package com.zp.api.common.parameter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "ResponseEntity", description = "返回参数")
public class ResponseEntity<T> {

	@ApiModelProperty(value = "请求状态,01-成功，02-失败，0003-登录失效", dataType = "String", example = "01", required = true)
	private String code;

	@ApiModelProperty(value = "消息提示", dataType = "String", example = "成功", required = true)
	private String msg;

	@JsonInclude(Include.NON_NULL)
	@ApiModelProperty(value = "返回数据", dataType = "Object", example = "", required = true)
	private T rows;

	@ApiModelProperty(value = "分页数据总条数", dataType = "long", example = "1000", required = true)
	private Long total;

	@ApiModelProperty(value = "分页当前页数", dataType = "int", example = "1", required = true)
	private Integer pageNo;

	@ApiModelProperty(value = "分页显示条数", dataType = "int", example = "10", required = true)
	private Integer pageSize;

	public ResponseEntity() {
		super();
	}

	public ResponseEntity(String code, String msg) {
		this(code, msg, null);
	}

	public ResponseEntity(String code, String msg, T rows) {
		super();
		this.code = code;
		this.msg = msg;
		this.rows = rows;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getRows() {
		return rows;
	}

	public void setRows(T rows) {
		this.rows = rows;
	}

	public Long getTotal() {
		return total;
	}

	public void setTotal(Long total) {
		this.total = total;
	}

	public Integer getPageNo() {
		return pageNo;
	}

	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

}
