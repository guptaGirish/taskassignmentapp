package com.srijan.taskassignmentapp;

public class TaskResponseObject {
	
	public static String MOBILE_RESPONSE_ID = "mobileResponseId";
	public static String SERVER_RESPONSE_ID = "serverResponseId";
	public static String REPONSE_SYNC_ID = "responseSyncId";
	
	public static String TASK_SERVER_ID = "taskServerId";
	
	public static String RESPONSE_DESC = "responseDesc";
	public static String RESPONSE_MOBILE_TIMESTAMP = "responseMobileTimeStamp";
	public static String RESPONSE_SERVER_TIMESTAMP = "responseServerTimeStamp";
	
	String mobileResponseId, serverResponseId, responseSyncId, taskServerId, responseDesc, responseMobileTimeStamp, responseServerTimeStamp;

	public String getMobileResponseId() {
		return mobileResponseId;
	}

	public void setMobileResponseId(String mobileResponseId) {
		this.mobileResponseId = mobileResponseId;
	}

	public String getServerResponseId() {
		return serverResponseId;
	}

	public void setServerResponseId(String serverResponseId) {
		this.serverResponseId = serverResponseId;
	}

	public String getResponseSyncId() {
		return responseSyncId;
	}

	public void setResponseSyncId(String responseSyncId) {
		this.responseSyncId = responseSyncId;
	}

	public String getTaskServerId() {
		return taskServerId;
	}

	public void setTaskServerId(String taskServerId) {
		this.taskServerId = taskServerId;
	}

	public String getResponseDesc() {
		return responseDesc;
	}

	public void setResponseDesc(String responseDesc) {
		this.responseDesc = responseDesc;
	}

	public String getResponseMobileTimeStamp() {
		return responseMobileTimeStamp;
	}

	public void setResponseMobileTimeStamp(String responseMobileTimeStamp) {
		this.responseMobileTimeStamp = responseMobileTimeStamp;
	}

	public String getResponseServerTimeStamp() {
		return responseServerTimeStamp;
	}

	public void setResponseServerTimeStamp(String responseServerTimeStamp) {
		this.responseServerTimeStamp = responseServerTimeStamp;
	}
	
	
	
	
}
