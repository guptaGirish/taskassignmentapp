package com.srijan.taskassignmentapp;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.openmobster.android.api.rpc.MobileService;
import org.openmobster.android.api.rpc.Request;
import org.openmobster.android.api.rpc.Response;
import org.openmobster.android.api.sync.MobileBean;
import org.openmobster.core.mobileCloud.android_native.framework.CloudService;
import org.openmobster.core.mobileCloud.android_native.framework.ViewHelper;

import com.srijan.taskassignmentapp.baas.DeviceActivation;
import com.srijan.taskassignmentapp.utils.ActivationRequest;
import com.srijan.taskassignmentapp.utils.AppUtils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterDeviceActivity extends Activity {
	
	EditText userName, userContact, userCompany;
	Button registerDetails;
	String TAG = RegisterDeviceActivity.class.getName();
	Activity activity;
	Handler handlerRegisterDetails;
	SharedPreferences mSharedPreferences; 

	
	
	
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.v(TAG, "In onStart");
		CloudService.getInstance().start(this);
	}
	

	@Override
	protected void onResume()
	{
		super.onResume();
		Log.v(TAG, "In onResume");
		boolean isDeviceActivated = CloudService.getInstance().isDeviceActivated();
		if(!isDeviceActivated)
		{
			Log.v(TAG,"Device is not activated");
			DeviceActivation d = new DeviceActivation();
			d.activateDevice(this);
		}
		else
		{
			Log.v(TAG,"Device is already activated");
		}
			
		try{
			
			
		}
		catch(Exception e)
		{
			
		}
		
		MobileBean.newInstance("task_creation_channel");
		MobileBean.newInstance("task_assignee_channel");
		MobileBean.newInstance("assignee_response_sync_channel");
		MobileBean.newInstance("creator_response_sync_channel");
		CloudService.getInstance().start(this);
		
		postCheckoutProcessing();
	}
	
	
	public void postCheckoutProcessing()
	{
		
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register_device_details);
		activity = (Activity)this;
		mSharedPreferences = getSharedPreferences(AppUtils.APP_PREFERENCES, Context.MODE_PRIVATE);
		
		
		
		initializeHandlerRegisterDetails();
		initializeControls();
		
		
	}
	void initializeControls()
	{
		userName = (EditText)findViewById(R.id.user_name);
		userContact = (EditText)findViewById(R.id.user_contact);
		userCompany = (EditText)findViewById(R.id.user_company);
		registerDetails = (Button)findViewById(R.id.register_details);
		
		registerDetails.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				boolean isDeviceActivated = CloudService.getInstance().isDeviceActivated();
				if(isDeviceActivated)
				{
					ToRegisterDevice mToActivateDevice = new ToRegisterDevice(activity, handlerRegisterDetails);
					mToActivateDevice.execute();
				}
			}
		});
		
	}
	
	

	private class ToRegisterDevice extends AsyncTask<Void,Void,String>
	{

		Context context;
		ProgressDialog dialog = null;
		Handler handler;
		Message message;
		
		String TAG = this.getClass().getName();
		public ToRegisterDevice(Context context,Handler handler){
			Log.v(TAG,"In constructor");
			this.context=context;
			this.handler = handler;	
			
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			Log.v(TAG,"In onPostExecute");
			dialog.dismiss();
				
			handler.sendMessage(message);
		
		}

		@Override
		protected void onPreExecute()
		{
			Log.v(TAG,"In onPreExecute");
			dialog = new ProgressDialog(context);		
			dialog.setMessage("Please wait...");
			dialog.setCancelable(false);
			dialog.show();	
		}
		
		@Override
		protected String doInBackground(Void... arg0){			 
			try
			{
				
				Log.v(TAG,"In doInBackground");
				
				CloudService.getInstance().start(activity);
				
				message=handler.obtainMessage();
				
				Request request = new Request(AppUtils.REGISTER_DEVICE_OVER_BAAS_URI);
				request.setAttribute(AppUtils.DEVICE_USER_NAME_OVER_BAAS, userName.getText().toString());
				request.setAttribute(AppUtils.DEVICE_USER_CONTACT_OVER_BAAS, userContact.getText().toString());
				request.setAttribute(AppUtils.DEVICE_USER_COMPANY_OVER_BAAS, userCompany.getText().toString());

				
				Response response = MobileService.invoke(request);			
					
				String deviceId = response.getAttribute(AppUtils.DEVICE_ID_OVER_BAAS);
				
				message = handler.obtainMessage();
				
				Bundle b = new Bundle();
				b.putString(AppUtils.DEVICE_ID_OVER_BAAS, deviceId);
					
				message.setData(b);
			
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

	void initializeHandlerRegisterDetails()
	{
		
		handlerRegisterDetails = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				if(msg.what == 1)
				{
					Log.v(TAG, "Registration Successful");
					Bundle b = msg.getData();
					AppUtils.deviceId = b.getString(AppUtils.DEVICE_ID_OVER_BAAS);
					Log.v(TAG,"Device id retrieved is "+ AppUtils.deviceId);
					
					
					
					
					
					if(AppUtils.deviceId.equals("available"))
						Log.v(TAG, "Device already registered in server db");
					
					if(AppUtils.deviceId == null || AppUtils.deviceId.equals(""))
						Log.v(TAG, "Device Not registered successfully with server");
					
					Editor e = mSharedPreferences.edit();
					e.putString(AppUtils.DEVICE_ID_OVER_BAAS, AppUtils.deviceId);
					e.commit();
					
					AppUtils.deviceId = mSharedPreferences.getString(AppUtils.DEVICE_ID_OVER_BAAS, "");
					
				}
				else if(msg.what == 0)
				{
					Log.v(TAG, "Exception occured while registering device details");
				}
				
				
			}
			
		};
				
		
	}
	
}
