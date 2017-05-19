package com.sjsu.project.dao.impl;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.sjsu.project.dao.TaskDao;
import com.sjsu.project.model.Task;
import com.sjsu.project.model.User;
import com.sjsu.project.vo.UserVo;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

/*
 * @author Izhar Raazi 
 */
public class TaskDaoImpl implements TaskDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    public void createTask(Task task) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(task);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public Task getTask(long taskId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Task task = (Task) session.get(Task.class, taskId);
            tx.commit();
            return task;
        }catch (HibernateException e){
            tx.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public void updateTask(long taskId, Task task) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Task task1 = (Task) session.get(Task.class, taskId);
            if (task.getTitle() != null) {
                task1.setTitle(task.getTitle());
            }
            if (task.getDesc() != null) {
                task1.setDesc(task.getDesc());
            }
            if (task.getState() != null) {
                task1.setState(task.getState());
            }
            if (task.getAssignee()!=null){
                task1.setAssignee(task.getAssignee());
            }
            if (task.getActual()!= 0L){
                task1.setActual(task.getActual());
            }
            if (task.getEstimate()!= 0L){
                task1.setEstimate(task.getEstimate());
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void deleteTask(long taskId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            Task task = (Task) session.get(Task.class, taskId);
            
            Set<Task> setTasks = task.getProject().getListTasks();
        	Iterator<Task> itr = setTasks.iterator();
        	while( itr.hasNext() ){
        	    if( itr.next().getTaskId() == taskId ){
        		itr.remove();
        		break;
        	    }
        	}
            session.delete(task);
            tx.commit();
        }catch (HibernateException e){
            tx.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    @Override
    public Set<Task> getTasks(long projectId) {
	// TODO Auto-generated method stub
	return null;
    }
    
    @Override
    public List<Task> getTasksByUser(long userId) {
	
	List<Task> queryResult = null;
	Session session = sessionFactory.openSession();
	Transaction tx = session.beginTransaction();
	try {
	    
	    String queryString = "from Task ts where ts.assignee.userId = :userId ORDER BY ts.project.title DESC";
	    Query query = session.createQuery(queryString);
	    query.setLong("userId", userId);
	    
	    queryResult = ( List<Task> )query.list();
	    
	    tx.commit();
	    return queryResult;
	} catch (HibernateException e) {
	    tx.rollback();
	    return null;
	} finally {
	    session.close();
	}
	
    }
}
