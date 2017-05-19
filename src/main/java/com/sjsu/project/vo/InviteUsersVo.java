package com.sjsu.project.vo;

import java.util.List;
import java.util.Set;

import com.sjsu.project.model.User;

public class InviteUsersVo implements java.io.Serializable{
    
    private List<User> listUsers;
    
    private long projectId;

    
    public long getProjectId() {
        return projectId;
    }

    public void setProjectId(long projectId) {
        this.projectId = projectId;
    }

    public List<User> getListUsers() {
        return listUsers;
    }

    public void setListUsers(List<User> listUsers) {
        this.listUsers = listUsers;
    }

    
  
}
