package org.crud.cloud.crm.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.crud.cloud.crm.DeviceDetails;
import org.crud.cloud.crm.Task;
import org.crud.cloud.crm.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.openmobster.core.common.Utilities;
import org.openmobster.core.common.database.HibernateManager;

public class DeviceDetailsDS {

private static Logger log = Logger.getLogger(DeviceDetailsDS.class);
	
	private HibernateManager hibernateManager;
	SessionFactory factory;
	
	String TAG = this.getClass().getName();
	
	public DeviceDetailsDS() {
		// TODO Auto-generated constructor stub
		log.info(TAG+" : "+"In DeviceDetailsDS");
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


	public String create(DeviceDetails deviceDetaiils)
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
			
			//String taskSyncId = Utilities.generateUID();
			//task.setTaskSyncId(taskSyncId);
			session.save(deviceDetaiils);
						
			tx.commit();
			
			return deviceDetaiils.getDeviceId();
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

	public List<DeviceDetails> listAllDevices()
	{
		log.info(TAG+" : "+"In listAllDevices Method");
		Session session = null;
		Transaction tx = null;
		try
		{
			List<DeviceDetails> listDevices = new ArrayList<DeviceDetails>();
			
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
			
			String query = "from org.crud.cloud.crm.DeviceDetails";
			
			List cour = session.createQuery(query).list();
			
			if(cour != null)
			{
				listDevices.addAll(cour);
			}
						
			tx.commit();
			
			return listDevices;
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

	

	public DeviceDetails getDeviceDetailsByDeviceId(String deviceId)
	{
		log.info(TAG+" : "+"In getDeviceDetailsByDeviceId method");
		Session session = null;
		Transaction tx = null;
		try
		{
			DeviceDetails deviceDetails = null;
			
			if(this.hibernateManager == null)
				log.info(TAG+" : "+"Hibernate Manager is null");
			
			if(this.hibernateManager.getSessionFactory() == null)
				log.info(TAG+" : "+"Session Factory is null");
			
			if(this.hibernateManager.getSessionFactory().getCurrentSession() == null)
				log.info(TAG+" : "+"getCurrentsession is null");
			
			
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			/*
			session = factory.getCurrentSession();
			if(session != null)
				session = factory.openSession();//this.hibernateManager.getSessionFactory().openSession();
			*/
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.DeviceDetails where deviceId=?";
			
			deviceDetails = (DeviceDetails)session.createQuery(query).setParameter(0, deviceId).uniqueResult();
						
			tx.commit();
			
			return deviceDetails;
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

	public void updateDeviceDetails(DeviceDetails device)
	{
		
		log.info(TAG+" : "+"In updateDeviceDetails method");
		
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
						
			session.update(device);
						
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
	
	
	
	public void removeDevice(DeviceDetails device)
	{
		log.info(TAG+" : "+"In removeDevice method");
		
		Session session = null;
		Transaction tx = null;
		try
		{
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			
			tx = session.beginTransaction();
			
			session.delete(device);
						
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
	
	public void removeAllDevices()
	{
		log.info(TAG+" : "+"In removeAllDevice method");
		
		Session session = null;
		Transaction tx = null;
		try
		{
			session = this.hibernateManager.getSessionFactory().getCurrentSession();
			if(session != null)
				session = this.hibernateManager.getSessionFactory().openSession();
			
			
			tx = session.beginTransaction();
			
			String query = "from org.crud.cloud.crm.DeviceDetails";
			List cour = session.createQuery(query).list();			
			if(cour != null)
			{
				for(Object device: cour)
				{
					session.delete(device);
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
