package com.sjsu.project.vo;

public class UserVo implements java.io.Serializable{
    
    private String userName;
    
    private long userId;
    
    private String email;
    
    

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
    
    
    
    
}
