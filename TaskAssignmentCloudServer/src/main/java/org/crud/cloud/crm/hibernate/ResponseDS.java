package org.crud.cloud.crm.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.crud.cloud.crm.Response;
import org.crud.cloud.crm.Task;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.openmobster.core.common.Utilities;
import org.openmobster.core.common.database.HibernateManager;
import org.openmobster.core.security.device.Device;

public class ResponseDS {
	

	private static Logger log = Logger.getLogger(ResponseDS.class);
	
	private HibernateManager hibernateManager;
	SessionFactory factory;
	TaskDS taskDs;
	String TAG= this.getClass().getName();
	
	public ResponseDS()
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
	
	
	
	
	public TaskDS getTaskDs() {
		return taskDs;
	}




	public void setTaskDs(TaskDS taskDs) {
		this.taskDs = taskDs;
	}




	public HibernateManager getHibernateManager() {
		
		log.info(TAG+" : "+"In getHibernateManager method");
		
		return hibernateManager;
	}




	public void setHibernateManager(HibernateManager hibernateManager) {
		
		log.info(TAG+" : "+"In setHibernateManager method");
		
		this.hibernateManager = hibernateManager;
	}

	
	
	
	public String create(Response response)
	{
		log.info(TAG+" : "+"In create method");
		
		Session session = null;
		Transaction tx = null;
		
		
		
		try
		{
			
			//session = this.hibernateManager.getSessionFactory().getCurrentSession();
			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			/*
			session = factory.getCurrentSession();
			if(session != null)
				session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			*/
			
			tx = session.beginTransaction();
			
			String responseSyncId = Utilities.generateUID();
			response.setResponseSyncId(responseSyncId);
			session.save(response);
						
			tx.commit();
			
			
			long taskServerId = response.getTaskServerId();
			Task task = taskDs.readByTaskByServerId(taskServerId);
			task.setTaskStatus(1);
			this.taskDs.update(task);
			
			
			return response.getResponseSyncId();
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


	public List<Response> readAll()
	{
		log.info(TAG+" : "+"In realAll method");
		
		Session session = null;
		Transaction tx = null;
		try
		{
			List<Response> responses = new ArrayList<Response>();
			

			//session = this.hibernateManager.getSessionFactory().getCurrentSession();
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			/*
			session = factory.getCurrentSession();
			if(session != null)
				session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			*/
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.Response";
			
			List cour = session.createQuery(query).list();
			
			if(cour != null)
			{
				responses.addAll(cour);
			}
						
			tx.commit();
			
			return responses;
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


	public List<Response> readAllResponsesByCreatorDeviceId(String deviceId)
	{
		
		log.info(TAG+" : "+"In readAllResponsesByCreatorDeviceId method");
	
		
		try
		{
			List<Response> responses = new ArrayList<Response>();
			
			List<Task> tasks = taskDs.readByTaskByCreaterDeviceId(deviceId);
			
			
			for(int i=0; i<tasks.size(); i++)
			{
				if(tasks.get(i).getTaskStatus() == 1)   // 1 means task has been responded
				{
					Response response = readByResponseByTaskId(tasks.get(i).getServerTaskId());
					if(response != null)
					{
						responses.add(response);
					}
					else
					{
						log.info(TAG + " : "+"Task status is set but Response is not found");
					}
				}
				
			}
			
			
			return responses;
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			throw new RuntimeException(e);
		}

		
	}

	
	
	
	
	public List<Response> readAllResponsesByAssigneeDeviceId(String deviceId)
	{
		
		log.info(TAG+" : "+"In readAllResponsesByAssigneeDeviceId method");
	
		
		try
		{
			List<Response> responses = new ArrayList<Response>();
			
			List<Task> tasks = taskDs.readByTaskByAssigneeDeviceId(deviceId);
			
			
			for(int i=0; i<tasks.size(); i++)
			{
				if(tasks.get(i).getTaskStatus() == 1)   // 1 means task has been responded
				{
					Response response = readByResponseByTaskId(tasks.get(i).getServerTaskId());
					if(response != null)
					{
						responses.add(response);
					}
					else
					{
						log.info(TAG + " : "+"Task status is set but Response is not found");
					}
				}
				
				
			}
			
			
			return responses;
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			throw new RuntimeException(e);
		}

		
	}
	

	public Response readByResponseBySyncId(String responseSyncId)
	{
		log.info(TAG+" : "+"In readByResponseBySyncId method");
		
		Session session = null;
		Transaction tx = null;
		try
		{
			Response response = null;
			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			/*
			session = factory.getCurrentSession();
			if(session != null)
				session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			*/
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.Response where responseSyncId=?";
			
			response = (Response)session.createQuery(query).setParameter(0, responseSyncId).uniqueResult();
						
			tx.commit();
			
			return response;
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


	public Response readByResponseByTaskId(long taskServerId)
	{
		log.info(TAG+" : "+"In  readByResponseByTaskId method");
		
		Session session = null;
		Transaction tx = null;
		try
		{
			Response response = null;
			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			/*
			session = factory.getCurrentSession();
			if(session != null)
				session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			*/
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.Response where taskServerId=?";
			
			response = (Response)session.createQuery(query).setParameter(0, taskServerId).uniqueResult();
						
			tx.commit();
			
			return response;
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


	public void update(Response response)
	{
		log.info(TAG+" : "+"In  update method");
		
		Session session = null;
		Transaction tx = null;
		try
		{

			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			tx = session.beginTransaction();
						
			session.update(response);
						
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
	
	
	public void delete(Response response)
	{
		log.info(TAG+" : "+"In  delete method");
		
		Session session = null;
		Transaction tx = null;
		try
		{

			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			tx = session.beginTransaction();
			
			session.delete(response);
						
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
		
		log.info(TAG+" : "+"In  deleteAll method");
		Session session = null;
		Transaction tx = null;
		try
		{

			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.Response";
			List cour = session.createQuery(query).list();			
			if(cour != null)
			{
				for(Object response: cour)
				{
					session.delete(response);
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
