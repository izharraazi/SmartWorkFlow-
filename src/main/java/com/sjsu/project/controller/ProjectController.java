package com.sjsu.project.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sjsu.project.dao.ProjectDao;
import com.sjsu.project.dao.TaskDao;
import com.sjsu.project.dao.UserDao;
import com.sjsu.project.logging.LoggingAspect;
import com.sjsu.project.mail.agent.GoogleMailAgent;
import com.sjsu.project.model.Project;
import com.sjsu.project.model.Task;
import com.sjsu.project.model.User;
import com.sjsu.project.vo.ProjectDetailsVo;
import com.sjsu.project.vo.UserVo;
/*
 * @author Izhar Raazi 
 */

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectDao projectDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    TaskDao taskDao;
    
    @Autowired
    LoggingAspect loggingAspect;
    
    
    /*

    Project has four states  1. Planning, 2. Ongoing, 3. Cancelled, and 4. Completed.

    */

    // CREATE project and assign to owner
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity createProject(
            @PathVariable(value = "userId") long userId,
            @RequestParam(value = "title", required = true) String title,
            @RequestParam(value = "description", required = true) String description,
            @RequestParam(value = "state", required = false) String state) {

	if (title == null || "".equalsIgnoreCase(title) || description == null || "".equalsIgnoreCase(description) )
        {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
	Project project = new Project(title, description, "Planning");
        
	User user1 = userDao.getUser(userId);
        project.setOwner(user1);
        
        userDao.addProjects(userId, project);
        
        return new ResponseEntity<String>(String.valueOf(project.getProjectId()),HttpStatus.OK);
    }

    // GET project
    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public String getProject(
            @PathVariable(value = "projectId") long projectId, Model model, HttpServletRequest req) {

        Project project = projectDao.getProject(projectId);
        
	HttpSession session = req.getSession();
	UserVo userVo = (UserVo) session.getAttribute("User");
	
	
        ProjectDetailsVo projectDetailsVo = new ProjectDetailsVo();
        projectDetailsVo.setProjectId(projectId);
        projectDetailsVo.setOwner(project.getOwner());
        projectDetailsVo.setTitle(project.getTitle());
        projectDetailsVo.setState(project.getState());
        projectDetailsVo.setDesc(project.getDesc());
        projectDetailsVo.setListTasks(project.getListTasks());
        
        projectDetailsVo.setIsOwner("N");
        if(project.getOwner().getUserId() == userVo.getUserId() )
            projectDetailsVo.setIsOwner("Y");
        
        projectDetailsVo.setUsersAssigned(project.getUsers());
        
        List<User> listUsers = userDao.getAll();
        User firstUser = null;
        for( User user : listUsers ){
            if( user.getUserId() == userVo.getUserId() ){
        	firstUser = user;
        	break;
            }
        }
        Set<User> setUsers = new HashSet<User>();
        setUsers.add(firstUser);
        setUsers.addAll(listUsers);
        projectDetailsVo.setListUsers(setUsers);
        
        model.addAttribute("projectDetails", projectDetailsVo);
        return "projectDetails";
    }

    // Assign projects to user.
    @RequestMapping(value = "/{projectId}/{userId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity addUsersToProject(@PathVariable(value = "projectId") long projectId,
                                            @PathVariable(value = "userId") long userId) {

        User user = userDao.getUser(userId);
        Set<User> users = null;
        if( null != user ){
        	projectDao.addUsers(projectId, userId);
        	
        	// TODO - check for successful addition
        	GoogleMailAgent agent = new GoogleMailAgent();
        	agent.sendInvitationMail(user.getName(), user.getEmail(), null, null);
        	
            Project project = projectDao.getProject(projectId);
            if( null != project )
            	users = project.getUsers();
        }
    	return new ResponseEntity<Set<User>>(users, HttpStatus.OK);
    }

    // Move project (change state to Ongoing) or change state of project
    @RequestMapping(value = "/changeState", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity startProject(@RequestParam(value = "userId") long userId,
                                       @RequestParam(value = "projectId") long projectId,
                                       @RequestParam(value = "state") String state) {
        
	/*String state = null;
	
	if( prevState.equalsIgnoreCase("Planning") )
	    state = "Ongoing";
	else if( prevState.equalsIgnoreCase("Ongoing") )
	    state = "Completed"; 
	
	if( prevState.equalsIgnoreCase("Cancelled") )
	    state = "Cancelled";*/
	
	
	Project project = projectDao.getProject(projectId);
        User user = userDao.getUser(userId);

        if (project.getState().compareToIgnoreCase("Completed") == 0 ||
                project.getState().compareToIgnoreCase("Cancelled") == 0) {

            return new ResponseEntity<String>("Project state cannot be changed after project is in Completed or Cancelled state",
                    HttpStatus.BAD_REQUEST);

        }else {

            if (state.compareToIgnoreCase("Ongoing") == 0) {

                // Check if userId is of owner to start project
                for (Project project1 : user.getProjects()) {
                    if (project1.getProjectId() == projectId) {

                        // Check if every task is in assigned state
                        Set<Task> taskSet = project.getListTasks();
                        if( null == taskSet || taskSet.size() == 0){
                            return new ResponseEntity<String>("No task is added to the Project. Project can't be moved to Ongoing.", HttpStatus.BAD_REQUEST);
                        }
                        
                        for (Task task : taskSet) {
                            // If any of task is not in assigned state
                            if (task.getState().compareToIgnoreCase("Assigned") != 0) {
                                return new ResponseEntity<String>("Task " + task.getTaskId() + " not in assigned state", HttpStatus.BAD_REQUEST);
                            }
//                            // Check if task has estimated amount of work
//                            if (task.getActual() == 0) {
//                                return new ResponseEntity<String>("Task " + task.getTaskId() + " has not actual time", HttpStatus.BAD_REQUEST);
//                            }
                        }

                        // If above satisfy then only change state of project to Ongoing
                        Project p = new Project();
                        p.setState(state);

                        projectDao.updateProject(projectId, p);
                        return new ResponseEntity(HttpStatus.OK);
                    }
                }

            } else if (state.compareToIgnoreCase("Cancelled") == 0) {

                // Check if userId is of owner to cancel project
                for (Project project1 : user.getProjects()) {
                    if (project1.getProjectId() == projectId) {

                        // If above satisfy then only change state of project to Cancelled
                        Project p = new Project();
                        p.setState(state);
                        projectDao.updateProject(projectId, p);

                        return new ResponseEntity(HttpStatus.OK);
                    }
                }
            } else if (state.compareToIgnoreCase("Completed") == 0) {

                if(project.getState().compareToIgnoreCase("planning") == 0){
                    return new ResponseEntity<String>("Project has not started yet.", HttpStatus.BAD_REQUEST);
                }else {
                    Set<Task> tasks = project.getListTasks();
                    int size = tasks.size();
                    int countFinished = 0;
                    int countCancelled = 0;
                    for (Task task : tasks) {
                        //check if each task is either finished or Cancelled

                        //atleast one finished state task
                        if (task.getState().compareToIgnoreCase("Finished") == 0) {
                            countFinished++;
                        }
                        if (task.getState().compareToIgnoreCase("Cancelled") == 0) {
                            countCancelled++;
                        }
                    }

                    if (countFinished + countCancelled < size) {
                        return new ResponseEntity<String>("Project can't be changed to Completed, check all the task status.", HttpStatus.BAD_REQUEST);
                    }
                    if( countFinished == 0){
                	return new ResponseEntity<String>("Project can't be changed to Completed, at least one task needs to be Finished.", HttpStatus.BAD_REQUEST);
                    }

                    Project p = new Project();
                    p.setState(state);
                    projectDao.updateProject(projectId, p);

                    return new ResponseEntity(HttpStatus.OK);
                }
            } else if (state.compareToIgnoreCase("planning") == 0) {

                if (project.getState().compareToIgnoreCase("Ongoing") == 0) {

                    return new ResponseEntity<String>("Project state cann't be changed to Planning.", HttpStatus.BAD_REQUEST);
                }
            }

            return new ResponseEntity<String>("Bad Request", HttpStatus.BAD_REQUEST);
        }

    }

}
