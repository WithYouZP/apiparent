package com.zp.api.common.persistence.jpa;

import com.zp.api.common.exception.DaoException;
import com.zp.api.common.parameter.Page;
import com.zp.api.common.persistence.refect.RefectBudiler;
import com.zp.api.common.persistence.sql.Delete;
import com.zp.api.common.persistence.sql.Insert;
import com.zp.api.common.persistence.sql.Query;
import com.zp.api.common.persistence.sql.Update;
import com.zp.api.common.util.BeanUtils;
import com.zp.api.common.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;


public class DialectOperations extends BaseDialectOperations{
	
	protected Map<String, Object> data;
	
	@Override
	protected BaseDialectOperations build(String dbType,String pageSql, Page<?> page) throws DaoException {
		if (ObjectUtils.isEmpty(dbType)) {
			throw new DaoException("dbType must be not null.");
		}
		this.initDialect(dbType);
		if (Objects.equals("oracle", dbType)) {
			this.pageSql = this.dialect.getLimitString(pageSql, page.getFirstEntityIndex(), page.getLastEntityIndex(),
					page.getPageSize());
		}
		if (Objects.equals("mysql", dbType)) {
			this.pageSql = this.dialect.getLimitString(pageSql, page.getFirstEntityIndex(), page.getPageSize(), 0);
		}
		return this;
	}

	@Override
	public <E> String getInsertSql(E e, Class<?> cls) throws DaoException {
		String table = RefectBudiler.getTable(cls);
		String id = RefectBudiler.getId(cls);
		this.data = BeanUtils.convertColumn(e);
		return new Insert(table, data, id).build().getSql();
	}

	@Override
	public <E> String getUpdateSql(E e, Class<?> cls) throws DaoException {
		String table = RefectBudiler.getTable(cls);
		String id = RefectBudiler.getId(cls);
		this.data = BeanUtils.convertColumn(e);
		return new Update(table, data, id).build().getSql();
	}

	@Override
	public <PK> String getDeteleSql(PK pk, Class<?> cls) throws DaoException {
		String table = RefectBudiler.getTable(cls);
		String id = RefectBudiler.getId(cls);
		this.data = new ConcurrentHashMap<String, Object>();
		data.put(id, pk);
		return new Delete(table, data, id).build().getSql();
	}
	
	@Override
	public <PK> String getDeteleSql(PK pk, int delflag, Class<?> cls) throws DaoException {
		String table = RefectBudiler.getTable(cls);
		String id = RefectBudiler.getId(cls);
		this.data = new HashMap<String, Object>();
		data.put(id, pk);
		return new Delete(table, data, id).build(delflag).getSql();
	}
	
	@Override
	protected <PK> String getQuerySql(PK pk, Class<?> cls) throws DaoException {
		String table = RefectBudiler.getTable(cls);
		String id = RefectBudiler.getId(cls);
		this.data = new ConcurrentHashMap<String, Object>();
		data.put(id, pk);
		return new Query(table, null, id).build().getSql();
	}
	
	@Override
	protected <PK> String getQuerySql(PK pk, int delflag, Class<?> cls) throws DaoException {
		String table = RefectBudiler.getTable(cls);
		String id = RefectBudiler.getId(cls);
		this.data = new ConcurrentHashMap<String, Object>();
		data.put(id, pk);
		return new Query(table, null, id).build(delflag).getSql();
	}
	
	public Map<String, Object> getData(){
		return data;
	}
}
