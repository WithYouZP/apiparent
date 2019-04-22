package com.zp.api.core.context;


import com.zp.api.common.context.ContextHolder;

public class KernerContextHolder extends ContextHolder {
	
	private final ThreadLocal<Object> userThreadLocal = new ThreadLocal<>();
	
	/**
	 * 从session缓存中获取当前App用户信息
	 * @return 
	 * @return
	 */
	public Object getUser(){
		return userThreadLocal.get();
	}
	
	public void setUser(Object user){
		userThreadLocal.set(user);
	}
	
}
