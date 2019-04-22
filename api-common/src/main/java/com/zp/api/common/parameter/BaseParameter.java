package com.zp.api.common.parameter;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 *  Title: BaseParameter.java
 *
 *  @author zp
 *
 *  Description: 分页参数
 *
 *  Date:2017年11月30日
 */
@ApiModel(value = "BaseParameter", description = "分查询参数")
public class BaseParameter<T> {

	@ApiModelProperty(value = "分页父类", dataType = "BasePage", example = "", required = true)
	private BasePage page;
	
	@ApiModelProperty(value = "分页查询条件", dataType = "object", example = "", required = true)
	private T parameters;

	public BasePage getPage() {
		return page;
	}

	public void setPage(BasePage page) {
		this.page = page;
	}

	public T getParameters() {
		return parameters;
	}

	public void setParameters(T parameters) {
		this.parameters = parameters;
	}
	
}
