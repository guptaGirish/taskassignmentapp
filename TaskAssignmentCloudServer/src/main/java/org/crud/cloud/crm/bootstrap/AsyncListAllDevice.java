package org.crud.cloud.crm.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.crud.cloud.crm.DeviceDetails;
import org.crud.cloud.crm.hibernate.DeviceDetailsDS;
import org.openmobster.cloud.api.rpc.MobileServiceBean;
import org.openmobster.cloud.api.rpc.Request;
import org.openmobster.cloud.api.rpc.Response;
import org.openmobster.cloud.api.rpc.ServiceInfo;

@ServiceInfo(uri="/async/load/deviceslist")
public class AsyncListAllDevice implements MobileServiceBean
{
	private static Logger log = Logger.getLogger(AsyncListAllDevice.class);
	DeviceDetailsDS ds;
	public static String DEVICE_IDS = "deviceIds";
	public static String ASSIGNEES = "assignees";
	
	String TAG = AsyncListAllDevice.class.getName();
	
	public AsyncListAllDevice()
	{
		log.info(TAG+" : "+"In Constructor");
		//this.ds = new DeviceDetailsDS();
		
		
	}
	
	
	
	
	public DeviceDetailsDS getDs() {
		log.info(TAG+" : "+"In getDs Method");
		return ds;
	}




	public void setDs(DeviceDetailsDS ds) {
		log.info(TAG+" : "+"In setDs Method");
		this.ds = ds;
	}




	public void start()
	{
		log.info(TAG);
		log.info("--------------------------------------------------------------------------");
		log.info("/async/load/devicelist: was successfully started....");
		log.info("--------------------------------------------------------------------------");
	}
	
	public Response invoke(Request request) 
	{	
		log.info(TAG);
		log.info("-------------------------------------------------");
		log.info(this.getClass().getName()+" successfully invoked...");		
		
		Response response = new Response();
		
		ArrayList<DeviceDetails> deviceDetailsList= (ArrayList<DeviceDetails>) this.ds.listAllDevices(); 
		
		List<String> deviceUsersList = new ArrayList<String>();
		List<String> deviceIdsList = new ArrayList<String>();
		
		for(int i=0; i<deviceDetailsList.size() ; i++)
		{
			deviceUsersList.add(deviceDetailsList.get(i).getDeviceUserName());
			deviceIdsList.add(deviceDetailsList.get(i).getDeviceId());
			
			
		}
		    
		
		response.setListAttribute(DEVICE_IDS, deviceIdsList);
		response.setListAttribute(ASSIGNEES, deviceUsersList);
				
		log.info("-------------------------------------------------");
		
		return response;
	}
}
