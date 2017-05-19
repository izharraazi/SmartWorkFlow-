package com.sjsu.project.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sjsu.project.dao.UserDao;
import com.sjsu.project.vo.UserVo;
/*
 * @author Izhar Raazi 
 */
/**
 * Handles requests for the application home page.
 */
@Controller
public class LoginController {
    
    @Autowired
    UserDao userDao;
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String home(Model model) {
	
	System.out.println("Login...");
	return "login";
    }
    
    @RequestMapping(value = "/register")
    public String signup(Model model) {
	
	System.out.println("in registration");
	return "register";
    }
    
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginfunction(Model model, HttpServletRequest req,
	    @RequestParam(value = "email", required = true) String email,
	    @RequestParam(value = "password", required = true) String password) {
	
	UserVo userdetails = new UserVo();
	userdetails = userDao.validateCredential(email, password);
	if (userdetails == null) {
	    model.addAttribute("error", "Login Failed please check credentials");
	    return "login";
	} else {
	    model.addAttribute("user", userdetails);
	    req.getSession().setAttribute("User", userdetails);
	}
	return "redirect:/dashboard";
    }
}
