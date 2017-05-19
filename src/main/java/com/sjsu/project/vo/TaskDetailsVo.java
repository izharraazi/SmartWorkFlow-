package com.sjsu.project.vo;

import java.util.Set;

import com.sjsu.project.model.Project;
import com.sjsu.project.model.User;


public class TaskDetailsVo implements java.io.Serializable{
    
    private long taskId;
    private Project project;
    private User assignedUser;
    private String title;
    private String desc;
    private String state;
    private long expected;
    private long actual;
    private String ownerOfProject;
    private Set<User> listUsers;
    
    public long getTaskId() {
        return taskId;
    }
    public void setTaskId(long taskId) {
        this.taskId = taskId;
    }
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }
    public User getAssignedUser() {
        return assignedUser;
    }
    public void setAssignedUser(User assignedUser) {
        this.assignedUser = assignedUser;
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
    public long getExpected() {
        return expected;
    }
    public void setExpected(long expected) {
        this.expected = expected;
    }
    public long getActual() {
        return actual;
    }
    public void setActual(long actual) {
        this.actual = actual;
    }
    public String getOwnerOfProject() {
        return ownerOfProject;
    }
    public void setOwnerOfProject(String ownerOfProject) {
        this.ownerOfProject = ownerOfProject;
    }
    public Set<User> getListUsers() {
        return listUsers;
    }
    public void setListUsers(Set<User> listUsers) {
        this.listUsers = listUsers;
    }
    
    
}
