package com.zp.api.core.aspert;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.zp.api.common.persistence.annotation.SqlMonitor;
import com.zp.api.common.persistence.refect.SQLFormatter;
import com.zp.api.common.util.DateUtils;
import com.zp.api.common.util.ObjectUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;


@Aspect
@Component
public class SqlMonitorAspert {

	private Logger logger = LoggerFactory.getLogger(SqlMonitorAspert.class);
	//@Before是在所拦截方法执行之前执行一段逻辑。
	//@After 是在所拦截方法执行之后执行一段逻辑。
	//@Around是可以同时在所拦截方法的前后执行一段逻辑。
	@Around(value = "@annotation(monitor)")
	public Object doAfter(ProceedingJoinPoint joinPoint, SqlMonitor monitor) {
		//获取连接点方法运行时的入参列表
		Object[] args = joinPoint.getArgs();
		Map<String, Object> retMap = this.handlerExecTime(joinPoint, args);
		Object obj = retMap.get("result");
		long executeTime = ObjectUtils.convertToLong(retMap.get("time"));
		String sql = this.getSql(args);
		logger.info("sql:".concat(new SQLFormatter().format(sql)));
		logger.info("耗时:".concat(executeTime + "ms"));
		return obj;
	}

	/**
	 * 计算方法执行时间
	 * 
	 * @param joinPoint
	 * @return
	 */
	private Map<String, Object> handlerExecTime(ProceedingJoinPoint joinPoint, Object[] args) {
		//StopWath是apache commons lang3包下的一个任务执行时间监视器
		StopWatch watch = new StopWatch();
		watch.start();
		Object obj = null;
		try {
			obj = joinPoint.proceed(args);
		} catch (Throwable e) {
			logger.error("统计某方法执行耗时出错", e);
		}
		watch.stop();
		long time = watch.getLastTaskTimeMillis();
		Map<String, Object> retMap = new HashMap<String, Object>();
		retMap.put("result", obj);
		retMap.put("time", time);
		return retMap;
	}

	public String getSql(Object[] args) {
		if (ObjectUtils.isEmpty(args)) {
			return null;
		}
		String sql = ObjectUtils.convertToString(args[0]);
		Map<String, Object> parameter = this.getParameter(args);
		if (ObjectUtils.isEmpty(parameter)) {
			return sql;
		}
		for (Map.Entry<String, Object> entry : parameter.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if (sql.contains(key)) {
				if (key.contains("like")) {
					String sqlValue = this.getLikeValue(key, value);
					sql = sql.replace(key, sqlValue);
					continue;
				}
				if (value == null){
					sql = sql.replace(key, "null");
					continue;
				}
				if (value instanceof String) {
					String paramValue = "'".concat(ObjectUtils.convertToString(value)).concat("'");
					sql = sql.replace(key, paramValue);
					continue;
				}
				if (value instanceof Date) {
					Date date = (Date) value;
					String dateValue = DateUtils.format(date);
					String paramValue = "'".concat(dateValue).concat("'");
					sql = sql.replace(key, paramValue);
					continue;
				}
				if (value instanceof Integer) {
					sql = sql.replace(key, ObjectUtils.convertToString(value));
					continue;
				}
			}
		}
		return sql;
	}

	public String getLikeValue(String key, Object value) {
		return new StringBuilder().append("'%").append(value).append("%'").toString();
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getParameter(Object[] args) {
		if (ObjectUtils.isEmpty(args)) {
			return null;
		}
		Map<String, Object> parameter = null;
		for (Object obj : args) {
			if (obj instanceof Map) {
				parameter = (Map<String, Object>) obj;
				break;
			}
			//MapSqlParameterSource用于封装传入的参数，多用于查询数据中，如果命名参数与实体类对象属性不一致，可以用此类来映射，类似于map参数映射。
			if (obj instanceof MapSqlParameterSource) {
				MapSqlParameterSource source = (MapSqlParameterSource) obj;
				parameter = source.getValues();
				break;
			}
		}
		return this.processParameter(parameter);
	}

	private Map<String, Object> processParameter(Map<String, Object> parameter) {
		if (ObjectUtils.isEmpty(parameter)) {
			return null;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		for (Map.Entry<String, Object> entry : parameter.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			String paramKey = ":".concat(key);
			paramMap.put(paramKey, value);
		}
		return paramMap;
	}
}
