package org.crud.cloud.crm;

import java.util.Date;

import org.crud.cloud.crm.hibernate.DeviceDetailsDS;
import org.crud.cloud.crm.hibernate.ResponseDS;
import org.crud.cloud.crm.hibernate.TaskDS;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hsqldb.persist.Log;



public class TestDataSources {
	
	static String TAG = TestDataSources.class.getName();
	TaskDS taskDS;
	ResponseDS responseDS;
	
	public static void main(String[] s)
	{
		
		
		
		
		/*
		Task task = new Task();
		task.setAssigneDeviceId("efnsjowo932402");
		task.setCreaterDeviceId("efnsjowo932402");
		task.setMobileTaskId(10);
		task.setTaskDesc("new task, to be completed by this weekend");
		task.setTaskMobileTimeStamp(new Date(System.currentTimeMillis()));
		task.setTaskServerTimeStamp(new Date(System.currentTimeMillis()));
		task.setTaskStatus(0);
		task.setTaskTitle("new task");
		
		TaskDS taskDs = new TaskDS();
		
		String task_sync_id = taskDs.create(task);
		System.out.println("sync id of create task is "+task_sync_id);
		
		Task task2 = new Task();
		task2.setAssigneDeviceId("efnsjowo932402");
		task2.setCreaterDeviceId("efnsjowo932402");
		task2.setMobileTaskId(10);
		task2.setTaskDesc("new task, to be completed by this weekend");
		task2.setTaskMobileTimeStamp(new Date(System.currentTimeMillis()));
		task2.setTaskServerTimeStamp(new Date(System.currentTimeMillis()));
		task2.setTaskStatus(0);
		task2.setTaskTitle("new1 task");
		
		
		task_sync_id = taskDs.create(task2);
		
		
		
		System.out.println("sync id of create task is "+task_sync_id);
		
		//taskDs.readAll();
		
		Task task1 = taskDs.readByTaskSyncId(task_sync_id);
		
		System.out.println("Fetched task object is "+task1.toString());
		//Log.v(TAG, "Sync id of created task is "+task_sync_id);
		
		*/
		/*
		ResponseDS responseDS = new ResponseDS();
		
		Response response = new Response();
		response.setMobileResponseId(15);
		response.setResponseMobileTimeStamp(new Date(System.currentTimeMillis()));
		response.setResponseServerTimeStamp(new Date(System.currentTimeMillis()));
		response.setResponseDesc("assigned has been completed");
		response.setTaskServerId(1);
		
		String response_sync_id = responseDS.create(response);
		System.out.println("Response sync id is "+response_sync_id);
		
		Response response2 = responseDS.readByResponseSyncId(response_sync_id);		
		System.out.println("Retrieved response is "+response2.toString());
		*/
	
		
		/*
		DeviceDetailsDS mDeviceDetailsDS = new DeviceDetailsDS();
		
		DeviceDetails mDeviceDetails = new DeviceDetails();
		mDeviceDetails.setDeviceId("fsdjlslsds");
		mDeviceDetails.setLastTimeActivatedTimeStamp(new Date(System.currentTimeMillis()));
		mDeviceDetails.setDeviceUserName("Girish");
		mDeviceDetails.setDeviceUserContact("8285568996");
		mDeviceDetails.setDeviceUserCompany("Srijan");
		
		
		String deviceID = mDeviceDetailsDS.create(mDeviceDetails);
		
		System.out.println("Device id for inserted record is "+deviceID);
		
		DeviceDetails mDeviceDetails1 = mDeviceDetailsDS.getDeviceDetailsByDeviceId(deviceID);
		System.out.println("Device object is "+deviceID);
		
		
		
		*/
		
		
	}

}
