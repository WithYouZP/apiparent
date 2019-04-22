package com.zp.api.common.util;

public class RedisKeyUtil {
	private static final String SPIL=":";
	private static final String MY_HERT="MYHERT";//我心动别人的
	private static final String TO_HERT="TOHERT";//别人对我的心动
	
	/**获取别人对我心动set 的key
	 * @param memberId
	 * @return
	 */
	public static String getToHert(long memberId) {
		return TO_HERT+SPIL+String.valueOf(memberId);
	}
	
	/**获取到我对别人心动的set的key
	 * @param memberId
	 * @return
	 */
	public static String getMyHert(long memberId) {
		return MY_HERT+SPIL+String.valueOf(memberId);
		
	}
	

}
