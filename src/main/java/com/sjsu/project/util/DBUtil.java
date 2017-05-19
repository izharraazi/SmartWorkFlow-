package com.sjsu.project.util;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.sjsu.project.dao.ProjectDao;
import com.sjsu.project.dao.TaskDao;
import com.sjsu.project.dao.UserDao;
import com.sjsu.project.model.Project;

public class DBUtil {
    
    @Autowired
    ProjectDao projectDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    TaskDao taskDao;
    
    public static List<Project> getProjectsOfTheUser(long userId){
	
	
	
	return null;
    }
    
}
