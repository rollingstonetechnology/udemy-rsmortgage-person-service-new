package com.rollingstone;

import java.util.NoSuchElementException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RestControllerAspect {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	CounterService counterService;

	@Before("execution(public * com.rollingstone.api.rest.*Controller.*(..))")
	public void logBeforeRestCall(JoinPoint pjp) throws Throwable {
		logger.info(":::::AOP Before REST call:::::" + pjp);
	}

	@AfterReturning("execution(public * com.rollingstone.api.rest.*Controller.createPerson*(..))")
	public void afterCallingCreatePerson(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning Create REST call:::::" + pjp);
		counterService.increment("com.rollingstone.api.rest.PersonController.createPerson");
	}

	@AfterReturning("execution(public * com.rollingstone.api.rest.*Controller.getAllPerson*(..))")
	public void afterCallinggetAllPerson(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning getAllPerson REST call:::::" + pjp);

		counterService.increment("com.rollingstone.api.rest.PersonController.getAllPerson");
	}

	@AfterReturning("execution(public * com.rollingstone.api.rest.*Controller.getPerson*(..))")
	public void afterCallinggetPerson(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning getPErson REST call:::::" + pjp);
		counterService.increment("com.rollingstone.api.rest.PersonController.getPerson");
	}

	@AfterReturning("execution(public * com.rollingstone.api.rest.*Controller.updatePerson*(..))")
	public void afterCallingupdatePerson(JoinPoint pjp) {
		logger.info(":::::AOP @AfterReturning update PErson REST call:::::" + pjp);
		counterService.increment("com.rollingstone.api.rest.PersonController.updatePerson");
	}

	@AfterThrowing(pointcut = "execution(public * com.rollingstone.api.rest.*Controller.*(..))", throwing = "e")
	public void afterGetGreetingThrowsException(NoSuchElementException e) {
		counterService.increment("counter.errors.person.controller");
	}
}
