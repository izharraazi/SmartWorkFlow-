package com.sjsu.project.controller;

import com.sjsu.project.dao.ProjectDao;
import com.sjsu.project.dao.TaskDao;
import com.sjsu.project.dao.UserDao;
import com.sjsu.project.model.Project;
import com.sjsu.project.model.Task;
import com.sjsu.project.model.User;
import com.sjsu.project.vo.TaskDetailsVo;
import com.sjsu.project.vo.UserVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


/*
 * @author Izhar Raazi 
 */
@Controller
@RequestMapping("/task")
public class TaskController {

    @Autowired
    ProjectDao projectDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    TaskDao taskDao;
    

    /*
    Task has five states 1. New, 2. Assigned, 3. Started, 4. Finished, and 5. Cancelled
     */


    // CREATE task and assign to user and project
    @RequestMapping(value = "/{projectId}",method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> createTask(
            @PathVariable(value = "projectId") long projectId,
            @RequestParam(value="title", required = true) String title,
            @RequestParam(value="description", required = true) String description,
            @RequestParam(value = "userId", required = false) Long assignedUserId,
            @RequestParam(value="estimate", required = true) String estimate,
            @RequestParam(value="actual", required = false) String actual) {

        // Task cannot be added after Project Planning state
        Project myProject = projectDao.getProject(projectId);
        if ( !myProject.getState().equalsIgnoreCase("Planning") && !myProject.getState().equalsIgnoreCase("Ongoing")){
            return new ResponseEntity<String>("Project is not in Planning or Ongoing, Tasks can't be added.",HttpStatus.BAD_REQUEST);
        }
        
        if (title == null || "".equalsIgnoreCase(title) || description == null || "".equalsIgnoreCase(description)
                || estimate == null || "".equalsIgnoreCase(estimate)) {
            return new ResponseEntity<String>("Required fields are missing.",HttpStatus.BAD_REQUEST);
        }
        
        User user = null;
        String state = null;
        if( assignedUserId != null ){
            user = userDao.getUser(assignedUserId);
            if( null != user )
        	state = "Assigned";
            else
        	state = "New";
        }else{
            state = "New";
        }
          
        Task task = new Task(title, description, state, Long.valueOf(estimate));
        if( null != user )
            task.setAssignee(user);
        
        Project project = projectDao.getProject(projectId);
        task.setProject(project);
        
        taskDao.createTask(task);

        Project project1 = projectDao.getProject(projectId);
        Set<Task> taskSet = project1.getListTasks();
        taskSet.add(task);
        project1.setListTasks(taskSet);
        
        projectDao.updateProject(projectId, project1);
        
        //TODO - Send email to notify that Task is assigned.
        return new ResponseEntity<String>( String.valueOf(task.getTaskId()) ,HttpStatus.OK);
    }


    
    // Get Task
    @RequestMapping(value = "/{taskId}",method = RequestMethod.GET,produces = "application/json")
    public String getTask(@PathVariable(value = "taskId") long taskId, Model model, HttpServletRequest req ){

        Task task = taskDao.getTask(taskId);
        
        HttpSession session = req.getSession();
        UserVo loggedInUser = (UserVo) session.getAttribute("User");
        
        TaskDetailsVo taskDetailsVo = new TaskDetailsVo();
        if( null != task ){
            taskDetailsVo.setTaskId( task.getTaskId() );
            taskDetailsVo.setTitle(task.getTitle());
            taskDetailsVo.setDesc(task.getDesc());
            taskDetailsVo.setState(task.getState());
            taskDetailsVo.setExpected(task.getEstimate());
            taskDetailsVo.setActual(task.getActual());
            taskDetailsVo.setAssignedUser(task.getAssignee());
            taskDetailsVo.setProject(task.getProject());
            if( task.getProject().getOwner().getUserId() == loggedInUser.getUserId() )
        	taskDetailsVo.setOwnerOfProject("Y");
            else
        	taskDetailsVo.setOwnerOfProject("N");
            
            List<User> listUsers = userDao.getAll();
            User firstUser = null;
            for( User user : listUsers ){
                if( user.getUserId() == loggedInUser.getUserId() ){
            		firstUser = user;
            		break;
                }
            }
            Set<User> setUsers = new HashSet<User>();
            setUsers.add(firstUser);
            setUsers.addAll(listUsers);
            taskDetailsVo.setListUsers(setUsers);
        }
        
        model.addAttribute("taskDetails", taskDetailsVo);
        return "taskDetails";
    }


    // Change assignee of a task OR Assign a task to one user
    @RequestMapping(value = "/change/{taskId}/{assigneeId}",method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> changeAssignee(@PathVariable(value = "taskId") long taskId,
                                     @PathVariable(value = "assigneeId") long assigneeId) {

    	Task task = taskDao.getTask(taskId);
    	if( null == task )
    		return new ResponseEntity<String>("Task not found.", HttpStatus.BAD_REQUEST);
    	
    	User newUser = userDao.getUser(assigneeId);
    	if( null == newUser )
    		return new ResponseEntity<String>("Assignee not found.", HttpStatus.BAD_REQUEST);
    	
    	if( null != task.getProject() 
    			&& ( task.getProject().getState().equalsIgnoreCase("Ongoing") || task.getProject().getState().equalsIgnoreCase("Planning")  )
    			&&  !task.getState().equalsIgnoreCase("Finished") 
    			&&  !task.getState().equalsIgnoreCase("Cancelled") 
    			){
    		task.setAssignee( newUser );
    		if( task.getState().equalsIgnoreCase("New") )
    			task.setState("Assigned");
    		taskDao.updateTask(taskId, task);
    	}else
    		return new ResponseEntity<String>("Assignee cannot be changed as Project is not in Planning or Ongoing.", HttpStatus.BAD_REQUEST);
    	
    	return new ResponseEntity<String>("Done", HttpStatus.OK);
    }	
    
    // Update task state - only by assignee
    @RequestMapping(value = "/{taskId}/{userId}",method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity updateTaskByAssignee(@PathVariable(value = "userId") long userId,
                                     @PathVariable(value = "taskId") long taskId,
                                     @RequestParam(value="actual", required = false) Long actual) {
    	
    	Task task = taskDao.getTask(taskId);
    	if( null == task )
    		return new ResponseEntity<String>("Task not found.", HttpStatus.BAD_REQUEST);
    	
    	User newUser = userDao.getUser(userId);
    	if( null == newUser )
    		return new ResponseEntity<String>("Assignee not found.", HttpStatus.BAD_REQUEST);
    	
    	if( userId != task.getAssignee().getUserId() )
    		return new ResponseEntity<String>("Not authorized.", HttpStatus.UNAUTHORIZED);
    	
    	if( actual != null && actual != 0 ){
    		task.setState("Finished");
    		task.setActual(actual);
    		taskDao.updateTask(taskId, task);
    	}else{
    		task.setState("Started");
    		taskDao.updateTask(taskId, task);
    	}
    	return new ResponseEntity<String>("Task state updated.", HttpStatus.OK);
    	
    }
   /* // Update task state
    @RequestMapping(value = "/{userId}/{taskId}",method = RequestMethod.PUT,produces = "application/json")
    @ResponseBody
    public ResponseEntity updateTask(@PathVariable(value = "userId") long userId,
                                     @PathVariable(value = "taskId") long taskId,
                                     @RequestBody Task taskState) {

        if (taskState == null) {
            return new ResponseEntity<String>("Task state Required", HttpStatus.BAD_REQUEST);
        }
        Task task = taskDao.getTask(taskId);
        
        if( null == task )
        	return new ResponseEntity<String>("Task not found.", HttpStatus.BAD_REQUEST);

        // Check if project is in terminal state Completed or Cancelled
        if (task.getProject().getState().compareToIgnoreCase("Completed") == 0 ||
                task.getProject().getState().compareToIgnoreCase("Cancelled") == 0) {
            return new ResponseEntity<String>("Task state cannot be changed, project is in terminal state", HttpStatus.BAD_REQUEST);
        }

        else {

            User user = userDao.getUser(userId);
            if (user.getUserId() != task.getAssignee().getUserId()) {
                return new ResponseEntity<String>("User not found", HttpStatus.BAD_REQUEST);
            }

            if (taskState.getState().compareToIgnoreCase("Cancelled") == 0) {
                long id = task.getProject().getProjectId();
                Set<Project> projectSet = user.getProjects();
                Iterator<Project> iterator = projectSet.iterator();
                while (iterator.hasNext()) {
                    if (id == iterator.next().getProjectId()) {
                        task.setState(taskState.getState());
                        taskDao.updateTask(taskId, task);
                        return new ResponseEntity<Task>(taskDao.getTask(taskId), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<String>("User not authorized", HttpStatus.BAD_REQUEST);
                    }
                }
            }

            task.setState(taskState.getState());
            taskDao.updateTask(taskId, task);

            return new ResponseEntity<Task>(taskDao.getTask(taskId), HttpStatus.OK);
        }

    }*/

    // Delete or Cancel a task from project
    @RequestMapping(value = "/delete/{userId}/{taskId}",method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<String> deleteTask(@PathVariable(value = "userId") long userId,
                                     @PathVariable(value = "taskId") long taskId){
        
    	Task task = taskDao.getTask(taskId);
    	if( null == task ){
    		return new ResponseEntity<String>("Task not found.",HttpStatus.BAD_REQUEST);
    	}
    	
        long id = task.getProject().getProjectId();
        Project myProject = projectDao.getProject(id);

        // Task cannot be deleted after Project planning state
        if (myProject.getState().equalsIgnoreCase("Planning") ){
            taskDao.deleteTask(taskId);
            return new ResponseEntity<String>("Task Deleted",HttpStatus.OK);
        }
        else if (myProject.getState().equalsIgnoreCase("Ongoing") && userId == myProject.getOwner().getUserId() ){
        	task.setState("Cancelled");
        	taskDao.updateTask(taskId, task);
            return new ResponseEntity<String>("Task cancelled",HttpStatus.OK);
        }else{
        	return new ResponseEntity<String>("Task cannot be deleted or cancelled.",HttpStatus.BAD_REQUEST);
        }
    }
}
