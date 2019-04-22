package com.zp.api.core.base.service;

import com.zp.api.common.parameter.ResponseEntity;
import com.zp.api.core.base.entity.BaseEntity;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;


/**
 *  Title: BaseSevice.java
 *
 *  @author zzf
 *
 *  Description: 业务父类
 *
 *  Date:2018年1月11日
 */
public interface BaseSevice<E extends BaseEntity<PK>,PK extends Serializable> {
	/**
	 * @param entity
	 * @return
	 */
	public ResponseEntity<Void> save(E entity);
	
	/**
	 * @param entity
	 * @return
	 */
	public ResponseEntity<Void> update(E entity);
	
	public ResponseEntity<Void> saveOrUpdate(E entity);

	
	/**
	 * @param pk
	 * @return
	 */
	public E get(PK pk);
	
	/**
	 * 根据删除标识获取对象
	 * @param pk ID
	 * @return
	 */
	public E getPhysics(PK pk);
	
	/**
	 * @param pk
	 * @return
	 */
	public ResponseEntity<Void> delete(PK pk);
	
	/**
	 * 逻辑删除
	 * @param pk
	 * @return
	 */
	public ResponseEntity<Void> deletePhysics(PK pk);
	
	/**
	 * 批量新增对象
	 * @param datas
	 * @return
	 */
	public ResponseEntity<Void> executeInsertBatch(Collection<E> datas);

	/**
	 * 批量删除
	 * @param datas
	 * @return
	 */
	public ResponseEntity<Void> executeDeleteBatch(Collection<Map<String, Object>> datas);
}
