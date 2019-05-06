package com.trungho.crm.demo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CRMLoggingAspect {

	private Logger logger = Logger.getLogger(getClass().getName());
	
	private String packageName = "com.trungho.crm.demo";
	
	@Pointcut("execution(* com.trungho.crm.demo.controller.*.*(..)) && !execution(* com.trungho.crm.demo.controller.*.initBinder(..))")
	private void forControllerPackage() {}
	
	@Pointcut("execution(* com.trungho.crm.demo.service.*.*(..))")
	private void forServicePackage() {}
	
	@Pointcut("execution(* com.trungho.crm.demo.dao.*.*(..))")
	private void forDaoPackage() {}
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDaoPackage()")
	private void forAppFlow() {}
	
	@Pointcut("execution(* com.trungho.crm.demo.exception.ExceptionController.*(..))")
	private void forExceptionController() {}
	
	@Before("forAppFlow() || forExceptionController()")
	public void before(JoinPoint joinPoint) {
		String method = joinPoint.getSignature().toShortString();
		logger.info("=====> @Before: calling method: " + method);
		
		Object[] args = joinPoint.getArgs();
		for (Object arg : args) {
			logger.info("=====> argument: " + arg);
		}
	}
	
	@AfterReturning(pointcut="forAppFlow()",
			returning="result")
	public void afterReturning(JoinPoint joinPoint, Object result) {
		String method = joinPoint.getSignature().toShortString();
		logger.info("=====> @AfterReturning: from method: " + method);
		
		logger.info("=====> result: " + result);
	}
}
