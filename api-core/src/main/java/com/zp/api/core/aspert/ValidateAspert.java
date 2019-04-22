package com.zp.api.core.aspert;

import com.zp.api.common.exception.BusinessException;
import com.zp.api.common.persistence.annotation.Validate;
import com.zp.api.common.persistence.refect.RefectBudiler;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

@Aspect
@Component
public class ValidateAspert {

	@Before(value = "@annotation(validate)")
	public void deBefore(JoinPoint joinPoint, Validate validate) throws Throwable {
		Object[] objs = joinPoint.getArgs();
		if (ObjectUtils.isEmpty(objs)){
			throw new BusinessException("参数不能为空.");
		}
		Object obj = objs[0];
		if (ObjectUtils.isEmpty(obj)){
			throw new BusinessException("参数不能为空.");
		}
		RefectBudiler.isNull(obj);
	}
}
