package com.zp.api.core.redis;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.util.Assert;

@Configuration
public class RedisClientTemplate<Key extends Serializable> {

	@Autowired
	private RedisTemplate<Key, Object> redisTemplate;

	/**
	 * 判断key是否在redis中
	 * @param key
	 * @return
	 */
	public boolean exists(final Key key) {
		assertIsNull(key);
		return redisTemplate.hasKey(key);
	}

	/**
	 * 从redis中获取数据
	 * @param key
	 * @return
	 */
	public Object get(final Key key) {
		assertIsNull(key);
		ValueOperations<Key, Object> operations = redisTemplate.opsForValue();
		return operations.get(key);
	}

	/**
	 * 讲数据插入redis中
	 * @param k
	 * @param v
	 */
	public void set(final Key k, Object v) {
		assertIsNull(k, v);
		ValueOperations<Key, Object> operations = redisTemplate.opsForValue();
		operations.set(k, v);
	}

	/**
	 * 将数据插入redis中，设置缓存失效时间
	 * @param k
	 * @param v
	 * @param expire 失效时间，秒
	 */
	public void set(final Key k, Object v, long expire) {
		assertIsNull(k, v);
		ValueOperations<Key, Object> operations = redisTemplate.opsForValue();
		operations.set(k, v);
		redisTemplate.expire(k, expire, TimeUnit.SECONDS);
	}

	/**
	 * 删除
	 * @param k
	 */
	public void remove(final Key k) {
		assertIsNull(k);
		if (exists(k)) {
			redisTemplate.delete(k);
		}
	}

	/**
	 * 批量删除
	 * @param keys
	 */
	public void remove(final Key[] keys) {
		assertIsNull(keys);
		redisTemplate.delete(Arrays.asList(keys));
	}
	
	/**将数据set 到
	 * @param key
	 * @param o
	 */
	public void setlist(final Key key,Object o) {
		redisTemplate.opsForList().rightPush(key, o);
	}
	
	public List getList(final Key key,long start,long end) {
		return 	(List)redisTemplate.opsForList().range(key, start, end);
	}
	/*
	 * 存储hash对象的值
	 */
	public void putAllHash(final Key key,Map<Object,Object> m) {
		redisTemplate.opsForHash().putAll(key, m);;
	}
	/*
	 * 获取到hash 的值
	 */
	public Map<Object, Object> getHash(final Key key){
		return redisTemplate.opsForHash().entries(key);
		
	}
	/*
	 * 往有序集合里面set值
	 */
	public void addzset(final Key key,Set<TypedTuple<Object>> ts) {
		redisTemplate.opsForZSet().add(key, ts);
		
	}
	/*
	 *根据相应的长度获取到对应的值
	 */
	public Set<Object> getZset(final Key key,long start,long end){
		return redisTemplate.opsForZSet().range(key, start, end);
	}
	/*
	 * 往无序集合中添加相对应的值
	 */
	public void addset(final Key key,Object...o) {
		redisTemplate.opsForSet().add(key, o);
	}
	/*
	 * 判断该成员是否属于该无序集合
	 */
	public Boolean isMember(final Key key,Object o) {
		return redisTemplate.opsForSet().isMember(key, o);
	} 
	/*
	 * 如果该成员已经存在就移除
	 */
	public void removeMeber(final Key key,Object o) {
		redisTemplate.opsForSet().remove(key, o);
	}
	
	/*
	 * 判断该有序集合的带下
	 */
	public Long getSetSize(final Key key) {
		return redisTemplate.opsForSet().size(key);
	}
	private void assertIsNull(Key k) {
		Assert.state(k != null, "redis key must not be null.");
	}

	private void assertIsNull(Key[] keys) {
		Assert.state(keys != null, "redis keys must not be null.");
	}

	private void assertIsNull(Key k, Object v) {
		Assert.state(k != null && v != null, "redis key or value must not be null.");
	}
}
