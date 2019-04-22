package com.zp.api.common.persistence.jpa;

import com.zp.api.common.dialect.Dialect;
import com.zp.api.common.dialect.db.MysqlDialect;
import com.zp.api.common.dialect.db.OracleDialect;
import com.zp.api.common.exception.DaoException;
import com.zp.api.common.parameter.Page;
import com.zp.api.common.util.ObjectUtils;

import java.util.Objects;


/**
 *  Title: BaseDialectOperations.java
 *
 *  @author zp
 *
 *  Description: sql生成父类
 *
 *  Date:2018年1月10日
 */
public abstract class BaseDialectOperations {
	
	protected Dialect dialect;
	
	protected String pageSql;

	/**
	 * 初始化方言解析器
	 */
	protected void initDialect(String dbType) {
		Dialect dialect = null;
		if (Objects.equals("oracle", dbType)) {
			dialect = new OracleDialect();
		}
		if (Objects.equals("mysql", dbType)) {
			dialect = new MysqlDialect();
		}
		if (ObjectUtils.isEmpty(dialect)) {
			throw new DaoException("dialect is error.");
		}
		this.dialect = dialect;
	}
	
	protected abstract BaseDialectOperations build(String dbType,String pageSql, Page<?> page) throws DaoException;
	
	public String getPageSql() {
		return pageSql;
	}
	/**
	 * 生成插入sql语句
	 * @param t
	 * @param cls
	 * @return
	 * @throws DaoException
	 */
	public abstract <T> String getInsertSql(T t,Class<?> cls) throws DaoException;
	
	/**
	 * 生成更新语句
	 * @param t
	 * @param cls
	 * @return
	 * @throws DaoException
	 */
	public abstract <T> String getUpdateSql(T t,Class<?> cls) throws DaoException;
	
	/**
	 * 生成物理删除语句
	 * @param pk 主键
	 * @param cls
	 * @return
	 * @throws DaoException
	 */
	public abstract <PK> String getDeteleSql(PK pk,Class<?> cls) throws DaoException;
	
	/**
	 * 生成逻辑删除语句
	 * @param pk 主键
	 * @param cls
	 * @param delflag 删除标识.
	 * @return
	 * @throws DaoException
	 */
	public abstract <PK> String getDeteleSql(PK pk,int delflag,Class<?> cls) throws DaoException;
	
	/**
	 * 生成查询语句
	 * @param pk 主键
	 * @param cls
	 * @return
	 * @throws DaoException
	 */
	protected abstract <PK> String getQuerySql(PK pk, Class<?> cls) throws DaoException;
	/**
	 * 根据删除标识生成查询语句
	 * @param pk 主键
	 * @param cls
	 * @param delflag
	 * @return
	 * @throws DaoException
	 */
	protected abstract <PK> String getQuerySql(PK pk,int delflag, Class<?> cls) throws DaoException;
}
