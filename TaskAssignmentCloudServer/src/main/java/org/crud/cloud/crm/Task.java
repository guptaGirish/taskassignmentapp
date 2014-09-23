package org.crud.cloud.crm;

import java.io.Serializable;
import java.util.Date;

import org.openmobster.cloud.api.sync.MobileBean;
import org.openmobster.cloud.api.sync.MobileBeanId;

public class Task implements MobileBean, Serializable{
	
	private static final long serialVersionUID = -13825574505549274L;
	
	private long serverTaskId;
	private long mobileTaskId;
	
	@MobileBeanId
	private String taskSyncId;
	
	private String assigneDeviceId;
	private String createrDeviceId;
	
	private String taskTitle;
	private String taskDesc;
	
	private String  taskMobileTimeStamp;
	private Date taskServerTimeStamp;
	
	//private String  taskMobileTimeStamp;
	//private String taskServerTimeStamp;
	
	
	private int taskStatus;
	//private String taskStatus;

	public long getServerTaskId() {
		
		return serverTaskId;
		
	}

	public void setServerTaskId(long serverTaskId) {
		this.serverTaskId = serverTaskId;
	}

	public long getMobileTaskId() {
		return mobileTaskId;
	}

	public void setMobileTaskId(long mobileTaskId) {
		this.mobileTaskId = mobileTaskId;
	}

	public String getTaskSyncId() {
		return taskSyncId;
	}

	public void setTaskSyncId(String taskSyncId) {
		this.taskSyncId = taskSyncId;
	}

	public String getAssigneDeviceId() {
		return assigneDeviceId;
	}

	public void setAssigneDeviceId(String assigneDeviceId) {
		this.assigneDeviceId = assigneDeviceId;
	}

	public String getCreaterDeviceId() {
		return createrDeviceId;
	}

	public void setCreaterDeviceId(String createrDeviceId) {
		this.createrDeviceId = createrDeviceId;
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

	public Date getTaskServerTimeStamp() {
		return taskServerTimeStamp;
	}

	public void setTaskServerTimeStamp(Date taskServerTimeStamp) {
		this.taskServerTimeStamp = taskServerTimeStamp;
	}

		public int getTaskStatus() {
		return taskStatus;
	}

	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}

	
	
	
	

	/*
	 * public int getTaskStatus() {
		return taskStatus;
	}
	 * public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}
	public void setTaskStatus(int taskStatus) {
		this.taskStatus = taskStatus;
	}
	public void setTaskStatus(String taskStatus) {
		this.taskStatus = Integer.parseInt(taskStatus) ;
	}
	*/
	
	

	
	
	
}
