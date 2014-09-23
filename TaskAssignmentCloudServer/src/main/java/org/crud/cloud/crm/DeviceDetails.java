package org.crud.cloud.crm;

import java.io.Serializable;
import java.util.Date;

public class DeviceDetails implements Serializable{
	
	private static final long serialVersionUID = -13825574505549276L;
	
	private String deviceId;
	private Date lastTimeActivatedTimeStamp;
	private String deviceUserName;
	private String deviceUserContact;
	private String deviceUserCompany;
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public Date getLastTimeActivatedTimeStamp() {
		return lastTimeActivatedTimeStamp;
	}
	public void setLastTimeActivatedTimeStamp(Date lastTimeActivatedTimeStamp) {
		this.lastTimeActivatedTimeStamp = lastTimeActivatedTimeStamp;
	}
	public String getDeviceUserName() {
		return deviceUserName;
	}
	public void setDeviceUserName(String deviceUserName) {
		this.deviceUserName = deviceUserName;
	}
	public String getDeviceUserContact() {
		return deviceUserContact;
	}
	public void setDeviceUserContact(String deviceUserContact) {
		this.deviceUserContact = deviceUserContact;
	}
	public String getDeviceUserCompany() {
		return deviceUserCompany;
	}
	public void setDeviceUserCompany(String deviceUserCompany) {
		this.deviceUserCompany = deviceUserCompany;
	}
	
	
	

}
