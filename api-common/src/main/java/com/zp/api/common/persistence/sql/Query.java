package com.zp.api.common.persistence.sql;

import com.zp.api.common.exception.DaoException;
import com.zp.api.common.persistence.jpa.AbstractBuilder;

import java.util.Map;


/**
 *  Title: Query.java
 *
 *  @author zzf
 *
 *  Description: 封装springjdbc查询操作，生成查询语句
 *
 *  Date:2018年1月12日
 */
public class Query extends AbstractBuilder {

	public Query(String table, Map<String, Object> data, String id) {
		super(table, data, id);
	}
	
	@Override
	public Query build() throws DaoException {
		StringBuilder sb = new StringBuilder("select * from ").append(table);
		sb.append(" where ").append(id).append(" = :").append(id);
		sql = sb.toString();
		return this;
	}

	public Query build(int delflag) throws DaoException {
		StringBuilder sb = new StringBuilder("select * from ").append(table);
		sb.append(" where ").append(id).append(" = :").append(id);
		sb.append(" and del_flag = ").append(delflag);
		sql = sb.toString();
		return this;
	}
}
