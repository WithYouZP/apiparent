package com.zp.api.common.parameter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "BasePage", description = "分页对象类")
public class BasePage {

	@ApiModelProperty(value = "分页显示条数", dataType = "int", example = "10", required = true)
	protected int pageSize;
	@ApiModelProperty(value = "分页当前页数", dataType = "int", example = "1", required = true)
	protected int pageNo;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

}
