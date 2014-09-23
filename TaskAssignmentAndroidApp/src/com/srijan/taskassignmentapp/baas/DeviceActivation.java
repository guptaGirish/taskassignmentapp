package com.srijan.taskassignmentapp.baas;

import org.openmobster.android.api.sync.MobileBean;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;


import com.srijan.taskassignmentapp.utils.AppUtils;

public class DeviceActivation {

	String TAG = this.getClass().getName();
	Handler handlerActivation;
	
	public void activateDevice(Activity activity)
	
	{
		initializeHandler();
		ToActivateDevice mToActivateDevice = new ToActivateDevice(activity, handlerActivation);
		mToActivateDevice.execute();
		
	}

	private void initializeHandler() {
		// TODO Auto-generated method stub
	
		handlerActivation = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				if(msg.what == 1)
				{
					Log.v(TAG, "Activation Successful");
					MobileBean.newInstance("task_creation_channel");
					MobileBean.newInstance("task_assignee_channel");
					MobileBean.newInstance("assignee_response_sync_channel");
					MobileBean.newInstance("creator_response_sync_channel");
					
				}
				else if(msg.what == 0)
				{
					Log.v(TAG, "Activation Exception occured");
				}
				
				
			}
			
		};
				
		
	}
}
