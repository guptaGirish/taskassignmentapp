package com.srijan.taskassignmentapp.baas;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

public class LoadCreatedTaskListAsyncTask extends AsyncTask<Void,Void,Void>{
	Context context;
	ProgressDialog dialog = null;
	Handler handler;
	Message message;
	
	
	public static String TASK_SERVER_ID_LIST = "TASK_SERVER_ID_LIST";
	public static String TASK_SYNC_ID_LIST = "TASK_SYNC_ID_LIST";
	public static String TASK_TITLE_LIST = "TASK_TITLE_LIST";
	public static String TASK_DESC_LIST = "TASK_DESC_LIST";
	public static String TASK_CREATED_TIMESTAMP_LIST = "TASK_CREATED_TIMESTAMP_LIST";
	public static String TASK_CREATER_DEVICE_ID_LIST = "TASK_CREATER_DEVICE_ID_LIST";
	public static String TASK_ASSIGNEE_DEVICE_ID_LIST = "TASK_ASSIGNEE_DEVICE_ID_LIST";
	
	
	public LoadCreatedTaskListAsyncTask(Context context,Handler handler){
		this.context=context;
		this.handler = handler;	
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
		Request request = new Request("/async/createdtasklist");
		
		try{
			message = handler.obtainMessage();
			Response response = new MobileService().invoke(request);			
			
			
			Vector taskServerIdList = response.getListAttribute(TASK_SERVER_ID_LIST);
			Vector taskSyncIdList = response.getListAttribute(TASK_SYNC_ID_LIST);
			Vector taskTitleList = response.getListAttribute(TASK_TITLE_LIST);
			Vector taskDescList = response.getListAttribute(TASK_DESC_LIST);
			Vector taskCreatedTimeStampList = response.getListAttribute(TASK_CREATED_TIMESTAMP_LIST);
			Vector taskCreaterDeviceIdList = response.getListAttribute(TASK_CREATER_DEVICE_ID_LIST);
			Vector taskAssigneeDeviceIdList = response.getListAttribute(TASK_ASSIGNEE_DEVICE_ID_LIST);
			
			
			Map <String,Vector>map=new HashMap<String,Vector>();
			try{				
				message.what = 1;
				map.put(TASK_SERVER_ID_LIST,taskServerIdList);
				map.put(TASK_SYNC_ID_LIST,taskSyncIdList);
				map.put(TASK_TITLE_LIST,taskTitleList);
				map.put(TASK_DESC_LIST,taskDescList);
				map.put(TASK_CREATED_TIMESTAMP_LIST,taskCreatedTimeStampList);
				map.put(TASK_CREATER_DEVICE_ID_LIST, taskCreaterDeviceIdList);
				map.put(TASK_CREATER_DEVICE_ID_LIST, taskCreaterDeviceIdList);
				map.put(TASK_ASSIGNEE_DEVICE_ID_LIST, taskAssigneeDeviceIdList);
				message.obj=map;				
			}catch(Exception ex){				
			}
		}catch(Exception ex){			
		}
		return null;
	}	
}
