package com.srijan.taskassignmentapp.baas;

import org.openmobster.android.api.sync.MobileBean;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.srijan.taskassignmentapp.TaskObject;
import com.srijan.taskassignmentapp.TaskResponseObject;

public class CreateResponseOverServerAsyncOM extends AsyncTask<Void,Void,Void>{
	Context context;
	ProgressDialog dialog = null;
	Handler handler;
	Message message;
	TaskResponseObject ro;
	MobileBean responseMobileBean;
	
	
	
	//public static String 
	public CreateResponseOverServerAsyncOM(Context context,Handler handler, TaskResponseObject ro){
		this.context=context;
		this.handler = handler;
		this.ro = ro;
		createMobileBean();
		
	}
	
	private void createMobileBean() {
		// TODO Auto-generated method stub
		responseMobileBean = MobileBean.newInstance("assignee_response_sync_channel");
		
		responseMobileBean.setValue(TaskResponseObject.RESPONSE_DESC, ro.getResponseDesc());
		responseMobileBean.setValue(TaskResponseObject.RESPONSE_MOBILE_TIMESTAMP, ""+System.currentTimeMillis());
		responseMobileBean.setValue(TaskResponseObject.TASK_SERVER_ID, ro.getTaskServerId());
		
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
			responseMobileBean.save();
			message.what = 1;
		}catch(Exception ex){
			
		}
		return null;
		
	}	
}