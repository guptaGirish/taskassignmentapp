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
import org.crud.cloud.crm.hibernate.ResponseDS;
import org.crud.cloud.crm.hibernate.TaskDS;

import org.openmobster.cloud.api.ExecutionContext;
import org.openmobster.cloud.api.sync.Channel;
import org.openmobster.cloud.api.sync.ChannelInfo;
import org.openmobster.cloud.api.sync.MobileBean;
import org.openmobster.core.common.transaction.TransactionHelper;
import org.openmobster.core.security.device.Device;
import org.openmobster.core.security.device.DeviceController;


@ChannelInfo(uri="assignee_response_sync_channel", mobileBeanClass="org.crud.cloud.crm.Response")
public class AssigneeResponseSyncChannel implements Channel {
	
	private static Logger log = Logger.getLogger(AssigneeResponseSyncChannel.class);
	private ResponseDS ds;
	private DeviceDetailsDS deviceDetailsDs;
	DeviceController deviceController;
	String TAG = this.getClass().getName();
	private NewResponseDetector mNewResponseDetector;
	
	public AssigneeResponseSyncChannel()
	{
		log.info("*************************************************************");
		log.info(TAG+" : "+"In constructor");
		//deviceController. 
		mNewResponseDetector = new NewResponseDetector();
	}
	
	
	
	
	public ResponseDS getDs() {
		log.info("*************************************************************");
		log.info(TAG+" : "+"In getDs"+" Method");
		return ds;
	}




	public void setDs(ResponseDS ds) {
		log.info("*************************************************************");
		log.info(TAG+" : "+"In setDs"+" Method");
		this.ds = ds;
	}




	public DeviceController getDeviceController() {
		log.info("*************************************************************");
		log.info(TAG+" : "+"getDeviceController"+" Method");
		
		return deviceController;
	}




	public void setDeviceController(DeviceController deviceController) {
		log.info("*************************************************************");
		log.info(TAG+" : "+"setDeviceController"+" Method");
		
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
		log.info("*************************************************************");
		log.info(TAG+" : "+"start"+" Method");
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
				this.mNewResponseDetector.load(device);
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
		log.info("*************************************************************");
		log.info(TAG+" : "+"stop"+" Method");
	}
	
	@Override
	public List<? extends MobileBean> bootup() {
		// TODO Auto-generated method stub
		log.info("*************************************************************");
		log.info(TAG+" : "+"boot up"+" Method");
		
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		this.mNewResponseDetector.load(device);

		List<Response> bootup = new ArrayList<Response>();
		
		List<Response> all = this.ds.readAll();
		
		if(all != null && !all.isEmpty())
		{
			bootup.add(all.get(0));
		}
		else
		{
			Response t = new Response();
			
			t.setResponseMobileTimeStamp(""+System.currentTimeMillis());
			t.setResponseDesc("desc");
			t.setResponseServerTimeStamp(new Date(System.currentTimeMillis()));
			t.setTaskServerId(0);
			
			this.create(t);
		}
		return bootup; 


		
		//return this.readAll();
	}

	@Override
	public String create(MobileBean newResponseBean) {
		// TODO Auto-generated method stub
		log.info("*************************************************************");
		log.info(TAG+" : In "+"create"+" Method");
		
		
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		Response local = (Response)newResponseBean;
		
		log.info("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		log.info("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		log.info("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		log.info("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		log.info("Response Desc is -"+local.getResponseDesc());
		log.info("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		log.info("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		log.info("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
		
		local.setResponseServerTimeStamp(new Date(System.currentTimeMillis()));
		//local.setTaskServerTimeStamp(new Date(System.currentTimeMillis()));
		
		String syncid=this.ds.create(local);
		//mNewTaskDetector.addSyncId(device,syncid);
		return syncid;
		
	}

	@Override
	public void delete(MobileBean arg0) {
		// TODO Auto-generated method stub
		log.info("*************************************************************");
		log.info(TAG+" : In "+"delete"+" Method");
		
	}

	@Override
	public MobileBean read(String responseSyncId) {
		// TODO Auto-generated method stub
		log.info("*************************************************************");
		log.info(TAG+" : In "+"read response by sync id"+" Method");
		return this.ds.readByResponseBySyncId(responseSyncId);
	}

	@Override
	public List<? extends MobileBean> readAll() {
		// TODO Auto-generated method stub
		log.info("*************************************************************");
		log.info(TAG+" : In "+"read all "+" Method");
		
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		/*
		//return this.ds.readAllResponsesByAssigneeDeviceId(device.getIdentifier());
		List<Response> dummyResponseList = new ArrayList<Response>();
		Response t = new Response();
		dummyResponseList.add(t);
				
		//return this.readAll();
				return dummyResponseList;
		*/
		return this.ds.readAll();
	}

	
	
	
	
	
	
	
	
	
	@Override
	public String[] scanForDeletions(Device arg0, Date arg1) {
		// TODO Auto-generated method stub
		log.info("*************************************************************");
		
		log.info(TAG+" : In "+"scanForDeletions"+" Method");
		return null;
	}

	@Override
	public String[] scanForNew(Device device, Date lastTimeStamp) {
		// TODO Auto-generated method stub
		log.info("*************************************************************");
		log.info(TAG+" : In "+"scanForNew"+" Method");
		
		Set<String> newBeans = mNewResponseDetector.scanForNewBeans(device);
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
		log.info("*************************************************************");
		log.info(TAG+" : In "+"scanForUpdates"+" Method");
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(MobileBean arg0) {
		// TODO Auto-generated method stub
		log.info("*************************************************************");
		log.info(TAG+" : In "+"update"+" Method");
		
		
	}

	
	
	
	private class NewResponseDetector 
	{
		private Map<String,Set<String>> device_to_response_bean_map;
		
		String TAG = this.getClass().getName();
		public NewResponseDetector() {
			log.info("*************************************************************");
			log.info(TAG+" : In Constructor");
			// TODO Auto-generated constructor stub
			this.device_to_response_bean_map = new HashMap<String,Set<String>>();		
		}
		
		public void load(Device device)
		{
			log.info("*************************************************************");
			log.info(TAG+" : In "+"load"+" Method");
			
			//load all tasks of device
			// then load all responses of those tasks
			
			String identifier=device.getIdentifier();
			
			Set<String> deviceAllResponsesSyncId = this.readAllResponsesWithDeviceIdentifier(identifier);
			device_to_response_bean_map.put(identifier,deviceAllResponsesSyncId);
		}
		
		
		
		
		public Set<String> readAllResponsesWithDeviceIdentifier(String identifier)
		{
			log.info("*************************************************************");
			log.info(TAG+" : In "+"readAllResponseWithDeviceIdentifier"+" Method");
			
			Set <String>deviceAllResponsesSyncId=new HashSet<String>();
			
			List<Response> deviceAllResponseBeanList = ds.readAllResponsesByAssigneeDeviceId(identifier);
			
			if(deviceAllResponseBeanList!= null)
				log.info(TAG+" : No. of responses are "+deviceAllResponseBeanList.size());
			
			for(Response responseBean:deviceAllResponseBeanList){
				String syncid=responseBean.getResponseSyncId();
				deviceAllResponsesSyncId.add(syncid);			
			}		
			return deviceAllResponsesSyncId;
			
		}
		
		
		
		public Set<String> scanForNewBeans(Device device)
		{
			log.info("*************************************************************");
			log.info(TAG+" : In "+"scanForNewBeans"+" Method");
			
			//identified new created responses beans
			Set <String>newResponsesBeanSyncIdSet=new HashSet<String>();
			
			
			String deviceIdentifier=device.getIdentifier();
			
			//new list
			Set<String> latestAllResponseTaskBeanIdentifierList = this.readAllResponsesWithDeviceIdentifier(deviceIdentifier);
			
			//old list
			Set <String> oldAllResponsesTaskBeanIdentifierList=device_to_response_bean_map.get(deviceIdentifier);
			
			if(oldAllResponsesTaskBeanIdentifierList==null)
			{
				device_to_response_bean_map.put(deviceIdentifier,latestAllResponseTaskBeanIdentifierList);
				return latestAllResponseTaskBeanIdentifierList;
			}
			else
			{
				for(String syncid:latestAllResponseTaskBeanIdentifierList){
					if(!oldAllResponsesTaskBeanIdentifierList.contains(syncid)){
						
						log.info("*************************************************************");
						log.info("New Bean Detected.");
						log.info("*************************************************************");
						
						newResponsesBeanSyncIdSet.add(syncid);
						oldAllResponsesTaskBeanIdentifierList.add(syncid);
					}				
				}
				return newResponsesBeanSyncIdSet;
			}
			
		}
		
		
		public void addSyncId(Device device,String responseSyncId){
			log.info("*************************************************************");
			log.info(TAG+" : In "+"addSyncId"+" Method");
			
			String identifier=device.getIdentifier();
			Set <String> deviceResponsesBeanSyncIdSet=device_to_response_bean_map.get(identifier);
			
			if(deviceResponsesBeanSyncIdSet==null)
			{
				Set <String>syncIdSet=new HashSet<String>();
				syncIdSet.add(responseSyncId);		
				device_to_response_bean_map.put(identifier,syncIdSet);
			}
			{
				deviceResponsesBeanSyncIdSet.add(responseSyncId);
			}
		}	
	}
	
}

