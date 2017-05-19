package com.sjsu.project.dao.impl;

import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;

import com.sjsu.project.controller.UserController;
import com.sjsu.project.dao.UserDao;
import com.sjsu.project.model.Project;
import com.sjsu.project.model.User;
import com.sjsu.project.vo.UserVo;
/*
 * @author Izhar Raazi 
 */

@Repository
public class UserDaoImpl implements UserDao {

    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;

    }

    public void createUser(User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            session.save(user);
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public User getUser(long userId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            User user = (User) session.get(User.class, userId);
            tx.commit();
            return user;
        }catch (HibernateException e){
            tx.rollback();
            return null;
        }finally {
            session.close();
        }
    }

    public void updateUser(long userId, User user) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            User user1 = (User) session.get(User.class, userId);
            if (user.getName() != null) {
                user1.setName(user.getName());
            }
            if (user.getPassword() != null) {
                user1.setPassword(user.getPassword());
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public void deleteUser(long userId) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            User user = (User) session.get(User.class, userId);
            session.delete(user);
            tx.commit();
        }catch (HibernateException e){
            tx.rollback();
            throw e;
        }finally {
            session.close();
        }
    }

    public void addProjects(long userId, Project project) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
            User user1 = (User) session.get(User.class, userId);
            if (user1.getProjects() != null) {
                Set<Project> projectSet = user1.getProjects();
                projectSet.add(project);
                user1.setProjects(projectSet);
            }
            tx.commit();
        } catch (HibernateException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    @Override
    public List<User> getAll() {
	
	 Session session = sessionFactory.openSession();
	        Transaction tx = session.beginTransaction();
	        try {
	            @SuppressWarnings("unchecked")
		    List<User> list = session.createCriteria(User.class).list();
	            tx.commit();
	            return list;
	        }catch (HibernateException e){
	            tx.rollback();
	            return null;
	        }finally {
	            session.close();
	        }
    }

 public UserVo validateCredential(String email,String password) {
        Session session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();
        try {
        	UserVo  uservo  = new UserVo();
        	User  user = new User();
        	String encrypt_pwd = UserController.cryptWithMD5(password);
            String queryString = "from User where email = :email and password =:password";
		    Query query = session.createQuery(queryString);
		    query.setString("email", email);
		    query.setString("password", encrypt_pwd);
		    Object queryResult = query.uniqueResult();
		    if( queryResult == null){
		    	return null;	
		     }
		    user  = (User)queryResult;
		    uservo.setEmail(user.getEmail());
		    uservo.setUserName(user.getName());
		    uservo.setUserId(user.getUserId());
            tx.commit();
            return uservo;
        }catch (HibernateException e){
            tx.rollback();
            return null;
        }finally {
            session.close();
        }
    }
 
 
}
