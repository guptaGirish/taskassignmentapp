package com.srijan.taskassignmentapp;

import java.util.Date;

import android.R.bool;

public class TaskObject {
	
	public static String MOBILE_TASK_ID = "mobileTaskId";  // id for app side
	public static String SERVER_TASK_ID = "serverTaskId";  // id for server side
	public static String TASK_SYNC_ID = "taskSyncId";  // id for sync + push engine
	
	public static String TASK_ASSIGNEE_DEVICE_ID = "assigneDeviceId";
	public static String TASK_CREATOR_DEVICE_ID = "createrDeviceId";
	
	public static String TASK_TITLE = "taskTitle";
	public static String TASK_DESC = "taskDesc";
	
	public static String TASK_MOBILE_TIMESTAMP = "taskMobileTimeStamp";
	public static String TASK_SERVER_TIMESTAMP = "taskServerTimeStamp";
	
	public static String TASK_STATUS = "taskStatus";
	
	String mobileTaskId, serverTaskId, taskSyncId, taskAssigneeDeviceId, taskCreaterDeviceId, taskTitle, taskDesc, taskStatus, taskMobileTimeStamp, taskServerTimeStamp ;
	
	
	public String getMobileTaskId() {
		return mobileTaskId;
	}

	public void setMobileTaskId(String mobileTaskId) {
		this.mobileTaskId = mobileTaskId;
	}

	public String getServerTaskId() {
		return serverTaskId;
	}

	public void setServerTaskId(String serverTaskId) {
		this.serverTaskId = serverTaskId;
	}

	public String getTaskSyncId() {
		return taskSyncId;
	}

	public void setTaskSyncId(String taskSyncId) {
		this.taskSyncId = taskSyncId;
	}

	public String getTaskAssigneeDeviceId() {
		return taskAssigneeDeviceId;
	}

	public void setTaskAssigneeDeviceId(String taskAssigneeDeviceId) {
		this.taskAssigneeDeviceId = taskAssigneeDeviceId;
	}

	public String getTaskCreaterDeviceId() {
		return taskCreaterDeviceId;
	}

	public void setTaskCreaterDeviceId(String taskCreaterDeviceId) {
		this.taskCreaterDeviceId = taskCreaterDeviceId;
	}

	public String getTaskTitle() {
		return taskTitle;
	}

	public void setTaskTitle(String taskTitle) {
		this.taskTitle = taskTitle;
	}

	public String getTaskDesc() {
		return taskDesc;
	}

	public void setTaskDesc(String taskDesc) {
		this.taskDesc = taskDesc;
	}

	

	

	

	public String getTaskMobileTimeStamp() {
		return taskMobileTimeStamp;
	}

	public void setTaskMobileTimeStamp(String taskMobileTimeStamp) {
		this.taskMobileTimeStamp = taskMobileTimeStamp;
	}

	public String getTaskServerTimeStamp() {
		return taskServerTimeStamp;
	}

	public void setTaskServerTimeStamp(String taskServerTimeStamp) {
		this.taskServerTimeStamp = taskServerTimeStamp;
	}

	public String getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(String taskStatus) {
		this.taskStatus = taskStatus;
	}
	
	

}
