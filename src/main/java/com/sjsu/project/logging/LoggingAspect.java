package com.sjsu.project.logging;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;


/**
 * @author IzharR
 *  14-Dec-2015 
 */
@Component
@Aspect
public class LoggingAspect {
	
	 private Logger logger = Logger.getLogger(LoggingAspect.class);
	/**
	 Pointcut for creating a project
	 */
	@Pointcut("execution(* com.sjsu.project.controller.ProjectController.createProject(..))")
	public void createProject() {}
	
	/**
	 * Pointcut for logging the new state of the project
	 */
	@Pointcut("execution(* com.sjsu.project.controller.ProjectController.startProject(..))")
	public void startProject() {}
	
	/*
	 * Task creation pointcut
	 */
	@Pointcut("execution(* com.sjsu.project.controller.TaskController.createTask(..))")
	public void createTask(){}
	
	@Before("createProject()")
	public void checkBeforeCreate(JoinPoint joiningPoint) {
		Object args[] = joiningPoint.getArgs();
		try{
			
			Long userId = (Long) args[0];
			String title =  (String) args[1];
			String description=(String) args[2];
			String state=(String) args[3];
			logger.info("Inside Before Advice for Creating Project");
			if (userId == null|| "".equalsIgnoreCase(title) || description == null || "".equalsIgnoreCase(description) )
	        	{
					throw new NullPointerException();
	        	}
			logger.info("Successfully validated Parameters before creating a Project");
			}
		catch(NullPointerException e){
			logger.info("Parameters are null Validation failed");
		}
	}
	
	/*
	 * This Advice will intercept the previous state and change it to proposed before forwarding to 
	 * the intercepted method
	 */
	@Around("startProject()")
	public Object  logState(ProceedingJoinPoint proceeding) throws Throwable {
		Object args[] = proceeding.getArgs();
		try{
			String state = null;
			Long userId = (Long) args[0];
			Long projectID =  (Long) args[1];
			String prevstate=(String) args[2];
			logger.info("Around  Advice for changing State of the  Project");
			if (userId == null|| "".equalsIgnoreCase(state) || projectID == null  )
	        	{
					throw new NullPointerException();
	        	}
			logger.info("Current State of the ProjectId ::"+projectID+" is ::" +prevstate);
			
			
			if( prevstate.equalsIgnoreCase("Planning") )
			    state = "Ongoing";
			else if( prevstate.equalsIgnoreCase("Ongoing") )
			    state = "Completed"; 
			
			if( prevstate.equalsIgnoreCase("Cancelled") )
			    state = "Cancelled";
			
			args[2] = state;
			logger.info("The proposed new state the ProjectId ::"+projectID+" is ::"+ state);
			
			}
		catch(NullPointerException e){
			logger.info("Parameters are null Validation failed");
		}
		return proceeding.proceed(args);
	}
	
	
	
/*
 * 	After Returning advice to log the TaskId created 
 */
	
	@AfterReturning(pointcut="createTask()",returning="taskId")
	public void createTaskAfterReturningAdvice(JoinPoint joinPoint, Object taskId) {
		
		Object args[] = joinPoint.getArgs();
		Long userId = (Long) args[3];
		Long projectID =  (Long) args[0];
		String title=(String) args[1];
		String description = (String) args[2];
		String estimate  = (String) args[4];
		
		ResponseEntity<String> TaskId = (ResponseEntity<String>) taskId;	
		if(TaskId.getStatusCode().value()==200){
			logger.info("New Task is created with Id ::"+TaskId.getBody()+"\nUnder Project Id ::" +projectID 
						+"\n Assigned to UserId ::"+userId
						+"Title :"+title+"\n Estimated Time ::"+estimate);
			
		}
		
	}
	
}
