
package com.srijan.taskassignmentapp.baas;

/**
 * Copyright (c) {2003,2011} {openmobster@gmail.com} {individual contributors as indicated by the @authors tag}.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import org.openmobster.android.api.rpc.MobileService;
import org.openmobster.android.api.rpc.Request;
import org.openmobster.android.api.rpc.Response;
import org.openmobster.android.api.sync.MobileBean;

import com.srijan.taskassignmentapp.TaskObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author openmobster@gmail.com
 */

public class CreateTaskOverServerAsyncOM extends AsyncTask<Void,Void,Void>{
	Context context;
	ProgressDialog dialog = null;
	Handler handler;
	Message message;
	TaskObject to;
	MobileBean taskMobileBean;
	
	
	
	//public static String 
	public CreateTaskOverServerAsyncOM(Context context,Handler handler, TaskObject to){
		this.context=context;
		this.handler = handler;
		this.to = to;
		createMobileBean();
		
	}
	
	private void createMobileBean() {
		// TODO Auto-generated method stub
		taskMobileBean = MobileBean.newInstance("task_creation_channel");
		
		taskMobileBean.setValue(TaskObject.TASK_TITLE, to.getTaskTitle());
		taskMobileBean.setValue(TaskObject.TASK_ASSIGNEE_DEVICE_ID, to.getTaskAssigneeDeviceId());
		taskMobileBean.setValue(TaskObject.TASK_DESC, to.getTaskDesc());
		taskMobileBean.setValue(TaskObject.TASK_CREATOR_DEVICE_ID, to.getTaskCreaterDeviceId());
		taskMobileBean.setValue(TaskObject.TASK_MOBILE_TIMESTAMP, ""+to.getTaskMobileTimeStamp());
		taskMobileBean.setValue(TaskObject.TASK_STATUS, to.getTaskStatus() );
	}

	@Override
	protected void onPreExecute()
	{
		dialog = new ProgressDialog(context);		
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.show();
	}

	@Override
	protected void onPostExecute(Void result)
	{
		dialog.dismiss();
		handler.sendMessage(message);
	}

	@Override
	protected Void doInBackground(Void... arg0)
	{
				
		try{
			message = handler.obtainMessage();
			taskMobileBean.save();
			message.what = 1;
		}catch(Exception ex){
			
		}
		return null;
		
	}	
}