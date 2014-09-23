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


@ChannelInfo(uri="task_creation_channel", mobileBeanClass="org.crud.cloud.crm.Task")
public class TaskCreationChannel implements Channel {
	
	String TAG = TaskCreationChannel.class.getName();
	private static Logger log = Logger.getLogger(TaskCreationChannel.class);
	
	private TaskDS ds;
	private DeviceDetailsDS deviceDetailsDs;
	DeviceController deviceController;
	
	private NewTaskDetector mNewTaskDetector;
	
	public TaskCreationChannel()
	{
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In Constructor");
		//ExecutionContext context = ExecutionContext.getInstance();
		//Device device = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device.getIdentifier());

		
		mNewTaskDetector = new NewTaskDetector();
		//deviceController. 
	}
	
	
	
	
	public TaskDS getDs() {

		log.info("------------------------------------------------------------------------------------");
		//ExecutionContext context = ExecutionContext.getInstance();
		//Device device = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device.getIdentifier());

		
		return ds;
	}




	public void setDs(TaskDS ds) {
		log.info("------------------------------------------------------------------------------------");
		//ExecutionContext context = ExecutionContext.getInstance();
		//Device device = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device.getIdentifier());
		
		this.ds = ds;
	}


	

	public DeviceDetailsDS getDeviceDetailsDs() {
		return deviceDetailsDs;
	}




	public void setDeviceDetailsDs(DeviceDetailsDS deviceDetailsDs) {
		this.deviceDetailsDs = deviceDetailsDs;
	}




	public DeviceController getDeviceController() {
		log.info("------------------------------------------------------------------------------------");
		//ExecutionContext context = ExecutionContext.getInstance();
		//Device device = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device.getIdentifier());
		
		
		return deviceController;
	}




	public void setDeviceController(DeviceController deviceController) {
		log.info("------------------------------------------------------------------------------------");
		this.deviceController = deviceController;

		//ExecutionContext context = ExecutionContext.getInstance();
		//Device device = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device.getIdentifier());

	}




	public void start()
	{
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In onStart method");

		//ExecutionContext context = ExecutionContext.getInstance();
		//Device device1 = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device1.getIdentifier());

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
		//ExecutionContext context = ExecutionContext.getInstance();
		//Device device = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device.getIdentifier());

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
		log.info("-----Boot up Method for device ID -"+device.getIdentifier()+"--Channel Task Creation");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		log.info("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
		
		
		
		log.info(TAG+" : "+"Device id is "+device.getIdentifier());
		
		
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

		//return null;
	}

	@Override
	public String create(MobileBean newTaskBean) {
		// TODO Auto-generated method stub
		
		log.info("------------------------------------------------------------------------------------");
		
		log.info(TAG+" : "+"In create method");

		
		ExecutionContext context1 = ExecutionContext.getInstance();
		Device device1 = context1.getDevice();
		log.info(TAG+" : "+"Device id is "+device1.getIdentifier());

		
		
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		Task local = (Task)newTaskBean;
		
		
		local.setTaskServerTimeStamp(new Date(System.currentTimeMillis()));
		String syncid=this.ds.create(local);
		//mNewTaskDetector.addSyncId(device,syncid);
		return syncid;
		
	}

	@Override
	public void delete(MobileBean arg0) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In delete method");
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		log.info(TAG+" : "+"Device id is "+device.getIdentifier());

		
	}

	@Override
	public MobileBean read(String taskSyncId) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In read method by sync id");

		
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		log.info(TAG+" : "+"Device id is "+device.getIdentifier());
		log.info(TAG+" : "+"Sync id is "+taskSyncId);

		
		return this.ds.readByTaskBySyncId(taskSyncId);
	}

	
	
	@Override
	public List<? extends MobileBean> readAll() {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In readAll method");

		ExecutionContext context1 = ExecutionContext.getInstance();
		Device device1 = context1.getDevice();
		log.info(TAG+" : "+"Device id is "+device1.getIdentifier());

		
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		//return this.ds.readByTaskByCreaterDeviceId(device.getIdentifier());
		
					
		return this.ds.readAll();
		
	}

	
	
	
	
	
	
	
	
	
	@Override
	public String[] scanForDeletions(Device arg0, Date arg1) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In scanForDeletions method");
		//ExecutionContext context = ExecutionContext.getInstance();
		//Device device = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device.getIdentifier());

		return null;
	}

	@Override
	public String[] scanForNew(Device device, Date lastTimeStamp) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In scanForNew method");
		//ExecutionContext context = ExecutionContext.getInstance();
		//Device device1 = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device1.getIdentifier());

		
		
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
		//ExecutionContext context = ExecutionContext.getInstance();
	//	Device device = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device.getIdentifier());

		return null;
	}

	@Override
	public void update(MobileBean arg0) {
		// TODO Auto-generated method stub
		log.info("------------------------------------------------------------------------------------");
		log.info(TAG+" : "+"In update method");
		//ExecutionContext context = ExecutionContext.getInstance();
		//Device device = context.getDevice();
		//log.info(TAG+" : "+"Device id is "+device.getIdentifier());

		
	}

	
	
	
	private class NewTaskDetector 
	{
		private Map<String,Set<String>> device_to_task_bean_map;
		private String TAG = NewTaskDetector.class.getName();
		public NewTaskDetector() {
			// TODO Auto-generated constructor stub
			log.info("------------------------------------------------------------------------------------");
			log.info(TAG+" : "+"In constructor");
			//ExecutionContext context = ExecutionContext.getInstance();
			//Device device = context.getDevice();
			//log.info(TAG+" : "+"Device id is "+device.getIdentifier());

			this.device_to_task_bean_map = new HashMap<String,Set<String>>();		
		}
		
		public void load(Device device)
		{
			log.info("------------------------------------------------------------------------------------");
			log.info(TAG+" : "+"In load method");
			//ExecutionContext context = ExecutionContext.getInstance();
			//Device device1 = context.getDevice();
			//log.info(TAG+" : "+"Device id is "+device1.getIdentifier());

			String identifier=device.getIdentifier();
			
			/*
			if(device_to_task_bean_map.containsKey(identifier))
			{
				
			}
			*/
			Set<String> deviceAllCreatedTaskSyncId = this.readAllTaskWithDeviceIdentifier(identifier);
			device_to_task_bean_map.put(identifier,deviceAllCreatedTaskSyncId);
		}
		
		public Set<String> readAllTaskWithDeviceIdentifier(String identifier)
		{
			log.info("------------------------------------------------------------------------------------");
			log.info(TAG+" : "+"In readAllTaskWithDeviceIdentifier method");
			//ExecutionContext context = ExecutionContext.getInstance();
			//Device device = context.getDevice();
			//log.info(TAG+" : "+"Device id is "+device.getIdentifier());

			
			
			Set <String>deviceAllCreatedTaskSyncId=new HashSet<String>();
			List<Task> deviceAllCreatedTaskBeanList = ds.readByTaskByCreaterDeviceId(identifier);
			
			if(deviceAllCreatedTaskBeanList != null)
				log.info(TAG+" : "+"Length of list is "+deviceAllCreatedTaskBeanList.size());
			
			for(Task taskBean:deviceAllCreatedTaskBeanList){
				String syncid=taskBean.getTaskSyncId();
				deviceAllCreatedTaskSyncId.add(syncid);			
			}		
			return deviceAllCreatedTaskSyncId;
			
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
			log.info(TAG+" : "+"In scnaForNewBeans method");
			//ExecutionContext context = ExecutionContext.getInstance();
			//Device device1 = context.getDevice();
			//log.info(TAG+" : "+"Device id is "+device1.getIdentifier());

			//identified new created task beans
			Set <String>newCreatedTaskBeanSyncIdSet=new HashSet<String>();
			
			
			String deviceIdentifier=device.getIdentifier();
			
			//new list
			log.info(TAG+" : "+"Getting New List");
			Set<String> latestAllCreatedTaskBeanIdentifierList = this.readAllTaskWithDeviceIdentifier(deviceIdentifier);
			if(latestAllCreatedTaskBeanIdentifierList != null)
				log.info(TAG+" : "+"New List length is "+latestAllCreatedTaskBeanIdentifierList.size());
			//old list
			log.info(TAG+" : "+"Setting old List");
			Set <String> oldAllCreatedTaskBeanIdentifierList=device_to_task_bean_map.get(deviceIdentifier);
			
			if(oldAllCreatedTaskBeanIdentifierList==null)
			{
				log.info(TAG+" : "+"old List of device is "+null);
				device_to_task_bean_map.put(deviceIdentifier,latestAllCreatedTaskBeanIdentifierList);
				return latestAllCreatedTaskBeanIdentifierList;
			}
			else
			{
				for(String syncid:latestAllCreatedTaskBeanIdentifierList){
					if(!oldAllCreatedTaskBeanIdentifierList.contains(syncid)){
			
						log.info("------------------------------------------------------------------------------------");
						log.info(TAG+" : "+"New Bean Detected");
						log.info("------------------------------------------------------------------------------------");
						newCreatedTaskBeanSyncIdSet.add(syncid);
						oldAllCreatedTaskBeanIdentifierList.add(syncid);
					}				
				}
				return newCreatedTaskBeanSyncIdSet;
			}
			
		}
		
		
		public void addSyncId(Device device,String syncId){
			log.info("------------------------------------------------------------------------------------");
			log.info(TAG+" : "+"In addSyncId method");
			//ExecutionContext context = ExecutionContext.getInstance();
			//Device device1 = context.getDevice();
			//log.info(TAG+" : "+"Device id is "+device1.getIdentifier());

			
			String identifier=device.getIdentifier();
			Set <String> deviceCreatedTaskBeanSyncIdSet=device_to_task_bean_map.get(identifier);
			
			if(deviceCreatedTaskBeanSyncIdSet==null)
			{
				Set <String>syncIdSet=new HashSet<String>();
				syncIdSet.add(syncId);		
				device_to_task_bean_map.put(identifier,syncIdSet);
			}
			{
				deviceCreatedTaskBeanSyncIdSet.add(syncId);
			}
		}	
	}
	
}
