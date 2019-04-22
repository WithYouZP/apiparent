package com.zp.api.core.base.service.impl;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import com.zp.api.common.parameter.ResponseCode;
import com.zp.api.common.parameter.ResponseEntity;
import com.zp.api.common.util.ResponseUtils;
import com.zp.api.core.base.dao.BaseDao;
import com.zp.api.core.base.entity.BaseEntity;
import com.zp.api.core.base.service.BaseSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BaseServiceImpl<E extends BaseEntity<PK>,PK extends Serializable> implements BaseSevice<E, PK> {

	@Autowired
	private BaseDao<E, PK> baseDao;

	@Override
	@Transactional
	public ResponseEntity<Void> save(E entity) {
		baseDao.save(entity);
		return ResponseUtils.createRet(ResponseCode.SUCCESS, "成功");
	}

	@Override
	@Transactional
	public ResponseEntity<Void> update(E entity) {
		baseDao.update(entity);
		return ResponseUtils.createRet(ResponseCode.SUCCESS, "成功");
	}

	@Override
	@Transactional
	public ResponseEntity<Void> saveOrUpdate(E entity) {
		baseDao.saveOrUpdate(entity);
		return ResponseUtils.createRet(ResponseCode.SUCCESS, "成功");
	}
	
	@Override
	public E get(PK pk) {
		E entity = baseDao.get(pk);
		return entity;
	}

	@Override
	public E getPhysics(PK pk) {
		E entity = baseDao.getPhysics(pk);
		return entity;
	}
	
	@Override
	@Transactional
	public ResponseEntity<Void> deletePhysics(PK pk) {
		baseDao.deletePhysics(pk);
		return ResponseUtils.createRet(ResponseCode.SUCCESS, "成功");
	}
	
	@Override
	@Transactional
	public ResponseEntity<Void> delete(PK pk) {
		baseDao.delete(pk);
		return ResponseUtils.createRet(ResponseCode.SUCCESS, "成功");
	}

	@Override
	public ResponseEntity<Void> executeInsertBatch(Collection<E> datas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Void> executeDeleteBatch(Collection<Map<String, Object>> datas) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
