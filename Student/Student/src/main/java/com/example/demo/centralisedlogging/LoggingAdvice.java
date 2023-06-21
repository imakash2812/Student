package com.example.demo.centralisedlogging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

@Aspect
@Component
public class LoggingAdvice {

	Logger Logger=LoggerFactory.getLogger(LoggingAdvice.class);
	
	@Pointcut(value="execution(* com.example.demo.*.*.*(..))")
	public void myPointCut() {
		
	}
	
	@Pointcut("myPointCut()")
	public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable{
		ObjectMapper mapper=new ObjectMapper();
		String methodName=pjp.getSignature().getName();
		String className=pjp.getTarget().getClass().toString();
		Object[] array=pjp.getArgs();
		Logger.info("method invoked " + className + " : " + methodName + "()" + "arguments : " + mapper.writeValueAsString(array));
		Object object=pjp.proceed();
		Logger.info(className + " : " + methodName + "()" + "Response : " + mapper.writeValueAsString(array));
		return object;

		
		
	}
}
