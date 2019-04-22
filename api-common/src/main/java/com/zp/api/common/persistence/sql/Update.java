package com.zp.api.common.persistence.sql;

import com.zp.api.common.exception.DaoException;
import com.zp.api.common.persistence.jpa.AbstractBuilder;
import com.zp.api.common.util.ObjectUtils;

import java.util.Map;
import java.util.Objects;


public class Update extends AbstractBuilder {

	public Update(String table, Map<String, Object> data,String id) {
		super(table, data,id);
	}

	@Override
	public Update build() throws DaoException {
		if(ObjectUtils.isEmpty(id)){
			throw new RuntimeException("id is not exist!");
		}
		StringBuilder insql = new StringBuilder().append("update ").append(table).append(" set ");
		boolean first = true;
		for(Map.Entry<String, Object> entry : data.entrySet()){
			String key = entry.getKey();
			if(Objects.equals(key, id)){
				continue;
			}
			if (!first) {
				insql.append(",");
			} else {
				first = false;
			}
			insql.append(key).append(" = :").append(key);
		}
		insql.append(" where ").append(id).append(" = :").append(id);
		sql = insql.toString();
		return this;
	}

}
