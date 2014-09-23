package org.crud.cloud.crm.bootstrap;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.crud.cloud.crm.Task;
import org.crud.cloud.crm.hibernate.ResponseDS;
import org.crud.cloud.crm.hibernate.TaskDS;
import org.openmobster.cloud.api.ExecutionContext;
import org.openmobster.cloud.api.rpc.MobileServiceBean;
import org.openmobster.cloud.api.rpc.Request;
import org.openmobster.cloud.api.rpc.Response;
import org.openmobster.cloud.api.rpc.ServiceInfo;
import org.openmobster.core.security.device.Device;

import EDU.oswego.cs.dl.util.concurrent.Takable;


@ServiceInfo(uri="/async/createdtasklist")
public class AsyncCreatedTaskList implements MobileServiceBean
{
	private static Logger log = Logger.getLogger(AsyncCreatedTaskList.class);
	//ResponseDS ds;
	TaskDS ds;
	
	String TASK_SERVER_ID_LIST = "TASK_SERVER_ID_LIST";
	String TASK_SYNC_ID_LIST = "TASK_SYNC_ID_LIST";
	String TASK_TITLE_LIST = "TASK_TITLE_LIST";
	String TASK_DESC_LIST = "TASK_DESC_LIST";
	String TASK_CREATED_TIMESTAMP_LIST = "TASK_CREATED_TIMESTAMP_LIST";
	String TASK_CREATER_DEVICE_ID_LIST = "TASK_CREATER_DEVICE_ID_LIST";
	String TASK_ASSIGNEE_DEVICE_ID_LIST = "TASK_ASSIGNEE_DEVICE_ID_LIST";
	
	
	String TAG = AsyncGetFullResponseWithTaskServerId.class.getName();
	
	public AsyncCreatedTaskList()
	{
		log.info(TAG+" : "+"In Constructor");
		//this.ds = new DeviceDetailsDS();
		
		
	}
	
	
	
	
	public TaskDS getDs() {
		log.info(TAG+" : "+"In getDs Method");
		return ds;
	}




	public void setDs(TaskDS ds) {
		log.info(TAG+" : "+"In setDs Method");
		this.ds = ds;
	}




	public void start()
	{
		log.info(TAG);
		log.info("--------------------------------------------------------------------------");
		log.info("/async/createdtasklist: was successfully started....");
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
			
			
			ExecutionContext context = ExecutionContext.getInstance();
			Device device = context.getDevice();
			
			
			List<Task> taskList = this.ds.readByTaskByCreaterDeviceId(device.getIdentifier());
			
			
			List<String> taskServerIdList = new ArrayList<String>();
			List<String> taskSyncIdList = new ArrayList<String>();
			List<String> taskTitleList = new ArrayList<String>();
			List<String> taskDescList = new ArrayList<String>();
			List<String> taskCreatedTimeStampList = new ArrayList<String>();
			List<String> taskCreaterDeviceIdList = new ArrayList<String>();
			List<String> taskAssigneeDeviceIdList = new ArrayList<String>();
			
			


			for(int i=0;i<taskList.size();i++)
			{
				
				Task task = taskList.get(i);
				
				taskServerIdList.add(""+task.getServerTaskId());
				taskSyncIdList.add(task.getTaskSyncId());
				taskTitleList.add(task.getTaskTitle());
				taskDescList.add(task.getTaskDesc());
				taskCreatedTimeStampList.add(""+task.getTaskServerTimeStamp());
				taskCreaterDeviceIdList.add(""+task.getCreaterDeviceId());
				taskAssigneeDeviceIdList.add(""+task.getAssigneDeviceId());
				
			}
			
			
			response.setListAttribute(TASK_SERVER_ID_LIST, taskServerIdList);
			response.setListAttribute(TASK_SYNC_ID_LIST, taskSyncIdList);
			response.setListAttribute(TASK_TITLE_LIST, taskTitleList);
			response.setListAttribute(TASK_DESC_LIST, taskDescList);
			response.setListAttribute(TASK_CREATED_TIMESTAMP_LIST, taskCreatedTimeStampList);
			response.setListAttribute(TASK_CREATER_DEVICE_ID_LIST, taskCreaterDeviceIdList);
			response.setListAttribute(TASK_ASSIGNEE_DEVICE_ID_LIST, taskAssigneeDeviceIdList);
		}
		catch(Exception e)
		{
			log.info(TAG+" : Exception raised is "+e.getMessage());
		}
		
		
		return response;
	}
}