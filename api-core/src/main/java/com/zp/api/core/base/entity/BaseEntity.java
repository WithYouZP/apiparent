package com.zp.api.core.base.entity;

import java.io.Serializable;
import java.util.Date;

import com.zp.api.common.persistence.annotation.Column;
import com.zp.api.common.persistence.annotation.Id;
import com.zp.api.common.util.DateUtils;
import com.zp.api.common.util.ObjectUtils;
import com.zp.api.core.context.BussinessContextHolder;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * Title: BaseEntity.java
 *
 * @author zp
 *
 *         Description: 实体类父类信息
 *
 *         Date:2017年11月30日
 */
public class BaseEntity<PK extends Serializable> implements Serializable {

	private static final long serialVersionUID = 3948684224883283890L;

	/**
	 * 主键
	 */
	private PK id;
	/**
	 * 创建人
	 */
	private Integer createBy;
	/**
	 * 创建时间
	 */
	private Date createDate;
	/**
	 * 更新人
	 */
	private Integer updateBy;

	/**
	 * 更新时间
	 */
	private Date updateDate;
	/**
	 * 备注
	 */
	private String remark;;

	@Id
	@Column(name = "id")
	public PK getId() {
		return id;
	}

	public void setId(PK id) {
		this.id = id;
	}

	@Column(name = "create_by")
	public Integer getCreateBy() {
		if (ObjectUtils.isEmpty(createBy)) {
			BaseUser info = BussinessContextHolder.getUser();
			if (ObjectUtils.isNotEmpty(info)) {
				return info.getId();
			}
		}
		return createBy;
	}

	public void setCreateBy(Integer createBy) {
		this.createBy = createBy;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "create_date")
	public Date getCreateDate() {
		if (ObjectUtils.isEmpty(createDate)){
			return DateUtils.nowDate();
		}
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "update_by")
	public Integer getUpdateBy() {
		if (ObjectUtils.isEmpty(updateBy)) {
			BaseUser info = BussinessContextHolder.getUser();
			if (ObjectUtils.isNotEmpty(info)) {
				return info.getId();
			}
		}
		return updateBy;
	}

	public void setUpdateBy(Integer updateBy) {
		this.updateBy = updateBy;
	}

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "update_date")
	public Date getUpdateDate() {
		if (ObjectUtils.isEmpty(updateDate))
			return DateUtils.nowDate();
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	@Column(name = "remark")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}
}
