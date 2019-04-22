package com.zp.api.core.context;


import com.zp.api.common.context.ContextHolder;
import com.zp.api.core.base.entity.BaseUser;

public class BussinessContextHolder extends ContextHolder {
	
	private final static ThreadLocal<String> tokenThreadLocal = new ThreadLocal<String>();
	
	private static final ThreadLocal<KernerContextHolder> kernerLocal = new ThreadLocal<KernerContextHolder>() {
		@Override
		protected KernerContextHolder initialValue() {
			return new KernerContextHolder();
		}
	};
	
	@SuppressWarnings("unchecked")
	public static <E extends BaseUser> E getUser() {
		KernerContextHolder holder = kernerLocal.get();
		return (E) holder.getUser();
	}
	
	public static <E extends BaseUser> void setUser(E user) {
		KernerContextHolder holder = kernerLocal.get();
		holder.setUser(user);
	}
	
	public static String getToken(){
		return tokenThreadLocal.get();
	}
	
	public static void setToken(String session){
		tokenThreadLocal.set(session);
	}
	
	public static void clear(){
		tokenThreadLocal.remove();
		kernerLocal.remove();
	}
}
