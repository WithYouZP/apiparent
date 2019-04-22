package com.zp.api.common.persistence.sql;

import com.zp.api.common.exception.DaoException;
import com.zp.api.common.persistence.jpa.AbstractBuilder;

import java.util.Map;


/**
 *  Title: Delete.java
 *
 *  @author zp
 *
 *  Description: 封装springjdbc删除操作，生成删除sql语句
 *
 *  Date:2018年1月12日
 */
public class Delete extends AbstractBuilder {

	public Delete(String table, Map<String, Object> data, String id) {
		super(table, data, id);
	}
	
	/**
	 * 生成物理删除sql语句
	 */
	@Override
	public Delete build() throws DaoException {
		StringBuilder sb = new StringBuilder("delete from ").append(table);
		sb.append(" where ").append(id).append(" = :").append(id);
		sql = sb.toString();
		return this;
	}

	/**
	 * 生成逻辑删除sql语句
	 * @param delFlag,删除标识
	 * @return
	 * @throws DaoException
	 */
	public Delete build(int delFlag) throws DaoException {
		StringBuilder sb = new StringBuilder("delete from ").append(table);
		sb.append(" where ").append(id).append(" = :").append(id);
		sb.append(" and del_flag = ").append(delFlag);
		sql = sb.toString();
		return this;
	}
}
