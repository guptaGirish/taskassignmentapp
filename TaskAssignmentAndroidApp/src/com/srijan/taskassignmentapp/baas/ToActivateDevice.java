package com.srijan.taskassignmentapp.baas;

import org.openmobster.android.api.rpc.MobileService;
import org.openmobster.android.api.rpc.Request;
import org.openmobster.android.api.rpc.Response;
import org.openmobster.core.mobileCloud.android_native.framework.CloudService;
import org.openmobster.core.mobileCloud.android_native.framework.ViewHelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.srijan.taskassignmentapp.utils.ActivationRequest;
import com.srijan.taskassignmentapp.utils.AppUtils;

public class ToActivateDevice extends AsyncTask<Void,Void,String>
{

	Context context;
	ProgressDialog dialog = null;
	Handler handler;
	Message message;
	Activity activity;
	ActivationRequest activationRequest;		
	String TAG = this.getClass().getName();
	public ToActivateDevice(Context context,Handler handler){
		Log.v(TAG, "In Constructor");
		
		this.context=context;
		this.handler = handler;
		this.activity = (Activity)context;
		this.activationRequest= activationRequest=new ActivationRequest("10.0.2.2",Integer.parseInt(""+1502) ,"abc@gmail.com","dfslkmfslm");
	}
	
	@Override
	protected void onPostExecute(String result)
	{
		Log.v(TAG,"In onPostExecute Mthod");
		dialog.dismiss();
		
		handler.sendMessage(message);
	}

	@Override
	protected void onPreExecute()
	{
		Log.v(TAG,"In onPreExecute Method");
		dialog = new ProgressDialog(context);		
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.show();	
	}
	
	@Override
	protected String doInBackground(Void... arg0){			 
		try
		{
			
			Log.v(TAG,"In doInBackground Method");
			//Start device activation
			Log.v(TAG,"Activation Request Details "+activationRequest.getServerIP()+activationRequest.getPortNo());
			
				message=handler.obtainMessage();
				
				CloudService.getInstance().activateDevice(activationRequest.getServerIP(),
						activationRequest.getPortNo(),activationRequest.getEmailId(),activationRequest.getPassword());
						
				Log.v(TAG,"Device Activated");
				
				//Start the local OpenMobster service after a successful activation
				CloudService.getInstance().start(activity);
				Log.v(TAG, "local openmobster engine started again after successful activation");
				
					message.what = 1;
				
					return null;
		}
		catch(Exception se)
		{
			se.printStackTrace();
			message.what = 0;
			return se.getMessage();
		}
	}		
}
