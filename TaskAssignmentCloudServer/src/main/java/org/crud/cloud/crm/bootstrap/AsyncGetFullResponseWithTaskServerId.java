package org.crud.cloud.crm.bootstrap;

import org.apache.log4j.Logger;
import org.crud.cloud.crm.hibernate.DeviceDetailsDS;
import org.crud.cloud.crm.hibernate.ResponseDS;
import org.openmobster.cloud.api.rpc.MobileServiceBean;
import org.openmobster.cloud.api.rpc.Request;
import org.openmobster.cloud.api.rpc.Response;
import org.openmobster.cloud.api.rpc.ServiceInfo;

@ServiceInfo(uri="/async/getfullresponse")
public class AsyncGetFullResponseWithTaskServerId implements MobileServiceBean
{
	private static Logger log = Logger.getLogger(AsyncGetFullResponseWithTaskServerId.class);
	ResponseDS ds;
	
	String MOBILE_RESPONSE_ID = "mobileResponseId";
	String SERVER_RESPONSE_ID = "serverResponseId";
	String REPONSE_SYNC_ID = "responseSyncId";
	
	String TASK_SERVER_ID = "taskServerId";
	
	String RESPONSE_DESC = "responseDesc";
	String RESPONSE_MOBILE_TIMESTAMP = "responseMobileTimeStamp";
	String RESPONSE_SERVER_TIMESTAMP = "responseServerTimeStamp";
	
	
	String TAG = AsyncGetFullResponseWithTaskServerId.class.getName();
	
	public AsyncGetFullResponseWithTaskServerId()
	{
		log.info(TAG+" : "+"In Constructor");
		//this.ds = new DeviceDetailsDS();
		
		
	}
	
	
	
	
	public ResponseDS getDs() {
		log.info(TAG+" : "+"In getDs Method");
		return ds;
	}




	public void setDs(ResponseDS ds) {
		log.info(TAG+" : "+"In setDs Method");
		this.ds = ds;
	}




	public void start()
	{
		log.info(TAG);
		log.info("--------------------------------------------------------------------------");
		log.info("/async/getfullresponse: was successfully started....");
		log.info("--------------------------------------------------------------------------");
	}
	
	public Response invoke(Request request) 
	{	
		log.info(TAG);
		log.info("-------------------------------------------------");
		log.info(this.getClass().getName()+" successfully invoked...");		
		
		Response response = new Response();
		
		try
		{
			String taskServerId = request.getAttribute(TASK_SERVER_ID);
			
			
			
			
			
			org.crud.cloud.crm.Response taskResponse = this.ds.readByResponseByTaskId(new Long(taskServerId));
			/*
			String MOBILE_RESPONSE_ID = "mobileResponseId";
			String SERVER_RESPONSE_ID = "serverResponseId";
			String REPONSE_SYNC_ID = "responseSyncId";
			
			String TASK_SERVER_ID = "taskServerId";
			
			String RESPONSE_DESC = "responseDesc";
			String RESPONSE_MOBILE_TIMESTAMP = "responseMobileTimeStamp";
			String RESPONSE_SERVER_TIMESTAMP = "responseServerTimeStamp";
			*/
			response.setAttribute(MOBILE_RESPONSE_ID, ""+taskResponse.getMobileResponseId());
			response.setAttribute(SERVER_RESPONSE_ID, ""+taskResponse.getServerResponseId());
			response.setAttribute(REPONSE_SYNC_ID, taskResponse.getResponseSyncId());
			response.setAttribute(RESPONSE_DESC, taskResponse.getResponseDesc());
			response.setAttribute(RESPONSE_MOBILE_TIMESTAMP, ""+taskResponse.getResponseMobileTimeStamp());
			response.setAttribute(RESPONSE_SERVER_TIMESTAMP, ""+taskResponse.getResponseServerTimeStamp());
			
		}
		catch(Exception e)
		{
			log.info(TAG+" : Exception raised is "+e.getMessage());
		}
		
		/*
		ArrayList<DeviceDetails> deviceDetailsList= (ArrayList<DeviceDetails>) this.ds.listAllDevices(); 
		
		List<String> deviceUsersList = new ArrayList<String>();
		List<String> deviceIdsList = new ArrayList<String>();
		
		for(int i=0; i<deviceDetailsList.size() ; i++)
		{
			deviceUsersList.add(deviceDetailsList.get(i).getDeviceUserName());
			deviceIdsList.add(deviceDetailsList.get(i).getDeviceId());
			
			
		}
		    
		
		//response.setListAttribute(DEVICE_IDS, deviceIdsList);
		//response.setListAttribute(ASSIGNEES, deviceUsersList);
				
		log.info("-------------------------------------------------");
		*/
		return response;
	}
}