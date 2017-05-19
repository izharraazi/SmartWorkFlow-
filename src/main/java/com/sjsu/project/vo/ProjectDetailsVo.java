package com.sjsu.project.vo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.sjsu.project.model.Task;
import com.sjsu.project.model.User;


public class ProjectDetailsVo implements java.io.Serializable{
    
    private String isOwner;
    private long projectId;
    private User owner;
    private String title;
    private String desc;
    private String state;
    private Set<Task> listTasks;
    private List<String> listStates;
    private Set<User> listUsers;

    public Set<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(Set<User> listUsers) {
        this.listUsers = listUsers;
    }

    private Set<User> usersAssigned = new HashSet<User>();
    
    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Set<User> getUsersAssigned() {
        return usersAssigned;
    }

    public void setUsersAssigned(Set<User> usersAssigned) {
        this.usersAssigned = usersAssigned;
    }

    public String getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }

    public List<String> getListStates() {
        return listStates;
    }

    public void setListStates(List<String> listStates) {
        this.listStates = listStates;
    }

    public Set<Task> getListTasks() {
        return listTasks;
    }

    public void setListTasks(Set<Task> listTasks) {
        this.listTasks = listTasks;
    }

   
    
        
}
