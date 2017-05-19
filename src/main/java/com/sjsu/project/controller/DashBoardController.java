package com.sjsu.project.controller;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sjsu.project.dao.ProjectDao;
import com.sjsu.project.dao.TaskDao;
import com.sjsu.project.dao.UserDao;
import com.sjsu.project.model.Project;
import com.sjsu.project.model.User;
import com.sjsu.project.vo.DashboardVo;
import com.sjsu.project.vo.UserVo;

/*
 * @author Izhar Raazi 
 */
@Controller
@RequestMapping("/dashboard")
public class DashBoardController {

    @Autowired
    ProjectDao projectDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    TaskDao taskDao;
    
    private static final Logger logger = Logger.getLogger(DashBoardController.class);
    
    // GET dashboard of the User
    @RequestMapping(method = RequestMethod.GET)
    public String getDashboard(Model model,HttpServletRequest req){
	
	DashboardVo dashboardVo = new DashboardVo();
	HttpSession session = req.getSession();
	
	UserVo user = (UserVo) session.getAttribute("User");
	User userDB = null;
	
	if(null != user )
	    userDB = userDao.getUser(user.getUserId());
	
	if( null != userDB ){
	    
	    Set<Project> ownedProjects = new HashSet<Project>();
	    Set<Project> participantProjects = new HashSet<Project>();
	    
	    for(Project project : userDB.getProjects() ){
		if( project.getOwner().getUserId() == user.getUserId() )
		    ownedProjects.add(project);
		else
		    participantProjects.add(project);
	    }
	    
	    dashboardVo.setOwnedProjects(ownedProjects);
	    dashboardVo.setParticipantProjects(participantProjects);
	    dashboardVo.setName(userDB.getName());
	    dashboardVo.setUserid(userDB.getUserId());
	    
	    dashboardVo.setListTasks( taskDao.getTasksByUser(userDB.getUserId()) );
	    
	}
	
	model.addAttribute("dashboard", dashboardVo);
	return "dashboard";

    }
    
  
}
