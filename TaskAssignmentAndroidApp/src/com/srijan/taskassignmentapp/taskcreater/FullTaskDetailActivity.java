package com.srijan.taskassignmentapp.taskcreater;


import org.openmobster.android.api.sync.MobileBean;
import org.openmobster.core.mobileCloud.android_native.framework.CloudService;

import com.srijan.taskassignmentapp.R;
import com.srijan.taskassignmentapp.TaskObject;
import com.srijan.taskassignmentapp.TaskResponseObject;
import com.srijan.taskassignmentapp.baas.DeviceActivation;
import com.srijan.taskassignmentapp.baas.RetrieveResponseOfTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.EditText;

public class FullTaskDetailActivity extends Activity {

	EditText taskTitle, taskDesc, taskCreater, response, responder;
	String TAG = this.getClass().getName();
	
	
	Handler handlerResponse;
	TaskObject to;
	TaskResponseObject ro;
	
	//Resp
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_task_detail);
		initializeControls();
		
		
		
	}
	
	

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
			
		MobileBean.newInstance("task_creation_channel");
		MobileBean.newInstance("task_assignee_channel");
		MobileBean.newInstance("assignee_response_sync_channel");
		MobileBean.newInstance("creator_response_sync_channel");
		CloudService.getInstance().start(this);
		
		postCheckoutProcessing();
		
	}
	
	public void postCheckoutProcessing()
	{
	
		initializeHandler();
		
		Intent i = getIntent();
		int taskItemPostion = 0;
		if(i != null)
			taskItemPostion = i.getIntExtra("position", 0);
		
		
		
		to = TaskListingActivity.listTaskObjects.get(taskItemPostion);
		
		
		taskTitle.setText(to.getTaskTitle());
		taskDesc.setText(to.getTaskDesc());
		taskCreater.setText("Me");
		
		
		/*
		MobileBean local = TaskListingActivity.activeBeans[taskItemPostion];
		to = new TaskObject();
		//to.setDeviceId(local.getValue(TaskObject.))
		to.setServerTaskId(local.getValue(TaskObject.SERVER_TASK_ID));
		to.setMobileTaskId(local.getValue(TaskObject.MOBILE_TASK_ID));
		to.setTaskSyncId(local.getValue(TaskObject.TASK_SYNC_ID));
		
		to.setTaskAssigneeDeviceId(local.getValue(TaskObject.TASK_ASSIGNEE_DEVICE_ID));
		to.setTaskCreaterDeviceId(local.getValue(TaskObject.TASK_CREATOR_DEVICE_ID));
		
		to.setTaskTitle(local.getValue(TaskObject.TASK_TITLE));
		to.setTaskStatus(local.getValue(TaskObject.TASK_STATUS));
		to.setTaskDesc(local.getValue(TaskObject.TASK_DESC));
		to.setTaskMobileTimeStamp(local.getValue(TaskObject.TASK_MOBILE_TIMESTAMP));
		to.setTaskServerTimeStamp(local.getValue(TaskObject.TASK_SERVER_TIMESTAMP));
		*/
		//if(to.getTaskStatus().equals("1"))
		//{
			RetrieveResponseOfTask rro = new RetrieveResponseOfTask(this, handlerResponse);
			
			Log.v(TAG, "Task server id is "+to.getServerTaskId());
			rro.setTaskServerId(Long.parseLong(to.getServerTaskId()));
			rro.execute();
		//}
		
		
		
		
	}
	
	void initializeControls()
	{
		
		
		taskTitle = (EditText)findViewById(R.id.taskTitle);
		taskDesc = (EditText)findViewById(R.id.taskDescription);
		taskCreater = (EditText)findViewById(R.id.creator);
		response = (EditText)findViewById(R.id.reponse);
		responder = (EditText)findViewById(R.id.responder);
	}

	
	
	
	void initializeHandler()
	{
		
		
		String MOBILE_RESPONSE_ID = "mobileResponseId";
		String SERVER_RESPONSE_ID = "serverResponseId";
		String REPONSE_SYNC_ID = "responseSyncId";
		
		String TASK_SERVER_ID = "taskServerId";
		
		final String RESPONSE_DESC = "responseDesc";
		String RESPONSE_MOBILE_TIMESTAMP = "responseMobileTimeStamp";
		String RESPONSE_SERVER_TIMESTAMP = "responseServerTimeStamp";
		
	
		handlerResponse = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
		//		super.handleMessage(msg);
				Bundle b = msg.getData();
				
				
				String responseDsc = b.getString(RESPONSE_DESC);
				Log.v(TAG, "Response Desc is"+responseDsc);
				response.setText(responseDsc);
				
				
			}
			
		};
		
		
	}
	
}
