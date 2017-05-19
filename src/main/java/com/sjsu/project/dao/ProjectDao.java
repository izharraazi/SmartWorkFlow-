package com.sjsu.project.dao;

import com.sjsu.project.model.Project;


public interface ProjectDao {
    void createProject(Project project);
    Project getProject(long projectId);
    void updateProject(long projectId, Project project);
    void deleteProject(long projectId);
    void addUsers(long projectId, long userId);
}
