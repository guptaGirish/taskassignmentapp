package com.srijan.taskassignmentapp.baas;

import org.openmobster.android.api.rpc.MobileService;
import org.openmobster.android.api.rpc.Request;
import org.openmobster.android.api.rpc.Response;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class RetrieveResponseOfTask extends AsyncTask<Void, Void, Void> {

	String TAG = RegisterDeviceDetailsAsyncTask.class.getName();
	
	String MOBILE_RESPONSE_ID = "mobileResponseId";
	String SERVER_RESPONSE_ID = "serverResponseId";
	String REPONSE_SYNC_ID = "responseSyncId";
	
	String TASK_SERVER_ID = "taskServerId";
	
	String RESPONSE_DESC = "responseDesc";
	String RESPONSE_MOBILE_TIMESTAMP = "responseMobileTimeStamp";
	String RESPONSE_SERVER_TIMESTAMP = "responseServerTimeStamp";
	
	long taskServerId = -1;
	
	ProgressDialog dialog = null;
	
	Context context;
	Handler handler;
	Message message;
	
	public RetrieveResponseOfTask(Context context,Handler handler){
		this.context=context;
		this.handler = handler;	
	}
	
	
	
	
	
	public long getTaskServerId() {
		return taskServerId;
	}





	public void setTaskServerId(long taskServerId) {
		this.taskServerId = taskServerId;
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
		
		if(taskServerId != -1)
		{
		
				Request request = new Request("/async/getfullresponse");
				request.setAttribute(TASK_SERVER_ID,""+taskServerId);
				
				
				try{
					Response response = new MobileService().invoke(request);			
					
					Bundle b = new Bundle();
					b.putString(REPONSE_SYNC_ID, response.getAttribute(REPONSE_SYNC_ID));
					b.putString(RESPONSE_DESC,response.getAttribute(RESPONSE_DESC));
					b.putString(RESPONSE_SERVER_TIMESTAMP, response.getAttribute(RESPONSE_SERVER_TIMESTAMP));
					
					
					
					Log.v(TAG, "Response is -"+response.getAttribute(RESPONSE_DESC));
					
					message = handler.obtainMessage();
					try{				
						message.what = 1;
						message.setData(b);
					}catch(Exception ex){				
					}
				}catch(Exception ex){			
				}
		}
		return null;
	}

}
