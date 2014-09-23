package org.crud.cloud.crm;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.crud.cloud.crm.hibernate.DeviceDetailsDS;
import org.crud.cloud.crm.hibernate.TaskDS;
import org.openmobster.cloud.api.ExecutionContext;
import org.openmobster.cloud.api.sync.Channel;
import org.openmobster.cloud.api.sync.ChannelInfo;
import org.openmobster.cloud.api.sync.MobileBean;
import org.openmobster.core.common.transaction.TransactionHelper;
import org.openmobster.core.security.device.Device;
import org.openmobster.core.security.device.DeviceController;


@ChannelInfo(uri="task_assignee_channel", mobileBeanClass="org.crud.cloud.crm.Task")
public class TaskAssigneeChannel implements Channel {
	
	private static Logger log = Logger.getLogger(TaskAssigneeChannel.class);
	private TaskDS ds;
	private DeviceDetailsDS deviceDetailsDs;
	DeviceController deviceController;
	
	private NewTaskDetector mNewTaskDetector;
	
	
	String TAG = TaskAssigneeChannel.class.getName();
	
	public TaskAssigneeChannel()
	{
		log.info("------------------------------------------------------------------------------------");	
		log.info(TAG+" : "+"In constructor");
	   	mNewTaskDetector = new NewTaskDetector();
		//deviceController. 
	}
	
	
	
	
	public TaskDS getDs() {
		log.info("------------------------------------------------------------------------------------");
		return ds;
	}




	public void setDs(TaskDS ds) {
		log.info("------------------------------------------------------------------------------------");
		this.ds = ds;
	}




	public DeviceController getDeviceController() {
		log.info("------------------------------------------------------------------------------------");
		return deviceController;
	}




	public void setDeviceController(DeviceController deviceController) {
		log.info("------------------------------------------------------------------------------------");
		this.deviceController = deviceController;
	}

	
	


	public DeviceDetailsDS getDeviceDetailsDs() {
		return deviceDetailsDs;
	}




	public void setDeviceDetailsDs(DeviceDetailsDS deviceDetailsDs) {
		this.deviceDetailsDs = deviceDetailsDs;
	}




	public void start()
	{
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In start method");
		boolean startedHere = TransactionHelper.startTx();
		try
		{
			//List<Device> registeredDevices = deviceController.readAll();
			
			List<Device> activatedDevicesWithServer = deviceController.readAll();
			
			
			
			ArrayList<DeviceDetails> registeredDevices= (ArrayList<DeviceDetails>) this.deviceDetailsDs.listAllDevices(); 
			
			List<String> deviceIdToKeepActivated = new ArrayList<String>();
			
			if(registeredDevices == null)
			{
				return;
			}
			for(DeviceDetails deviceDetails:registeredDevices)
			{
				log.info("Loading device with id -"+deviceDetails.getDeviceId());
			
				Device device = new Device();
				device.setIdentifier(deviceDetails.getDeviceId());
				deviceIdToKeepActivated.add(deviceDetails.getDeviceId());
				this.mNewTaskDetector.load(device);
			}
			/*
			for(int i=0; i<activatedDevicesWithServer.size();i++)
			{
				if(deviceIdToKeepActivated.contains(activatedDevicesWithServer.get(i).getId()))
					continue;
					deviceController.delete(activatedDevicesWithServer.get(i));
			}
			*/
			
			
			
			if(startedHere)
			{
				TransactionHelper.commitTx();
			}
		}
		catch(Exception e)
		{
			log.error(this, e);
			
			if(startedHere)
			{
				TransactionHelper.rollbackTx();
			}
		}
	}
	
	public void stop()
	{
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In onStop method");
	}
	
	@Override
	public List<? extends MobileBean> bootup() {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In bootup method");
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("-----Boot up Method for device ID -"+device.getIdentifier()+"--Channel Task Assignee");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
	
		this.mNewTaskDetector.load(device);
		
		List<Task> bootup = new ArrayList<Task>();
		
		List<Task> all = this.ds.readAll();
		
		if(all != null && !all.isEmpty())
		{
			bootup.add(all.get(0));
		}
		else
		{
			Task t = new Task();
			t.setTaskTitle("initiating task");
			t.setTaskStatus(0);
			t.setAssigneDeviceId("assginee");
			t.setCreaterDeviceId("creater");
			t.setTaskServerTimeStamp(new Date(System.currentTimeMillis()));
			
			this.create(t);
		}
		return bootup; 

				
		//return this.readAll();
		//		return dummyTaskList;
	}

	@Override
	public String create(MobileBean newTaskBean) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In create method");
		/*
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		Task local = (Task)newTaskBean;
		String syncid=this.ds.create(local);
		mNewTaskDetector.addSyncId(device,syncid);
		*/
		return null;
		
	}

	@Override
	public void delete(MobileBean arg0) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In delete method");
	}

	@Override
	public MobileBean read(String taskSyncId) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In read method");
		return this.ds.readByTaskBySyncId(taskSyncId);
	}

	@Override
	public List<? extends MobileBean> readAll() {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In readAll method");
		
		
		log.info("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		log.info("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		log.info("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		log.info("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		log.info("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		log.info("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		log.info("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		log.info("Device id is -"+device.getIdentifier());
		//return this.ds.readByTaskByAssigneeDeviceId(device.getIdentifier());
		
		/*
		List<Task> dummyTaskList = new ArrayList<Task>();
		Task t = new Task();
		dummyTaskList.add(t);
				
		//return this.readAll();
		return dummyTaskList;
		*/
		return this.ds.readAll();
		//return null;
	}

	
	
	
	
	
	
	
	
	
	@Override
	public String[] scanForDeletions(Device arg0, Date arg1) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In scanForDeletions method");
		return null;
	}

	@Override
	public String[] scanForNew(Device device, Date lastTimeStamp) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In scanForNew method");
		log.info("------------------------------------------------------------------------------------");
		Set<String> newBeans = mNewTaskDetector.scanForNewBeans(device);
		if(newBeans != null && !newBeans.isEmpty())
		{
			log.info("------------------------------------------------------------------------------------");
			log.info("------------------------------------------------------------------------------------");
			log.info("------------------------------------------------------------------------------------");
			log.info(TAG+" New beans are being sent to device.  Detected beans are "+newBeans.size());
			log.info("------------------------------------------------------------------------------------");
			log.info("------------------------------------------------------------------------------------");
			log.info("------------------------------------------------------------------------------------");
			
			return newBeans.toArray(new String[0]); 
		}		
		return null;
		
	}

	@Override
	public String[] scanForUpdates(Device arg0, Date arg1) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In scanForUpdates method");
		return null;
	}

	@Override
	public void update(MobileBean arg0) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In update method");
	}

	
	
	
	private class NewTaskDetector 
	{
		private Map<String,Set<String>> device_to_task_bean_map;
		
		String TAG = NewTaskDetector.class.getName();
		public NewTaskDetector() {
			log.info("------------------------------------------------------------------------------------");
			// TODO Auto-generated constructor stub
			log.info(TAG+" : "+"In constructor");
			this.device_to_task_bean_map = new HashMap<String,Set<String>>();		
		}
		
		public void load(Device device)
		{
			log.info("------------------------------------------------------------------------------------");
			log.info(TAG+" : "+"In load method");
			String identifier=device.getIdentifier();
			
			/*
			if(device_to_task_bean_map.containsKey(identifier))
			{
				
			}
			*/
			Set<String> deviceAllAssignedTaskSyncId = this.readAllTaskWithDeviceIdentifier(identifier);
			device_to_task_bean_map.put(identifier,deviceAllAssignedTaskSyncId);
		}
		
		public Set<String> readAllTaskWithDeviceIdentifier(String identifier)
		{
			log.info("------------------------------------------------------------------------------------");
			log.info(TAG+" : "+"In readAllTaskWithDeviceIdentifier");
			
			Set <String>deviceAllAssignedTaskSyncId=new HashSet<String>();
			List<Task> deviceAllAssignedTaskBeanList = ds.readByTaskByAssigneeDeviceId(identifier);
			
			if(deviceAllAssignedTaskBeanList != null)
				log.info(TAG+" : "+"No. of tasks assigned are "+deviceAllAssignedTaskBeanList.size());
			
			for(Task taskBean:deviceAllAssignedTaskBeanList){
				String syncid=taskBean.getTaskSyncId();
				deviceAllAssignedTaskSyncId.add(syncid);			
			}		
			return deviceAllAssignedTaskSyncId;
			
		}
		
		
		
		/*
		
		public Set<String> readAll()
		{
			Set <String>allCreatedTaskSyncId=new HashSet<String>();
			List <Task>allCreatedTaskBeanList=ds.readAll();
			for(Task createdTaskBean:allCreatedTaskBeanList){
				String syncid=createdTaskBean.getTaskSyncId();
				allCreatedTaskSyncId.add(syncid);			
			}		
			return allCreatedTaskSyncId;
		}
		*/
		public Set<String> scanForNewBeans(Device device)
		{
			log.info("------------------------------------------------------------------------------------");
			log.info(TAG+" : "+"In scanForNewBeans method");
			//identified new created task beans
			Set <String>newAssignedTaskBeanSyncIdSet=new HashSet<String>();
			
			
			String deviceIdentifier=device.getIdentifier();
				
			
					//new list
					log.info(TAG+" : "+"Getting New list of assigned tasks");
					
					Set<String> latestAllAssignedTaskBeanIdentifierList = this.readAllTaskWithDeviceIdentifier(deviceIdentifier);
					
					//old list
					Set <String> oldAllAssignedTaskBeanIdentifierList=device_to_task_bean_map.get(deviceIdentifier);
					
					if(oldAllAssignedTaskBeanIdentifierList==null)
					{
						device_to_task_bean_map.put(deviceIdentifier,latestAllAssignedTaskBeanIdentifierList);
						return latestAllAssignedTaskBeanIdentifierList;
					}
					else
					{
						for(String syncid:latestAllAssignedTaskBeanIdentifierList){
							if(!oldAllAssignedTaskBeanIdentifierList.contains(syncid)){
								
								log.info("------------------------------------------------------------------------------------");
								log.info(TAG+" : "+"New Bean Detected");
								log.info("------------------------------------------------------------------------------------");
								newAssignedTaskBeanSyncIdSet.add(syncid);
								oldAllAssignedTaskBeanIdentifierList.add(syncid);
							}				
						}
						return newAssignedTaskBeanSyncIdSet;
					}
		
		
			
		}
		
		
		public void addSyncId(Device device,String syncId){
			log.info("------------------------------------------------------------------------------------");
			log.info(TAG+" : "+"In addSyncId method");
			String identifier=device.getIdentifier();
			Set <String> deviceAssignedTaskBeanSyncIdSet=device_to_task_bean_map.get(identifier);
			
			if(deviceAssignedTaskBeanSyncIdSet==null)
			{
				Set <String>syncIdSet=new HashSet<String>();
				syncIdSet.add(syncId);		
				device_to_task_bean_map.put(identifier,syncIdSet);
			}
			else
			{
				deviceAssignedTaskBeanSyncIdSet.add(syncId);
			}
		}	
	}
	
}
