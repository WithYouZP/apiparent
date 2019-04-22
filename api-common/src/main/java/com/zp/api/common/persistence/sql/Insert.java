package com.zp.api.common.persistence.sql;

import com.zp.api.common.exception.DaoException;
import com.zp.api.common.persistence.jpa.AbstractBuilder;
import com.zp.api.common.util.ObjectUtils;

import java.util.Map;



/**
 *  Title: Insert.java
 *
 *  @author zp
 *
 *  Description: 封装springjdbc新增操作，生成insert语句
 *
 *  Date:2018年1月12日
 */
public class Insert extends AbstractBuilder {

	public Insert(String table, Map<String, Object> data,String id) {
		super(table, data,id);
	}

	/**
	 * 生成insert语句.
	 */
	@Override
	public Insert build() throws DaoException {
		StringBuilder insql = new StringBuilder().append("insert into ").append(table).append("(");
		StringBuilder vasql = new StringBuilder().append("(");
		boolean first = true;
		for (Map.Entry<String, Object> entry : data.entrySet()) {
			String key = entry.getKey();
			//主键是自增的所以跳过插入 id
			if (ObjectUtils.equals(key, id)){
				Object value = entry.getValue();
				if (ObjectUtils.isEmpty(value)){
					continue;
				}
			}
			//第一次进入不拼接 ,号
			if (!first) {
				insql.append(",");
				vasql.append(",");
			} else {
				first = false;
			}
			//先拼接表(中的 列名)
			insql.append(entry.getKey());
			//在拼接 values(中的值)
			vasql.append(":").append(entry.getKey());
		}
		insql.append(") values ").append(vasql).append(")");
		sql = insql.toString();
		return this;
	}
}
