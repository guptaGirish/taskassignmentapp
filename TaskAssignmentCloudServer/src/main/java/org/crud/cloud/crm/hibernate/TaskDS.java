package org.crud.cloud.crm.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.ant.taskdefs.Parallel.TaskList;
import org.crud.cloud.crm.Task;
import org.crud.cloud.crm.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.openmobster.core.common.Utilities;
import org.openmobster.core.common.database.HibernateManager;

public class TaskDS {
	
	private static Logger log = Logger.getLogger(TaskDS.class);
	
	private HibernateManager hibernateManager;
	SessionFactory factory;
	
	String TAG = this.getClass().getName();
	
	public TaskDS()
	{
		log.info(TAG+" : "+"In Constructor");
		/*
		try{ 
			factory = new Configuration().configure().buildSessionFactory(); 
		}
		catch (Throwable ex) { 
			System.err.println("Failed to create sessionFactory object." + ex); 
			throw new ExceptionInInitializerError(ex); 
		} 
		
		*/
		
	}
	

	public HibernateManager getHibernateManager() {
		
		log.info(TAG+" : "+"In getHibernateManager method");
		
		return hibernateManager;
	}




	public void setHibernateManager(HibernateManager hibernateManager) {
		
		log.info(TAG+" : "+"In setHibernateManager method");
		
		this.hibernateManager = hibernateManager;
	}




	public String create(Task task)
	{
		log.info(TAG+" : "+"In create method");
		
		Session session = null;
		Transaction tx = null;
		
		
		
		try
		{
			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			/*
			session = factory.getCurrentSession();
			if(session != null)
				session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			*/
			tx = session.beginTransaction();
			
			String taskSyncId = Utilities.generateUID();
			task.setTaskSyncId(taskSyncId);
			session.save(task);
						
			tx.commit();
			
			return task.getTaskSyncId();
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			
			throw new RuntimeException(e);
		}
	}


	public List<Task> readAll()
	{
		log.info(TAG+" : "+"In readAll method");
		Session session = null;
		Transaction tx = null;
		try
		{
			List<Task> tasks = new ArrayList<Task>();
			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			
			//session = factory.getCurrentSession();
			//if(session != null)
			//	session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			//
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.Task";
			
			List cour = session.createQuery(query).list();
			
			if(cour != null)
			{
				tasks.addAll(cour);
			}
						
			tx.commit();
			
			return tasks;
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
	}
	
	public Task readByTaskBySyncId(String taskSyncId)
	{
		log.info(TAG+" : "+"In readByTaskBySyncId method");
		Session session = null;
		Transaction tx = null;
		try
		{
			Task task = null;
			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			/*
			session = factory.getCurrentSession();
			if(session != null)
				session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			*/
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.Task where taskSyncId=?";
			
			task = (Task)session.createQuery(query).setParameter(0, taskSyncId).uniqueResult();
						
			tx.commit();
			
			return task;
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
	}


	public Task readByTaskByServerId(long taskServerId)
	{
		log.info(TAG+" : "+"In readByTaskByServerId method");
		Session session = null;
		Transaction tx = null;
		try
		{
			Task task = null;
			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			/*
			session = factory.getCurrentSession();
			if(session != null)
				session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			*/
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.Task where serverTaskId=?";
			
			task = (Task)session.createQuery(query).setParameter(0, taskServerId).uniqueResult();
						
			tx.commit();
			
			return task;
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
	}
	
	
	public List<Task> readByTaskByCreaterDeviceId(String deviceId)
	{
		log.info(TAG+" : "+"In readByTaskByCreaterDeviceId method");
		
		Session session = null;
		Transaction tx = null;
		try
		{
			List<Task> tasks = new ArrayList<Task>();
					
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			/*
			session = factory.getCurrentSession();
			if(session != null)
				session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			*/
			
			tx = session.beginTransaction();
			
			
			//String query = "from org.crud.cloud.crm.Task where creater_device_id=?";
			String query = "from org.crud.cloud.crm.Task where createrDeviceId=?";
			
			//task = (Task)session.createQuery(query).setParameter(0, deviceId).uniqueResult();
			
			//session.createQuery(query).setParameter
			
			List cour = session.createQuery(query).setParameter(0, deviceId).list();
			
			if(cour != null)
			{
				tasks.addAll(cour);
			}
						
			
			
			tx.commit();
			log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			log.info("In readByTaskByCreaterDeviceId Method, Retrieved list length is- "+tasks.size());
			log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			log.info("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			
			
			
			return tasks;
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
	}


	public List<Task> readByTaskByAssigneeDeviceId(String deviceId)
	{
		
		log.info(TAG+" : "+"In readByTaskByAssigneeDeviceId method");
		
		Session session = null;
		Transaction tx = null;
		try
		{
			List<Task> tasks = new ArrayList<Task>();
					
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			/*
			session = factory.getCurrentSession();
			if(session != null)
				session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			*/
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.Task where assigneDeviceId=?";
			
			//task = (Task)session.createQuery(query).setParameter(0, deviceId).uniqueResult();
			
			List cour = session.createQuery(query).setParameter(0, deviceId).list();
			
			if(cour != null)
			{
				tasks.addAll(cour);
			}
						
			log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			log.info("In readByTaskByAssigneeDeviceId Method, Retrieved list length is- "+tasks.size());
			log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			log.info("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
			
			
			tx.commit();
			
			return tasks;
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
	}

	
	
	public void update(Task task)
	{
		
		log.info(TAG+" : "+"In update method");
		
		Session session = null;
		Transaction tx = null;
		try
		{

			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			
			tx = session.beginTransaction();
						
			session.update(task);
						
			tx.commit();
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
	}
	
	
	public void delete(Task task)
	{
		log.info(TAG+" : "+"In delete method");
		
		Session session = null;
		Transaction tx = null;
		try
		{

			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			tx = session.beginTransaction();
			
			session.delete(task);
						
			tx.commit();
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
	}
	
	public void deleteAll()
	{
		
		log.info(TAG+" : "+"In deleteAll method");
		
		Session session = null;
		Transaction tx = null;
		try
		{

			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.Task";
			List cour = session.createQuery(query).list();			
			if(cour != null)
			{
				for(Object task: cour)
				{
					session.delete(task);
				}
			}						
						
			tx.commit();
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(tx != null)
			{
				tx.rollback();
			}
			throw new RuntimeException(e);
		}
	}
	
}


