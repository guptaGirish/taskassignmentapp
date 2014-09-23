package com.srijan.taskassignmentapp.baas;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.openmobster.android.api.rpc.MobileService;
import org.openmobster.android.api.rpc.Request;
import org.openmobster.android.api.rpc.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class RegisterDeviceDetailsAsyncTask  extends AsyncTask<Void, Void, Void> {

	String TAG = RegisterDeviceDetailsAsyncTask.class.getName();
	
	public static String DEVICE_ID = "deviceIds";
	public static String DEVICE_LASTTIMEACTIVATED_TIMESTAMP = "lastTimeActivatedTimeStamp";
	public static String DEVICE_USER_NAME = "deviceUserName";
	public static String DEVICE_USER_CONTACT = "deviceUserContact";
	public static String DEVICE_USER_COMPANY = "deviceUserCompany";
	
	ProgressDialog dialog = null;
	
	Context context;
	Handler handler;
	Message message;
	
	public RegisterDeviceDetailsAsyncTask(Context context,Handler handler){
		this.context=context;
		this.handler = handler;	
	}
	
	
	
	
	
	@Override
	protected void onPostExecute(Void result) {
		// TODO Auto-generated method stub
		dialog.dismiss();
		handler.sendMessage(message);
	}





	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		dialog = new ProgressDialog(context);		
		dialog.setMessage("Please wait...");
		dialog.setCancelable(false);
		dialog.show();
		
	}





	@Override
	protected Void doInBackground(Void... params) {
				// TODO Auto-generated method stub
		Request request = new Request("/async/registerdevice");
		request.setAttribute(DEVICE_USER_NAME, "");
		request.setAttribute(DEVICE_USER_CONTACT, "");
		request.setAttribute(DEVICE_USER_COMPANY, "");
		//request.setAttribute(DEVICE_, "");

		
		try{
			Response response = new MobileService().invoke(request);			
			
			String deviceId = response.getAttribute(DEVICE_ID);
			Log.v(TAG, "Device id over server is -"+deviceId);
			
			message = handler.obtainMessage();
			try{				
				message.what = 1;
			}catch(Exception ex){				
			}
		}catch(Exception ex){			
		}
		return null;
	}

}
