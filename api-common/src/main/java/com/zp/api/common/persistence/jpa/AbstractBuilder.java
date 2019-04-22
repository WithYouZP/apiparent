package com.zp.api.common.persistence.jpa;

import com.zp.api.common.exception.DaoException;

import java.util.Map;
/**
 * 根据对象组装sql语句
 * @author zp
 *
 */
public abstract class AbstractBuilder {
	/**
	 * 表名
	 */
	protected final String table;
	/**
	 * 数据。key对应数据库的字段
	 */
	protected final Map<String, Object> data;
	/**
	 * 对应数据库的主键名
	 * 修改，根据ID查询，删除时需要传递此参数
	 */
	protected final String id;
	/**
	 * 生成的sql语句
	 */
	protected String sql;
	
	public AbstractBuilder(String table, Map<String, Object> data,String id) {
		this.table = table;
		this.data = data;
		this.id = id;
	}
	/**
	 * 抽象方法，组装sql语句，子类必须实现
	 * @return
	 * @throws Exception
	 */
	public abstract AbstractBuilder build() throws DaoException;
	
	public String getSql() {
		return sql;
	}
}
