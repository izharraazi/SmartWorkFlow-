package com.sjsu.project.dao;

import java.util.List;
import java.util.Set;

import com.sjsu.project.model.Task;

public interface TaskDao {

    void createTask(Task task);
    Task getTask(long taskId);
    void updateTask(long taskId, Task task);
    void deleteTask(long taskId);
    Set<Task> getTasks(long projectId);
    List<Task> getTasksByUser(long userId);
}
