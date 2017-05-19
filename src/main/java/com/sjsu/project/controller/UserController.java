package com.sjsu.project.controller;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

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
import com.sjsu.project.model.User;
import com.sjsu.project.vo.InviteUsersVo;
/*
 * @author Izhar Raazi 
 */

@Controller
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    ProjectDao projectDao;
    
    @Autowired
    UserDao userDao;
    
    @Autowired
    TaskDao taskDao;
    
    private static MessageDigest md;
    
    @RequestMapping(value = "/invite/{projectId}", method = RequestMethod.GET)
    public String inviteUser(@PathVariable(value = "projectId") long projectId, Model model) {
	
	List<User> listUsers1 = userDao.getAll();
	
	Set<User> setUsers = new HashSet<>(listUsers1); 
	
	List<User> listUsers = new ArrayList<User>(setUsers);
	
	InviteUsersVo inviteUsersVo = new InviteUsersVo();
	inviteUsersVo.setListUsers(listUsers);
	inviteUsersVo.setProjectId(projectId);
	
	model.addAttribute("inviteUsers", inviteUsersVo);
	return "inviteUsers";
    }
    
    @RequestMapping(value = "/invite", method = RequestMethod.GET)
    public String sendInviteUser(Model model) {
	
	List<User> listUsers = userDao.getAll();
	
	InviteUsersVo inviteUsersVo = new InviteUsersVo();
	inviteUsersVo.setListUsers(listUsers);
	
	model.addAttribute("inviteUsers", inviteUsersVo);
	return "inviteUsers";
    }
	 @RequestMapping(value = "/invite/email/{email}", method = RequestMethod.GET)
    public String sendInviteUser(@PathVariable(value = "email") String email, Model model) {


        List<User> listUsers = userDao.getAll();
        Set<User> foo = new HashSet<>(listUsers);
        List<User> listUsers1  = new ArrayList<User>(foo);
        
        
        InviteUsersVo inviteUsersVo1 = new InviteUsersVo();
        inviteUsersVo1.setListUsers(listUsers1);

        model.addAttribute("inviteUsers", inviteUsersVo1);
        return "inviteUsers";

    }
	 
	 /*MD5 encryption for password 
	  * @param String Pass 
	  */
	 public static String cryptWithMD5(String pass){
		    try {
		        md = MessageDigest.getInstance("MD5");
		        byte[] passBytes = pass.getBytes();
		        md.reset();
		        byte[] digested = md.digest(passBytes);
		        StringBuffer sb = new StringBuffer();
		        for(int i=0;i<digested.length;i++){
		            sb.append(Integer.toHexString(0xff & digested[i]));
		        }
		        return sb.toString();
		    } catch (NoSuchAlgorithmException ex) {
		    }
		        return null;


		   }
    
    
    // CREATE user
    @RequestMapping( method = RequestMethod.POST)
	public String loginfunction(Model model, HttpServletRequest req,
			 					@RequestParam(value = "email", required = true) String email,
			 					@RequestParam(value="fullname", required = true) String name,
			 					@RequestParam(value = "password", required = true) String password) {
		
		
		String encrypted_pwd;
    	if (name == null || "".equalsIgnoreCase(name) || email == null || "".equalsIgnoreCase(email)) {
			model.addAttribute("error","login Failed please check the details ");
			return "register";
		}
		
			encrypted_pwd =  cryptWithMD5(password);
		 	User user = new User(name, email, encrypted_pwd);
	        userDao.createUser(user);
	        model.addAttribute("mesg", "Please sign  in again with your registered credentials");
			return "redirect:/login";
	}

    // GET user by id
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET, produces = { "application/json" })
    public ResponseEntity<User> getUser(@PathVariable(value = "userId") long userId) {

        User user = userDao.getUser(userId);
        if(user == null){
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<User>(user, HttpStatus.OK);
    }

    // Update user name and password (no email change as email is unique)
    @RequestMapping(value = "/{userId}", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<User> updateUser(@PathVariable(value = "userId") long userId,
                                             @RequestParam(value="name", required = true) String name,
                                             @RequestParam(value="password", required = true) String password){

        User user = userDao.getUser(userId);
        if (user == null) {
            return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
        }

        if (name != null || !"".equalsIgnoreCase(name))
            user.setName(name);
        if (password != null || !"".equalsIgnoreCase(password))
            user.setPassword(password);

        userDao.updateUser(userId,user);
        User user1 = userDao.getUser(userId);
        return new ResponseEntity<User>(user1, HttpStatus.OK);
    }

    // Delete a user
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<String> deleteUser(@PathVariable(value = "userId") long userId){

        User user = userDao.getUser(userId);
        if (user == null) {
            return new ResponseEntity<String>("User Not found",HttpStatus.NOT_FOUND);
        }

        userDao.deleteUser(userId);
        return new ResponseEntity<String>("User Deleted", HttpStatus.OK);
    }
}
