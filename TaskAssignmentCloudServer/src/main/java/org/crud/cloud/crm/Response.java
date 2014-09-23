package org.crud.cloud.crm;

import java.io.Serializable;
import java.util.Date;

import org.openmobster.cloud.api.sync.MobileBean;
import org.openmobster.cloud.api.sync.MobileBeanId;

public class Response implements MobileBean, Serializable{
	
	private static final long serialVersionUID = -13825574505549275L;
	
	private long serverResponseId;
	private long mobileResponseId;
	
	@MobileBeanId
	private String responseSyncId;
	
	private long taskServerId;
	
	private String responseDesc;
	
	private String responseMobileTimeStamp;
	private Date responseServerTimeStamp;
	
	
	public long getServerResponseId() {
		return serverResponseId;
	}
	public void setServerResponseId(long serverResponseId) {
		this.serverResponseId = serverResponseId;
	}
	public long getMobileResponseId() {
		return mobileResponseId;
	}
	public void setMobileResponseId(long mobileResponseId) {
		this.mobileResponseId = mobileResponseId;
	}
	public String getResponseSyncId() {
		return responseSyncId;
	}
	public void setResponseSyncId(String responseSyncId) {
		this.responseSyncId = responseSyncId;
	}
	public long getTaskServerId() {
		return taskServerId;
	}
	public void setTaskServerId(long taskServerId) {
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
	public Date getResponseServerTimeStamp() {
		return responseServerTimeStamp;
	}
	public void setResponseServerTimeStamp(Date responseServerTimeStamp) {
		this.responseServerTimeStamp = responseServerTimeStamp;
	}
	
	
	
	
	
	

}
