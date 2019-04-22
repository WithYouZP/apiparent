package com.zp.api.core.base.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.zp.api.common.exception.DaoException;
import com.zp.api.common.parameter.Page;
import com.zp.api.common.persistence.annotation.SqlMonitor;
import com.zp.api.common.persistence.jpa.DialectOperations;
import com.zp.api.common.util.ObjectUtils;
import com.zp.api.core.base.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;


/**
 * Title: JdbcTemplate.java
 *
 * @author zp
 *
 * Description: 封装jdbc数据库操作
 *
 * Date:2018年1月10日
 */
@Configuration
public class JdbcTemplateOperations<E extends BaseEntity<PK>,PK extends Serializable> extends DialectOperations {

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;
	
	@Value("${db.type}")
	private String dbType;

	@SqlMonitor
	public int execute(String sql, Map<String, Object> parameter) throws DaoException {
		Integer result = 0;
		result = namedParameterJdbcTemplate.update(sql, parameter);
		return result;
	}
	
	/**
	 * 执行新增sql返回主键
	 * @param sql
	 * @param sqlParameterSource
	 * @return
	 * @throws DaoException
	 */
	@SqlMonitor
	public int execute(String sql,MapSqlParameterSource sqlParameterSource) throws DaoException {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		namedParameterJdbcTemplate.update(sql, sqlParameterSource, keyHolder);
		int id = keyHolder.getKey().intValue();
		return id;
	}

	public String getInsertSql(E e) throws DaoException {
		Class<?> cls = e.getClass();
		String sql = this.getInsertSql(e, cls);
		return sql;
	}
	
	public String getUpdateSql(E e) throws DaoException {
		Class<?> cls = e.getClass();
		String sql = this.getUpdateSql(e, cls);
		return sql;
	}
	
	public String getDeleteSql(PK pk,Class<?> cls) throws DaoException {
		String sql = this.getDeteleSql(pk, cls);
		return sql;
	}
	
	public String getDeleteSql(PK pk,int delFlag,Class<?> cls) throws DaoException {
		String sql = this.getDeteleSql(pk,delFlag,cls);
		return sql;
	}
	
	public String getQueryOneSql(PK pk,Class<E> cls) throws DaoException {
		String sql = this.getQuerySql(pk, cls);
		return sql;
	}

	public String getQueryOneSql(PK pk,int delflag,Class<E> cls) throws DaoException {
		String sql = this.getQuerySql(pk,delflag, cls);
		return sql;
	}
	
	@SqlMonitor
	public List<Map<String, Object>> queryForList(String sql,Map<String, Object> parameter) throws DaoException {
		this.processParameters(parameter);
		return namedParameterJdbcTemplate.queryForList(sql, parameter);
	}
	
	public String getQueryPageSql(String sql, Page<?> page, Map<String, Object> parameter) throws DaoException {
		String pageSql = this.build(dbType, sql, page).getPageSql();
		return pageSql;
	}
	
	public String getCountSql(String sql) throws DaoException {
		String countSql = "select  count(*) as cn from (" + sql + ") alias_";
		return countSql;
	}
	
	/**
	 * 批量增，删，改
	 * @param sql
	 * @param parameter
	 * @return
	 * @throws DaoException
	 */
	@SuppressWarnings("unchecked")
	public int[] executeBatch(String sql, List<Map<String, Object>> parameter) throws DaoException {
		try {
			if (ObjectUtils.isEmpty(parameter)) {
				throw new DaoException("parameter can not be null.");
			}
			Map<String, Object>[] paramArray = new Map[parameter.size()];
			int index = 0;
			for (Map<String, Object> paramMap : parameter) {
				paramArray[index++] = paramMap;
			}
			return namedParameterJdbcTemplate.batchUpdate(sql, paramArray);
		} catch (DaoException e) {
			throw new DaoException(e);
		}
	}

	private void processParameters(Map<String, Object> parameter) {
		if (ObjectUtils.isEmpty(parameter)){
			return;
		}
		for (Map.Entry<String, Object> entry : parameter.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (ObjectUtils.isNotEmpty(value)) {
				if (key.indexOf("like") == -1) {
					continue;
				}
				value = "%" + value + "%";
				parameter.put(key, value);
			}
		}
	}
}
