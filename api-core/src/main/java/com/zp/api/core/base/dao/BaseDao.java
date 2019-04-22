package com.zp.api.core.base.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.zp.api.common.constant.ConfigConstant;
import com.zp.api.common.exception.DaoException;
import com.zp.api.common.parameter.BasePage;
import com.zp.api.common.parameter.Page;
import com.zp.api.common.util.BeanUtils;
import com.zp.api.common.util.DateUtils;
import com.zp.api.common.util.ObjectUtils;
import com.zp.api.core.base.entity.BaseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
public class BaseDao<E extends BaseEntity<PK>, PK extends Serializable> {

	@Autowired
	private JdbcTemplateOperations<E, PK> jdbcTemplateOperations;

	protected Class<E> entityClass = this.getEntityType();

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected Class<E> getEntityType() {
		Class cl = getClass();
		Class<E> resultType = null;
		Type superType = cl.getGenericSuperclass();
		if (superType instanceof ParameterizedType) {
			Type[] paramTypes = ((ParameterizedType) superType).getActualTypeArguments();
			if (paramTypes.length > 0) {
				resultType = (Class<E>) paramTypes[0];
			}
		}
		return resultType;
	}
	
	public int execute(String sql, Map<String, Object> parameter) throws DaoException {
		Integer result = 0;
		result = jdbcTemplateOperations.execute(sql, parameter);
		return result;
	}
	
	public E save(E entity) throws DaoException {
		PK id = entity.getId();
		if (id instanceof String) {
			return saveByString(entity);
		}
		return saveByIntger(entity);
	}

	@SuppressWarnings("unchecked")
	private E saveByIntger(E entity) throws DaoException {
		try {
			String sql = jdbcTemplateOperations.getInsertSql(entity);
			MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource();
			sqlParameterSource.addValues(jdbcTemplateOperations.getData());
			Integer id = jdbcTemplateOperations.execute(sql, sqlParameterSource);
			entity.setId((PK) id);
			return entity;
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	/**
	 * 当主键为String类型时.
	 * 
	 * @return
	 * @throws DaoException
	 */
	private E saveByString(E entity) throws DaoException {
		try {
			String sql = jdbcTemplateOperations.getInsertSql(entity);
			jdbcTemplateOperations.execute(sql, jdbcTemplateOperations.getData());
			return entity;
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public E update(E entity) throws DaoException {
		try {
			entity.setUpdateDate(DateUtils.nowDate());
			String sql = jdbcTemplateOperations.getUpdateSql(entity);
			jdbcTemplateOperations.execute(sql, jdbcTemplateOperations.getData());
			return entity;
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public E saveOrUpdate(E entity) throws DaoException {
		try {
			PK id = entity.getId();
			if (ObjectUtils.isEmpty(id)) {
				return save(entity);
			}
			if (id instanceof String) {
				E obj = get(id);
				if (ObjectUtils.isEmpty(obj)) {
					return save(entity);
				}
			}
			return update(entity);
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public int delete(PK pk) throws DaoException {
		try {
			String sql = jdbcTemplateOperations.getDeleteSql(pk, entityClass);
			return jdbcTemplateOperations.execute(sql, jdbcTemplateOperations.getData());
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public int deletePhysics(PK pk) throws DaoException {
		try {
			String sql = jdbcTemplateOperations.getDeleteSql(pk, ConfigConstant.DEL_FLAG_ONE, entityClass);
			return jdbcTemplateOperations.execute(sql, jdbcTemplateOperations.getData());
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public E get(PK pk) throws DaoException {
		try {
			String sql = jdbcTemplateOperations.getQueryOneSql(pk, entityClass);
			List<Map<String, Object>> result = jdbcTemplateOperations.queryForList(sql,
					jdbcTemplateOperations.getData());
			if (ObjectUtils.isEmpty(result)) {
				return null;
			}
			Map<String, Object> resultMap = result.iterator().next();
			return BeanUtils.convertMap(entityClass, resultMap);
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public E getPhysics(PK pk) throws DaoException {
		try {
			String sql = jdbcTemplateOperations.getQueryOneSql(pk, ConfigConstant.DEL_FLAG_ZERO, entityClass);
			List<Map<String, Object>> result = jdbcTemplateOperations.queryForList(sql,
					jdbcTemplateOperations.getData());
			if (ObjectUtils.isEmpty(result)) {
				return null;
			}
			Map<String, Object> resultMap = result.iterator().next();
			return BeanUtils.convertMap(entityClass, resultMap);
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public int executeInsertBatch(E entity, List<Map<String, Object>> parameter) {
		try {
			String sql = jdbcTemplateOperations.getInsertSql(entity, entityClass);
			return this.executeBatch(sql, parameter);
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public int executeBatch(String sql, List<Map<String, Object>> parameter) throws DaoException {
		try {
			int[] i = jdbcTemplateOperations.executeBatch(sql, parameter);
			return i.length;
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public E queryOneEntity(String sql, Map<String, Object> parameter) throws DaoException {
		E entity = null;
		try {
			List<Map<String, Object>> results = jdbcTemplateOperations.queryForList(sql, parameter);
			if (ObjectUtils.isNotEmpty(results)) {
				Map<String, Object> result = results.iterator().next();
				entity = BeanUtils.convertMap(entityClass, result);
			}
			return entity;
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public Map<String, Object> queryOneMap(String sql, Map<String, Object> parameter) throws DaoException {
		Map<String, Object> result = null;
		try {
			List<Map<String, Object>> results = jdbcTemplateOperations.queryForList(sql, parameter);
			if (ObjectUtils.isNotEmpty(results)) {
				result = results.iterator().next();
			}
			return result;
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public List<E> queryForListEntities(String sql, Map<String, Object> parameter) throws DaoException {
		List<E> entities = null;
		try {
			List<Map<String, Object>> results = jdbcTemplateOperations.queryForList(sql, parameter);
			if (ObjectUtils.isNotEmpty(results)) {
				entities = BeanUtils.convertMap(entityClass, results);
			}
			return entities;
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public List<Map<String, Object>> queryForList(String sql, Map<String, Object> parameter) throws DaoException {
		try {
			List<Map<String, Object>> results = jdbcTemplateOperations.queryForList(sql, parameter);
			return results;
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public Page<E> queryPage(String sql, BasePage basePage, Map<String, Object> parameter) throws DaoException {
		try {
			Page<E> page = new Page<E>(basePage.getPageSize(), basePage.getPageNo());
			String pageSql = jdbcTemplateOperations.getQueryPageSql(sql, page, parameter);
			List<Map<String, Object>> resultMaps = jdbcTemplateOperations.queryForList(pageSql, parameter);
			page.setData(BeanUtils.convertMap(entityClass, resultMaps));
			page.setTotalRecord(queryCount(sql, parameter));
			return page;
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}

	public int queryCount(String sql, Map<String, Object> parameter) throws DaoException {
		int i = 0;
		try {
			String countSql = jdbcTemplateOperations.getCountSql(sql);
			List<Map<String, Object>> results = jdbcTemplateOperations.queryForList(countSql, parameter);
			i = ObjectUtils.convertToInteger(results.iterator().next().get("cn"));
			return i;
		} catch (RuntimeException e) {
			throw new DaoException(e);
		}
	}
}
