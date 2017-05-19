package com.sjsu.project.vo;

import java.util.List;
import java.util.Set;

import com.sjsu.project.model.Project;
import com.sjsu.project.model.Task;

public class DashboardVo implements java.io.Serializable{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    private String name;
    
    private long userid;
    
    private Set<Project> ownedProjects;
    
    private Set<Project> participantProjects;
    
    private List<Task> listTasks;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public Set<Project> getOwnedProjects() {
        return ownedProjects;
    }

    public void setOwnedProjects(Set<Project> ownedProjects) {
        this.ownedProjects = ownedProjects;
    }

    public Set<Project> getParticipantProjects() {
        return participantProjects;
    }

    public void setParticipantProjects(Set<Project> participantProjects) {
        this.participantProjects = participantProjects;
    }

    public List<Task> getListTasks() {
        return listTasks;
    }

    public void setListTasks(List<Task> listTasks) {
        this.listTasks = listTasks;
    }
    
    
    
}
