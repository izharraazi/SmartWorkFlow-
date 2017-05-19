package com.sjsu.project.dao;

import java.util.List;
import java.util.Set;

import com.sjsu.project.model.Project;
import com.sjsu.project.model.User;
import com.sjsu.project.vo.UserVo;

public interface UserDao {

    User getUser(long userId);
    
    void createUser(User user);
    List<User> getAll();
    void updateUser(long userId, User user);
    void deleteUser(long userId);
    void addProjects(long userId, Project project);
    UserVo validateCredential(String email,String password);
}
