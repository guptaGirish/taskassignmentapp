package org.crud.cloud.crm.bootstrap;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.crud.cloud.crm.DeviceDetails;
import org.crud.cloud.crm.hibernate.DeviceDetailsDS;
import org.openmobster.cloud.api.ExecutionContext;
import org.openmobster.cloud.api.rpc.MobileServiceBean;
import org.openmobster.cloud.api.rpc.Request;
import org.openmobster.cloud.api.rpc.Response;
import org.openmobster.cloud.api.rpc.ServiceInfo;
import org.openmobster.core.security.device.Device;

@ServiceInfo(uri="/async/registerdevice")
public class AsyncRegisterDeviceWithDb implements MobileServiceBean
{
	private static Logger log = Logger.getLogger(AsyncRegisterDeviceWithDb.class);
	DeviceDetailsDS ds;
	
	/*
	public static String DEVICE_ID = "deviceIds";
	public static String DEVICE_LASTTIMEACTIVATED_TIMESTAMP = "lastTimeActivatedTimeStamp";
	public static String DEVICE_USER_NAME = "deviceUserName";
	public static String DEVICE_USER_CONTACT = "deviceUserContact";
	public static String DEVICE_USER_COMPANY = "deviceUserCompany";
	*/
	
	public static String DEVICE_ID_OVER_BAAS = "DEVICE_ID_OVER_BAAS";
	public static String DEVICE_USER_NAME_OVER_BAAS = "DEVICE_USER_NAME_OVER_BAAS";
	public static String DEVICE_USER_CONTACT_OVER_BAAS = "DEVICE_USER_CONTACT_OVER_BAAS";
	public static String DEVICE_USER_COMPANY_OVER_BAAS = "DEVICE_USER_COMPANY_OVER_BAAS";
	
	String TAG = AsyncRegisterDeviceWithDb.class.getName();
	
	
	
	
	
	public DeviceDetailsDS getDs() {
		log.info(TAG+" : "+"In getDs Method");
		return ds;
	}

	public void setDs(DeviceDetailsDS ds) {
		log.info(TAG+" : "+"In setDs Method");
		this.ds = ds;
	}

	public AsyncRegisterDeviceWithDb()
	{
		log.info(TAG+" : "+"In Constructor");
		//this.ds = new DeviceDetailsDS();
	}
	
	public void start()
	{
		log.info("--------------------------------------------------------------------------");
		log.info("/async/registerdevice  was successfully started....");
		log.info("--------------------------------------------------------------------------");
	}
	
	public Response invoke(Request request) 
	{	
		log.info("-------------------------------------------------");
		log.info(this.getClass().getName()+" successfully invoked...");		
		
		ExecutionContext context = ExecutionContext.getInstance();
		Device device = context.getDevice();
		
		DeviceDetails deviceDetails = new DeviceDetails();
		deviceDetails.setDeviceUserName(request.getAttribute(DEVICE_USER_NAME_OVER_BAAS));
		deviceDetails.setDeviceUserContact(request.getAttribute(DEVICE_USER_CONTACT_OVER_BAAS));
		deviceDetails.setDeviceUserCompany(request.getAttribute(DEVICE_USER_COMPANY_OVER_BAAS));
		deviceDetails.setLastTimeActivatedTimeStamp(new Date(System.currentTimeMillis()));
		
		deviceDetails.setDeviceId(device.getIdentifier());
		
		Response response = new Response();
		
		try{
			
			if(this.ds.getDeviceDetailsByDeviceId(device.getIdentifier())== null)
			{
				log.info(TAG+" : "+"new device to be inserted");
				this.ds.create(deviceDetails);
				response.setAttribute(DEVICE_ID_OVER_BAAS, deviceDetails.getDeviceId());
				
			}
			else
			{
				log.info(TAG+" : "+"device already in db");
				response.setAttribute(DEVICE_ID_OVER_BAAS, "available");
				return response;
			}
		}
		catch(Exception e)
		{
			log.info(e.getMessage());
			response.setAttribute(DEVICE_ID_OVER_BAAS, "");
		}
		
				
		log.info("-------------------------------------------------");
		
		return response;
	}
}
