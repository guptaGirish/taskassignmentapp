package com.srijan.taskassignmentapp.taskcreater;

import java.util.Map;
import java.util.Vector;

import org.openmobster.android.api.sync.MobileBean;
import org.openmobster.core.mobileCloud.android_native.framework.CloudService;

import com.srijan.taskassignmentapp.R;
import com.srijan.taskassignmentapp.TaskObject;
import com.srijan.taskassignmentapp.baas.CreateTaskOverServerAsyncOM;
import com.srijan.taskassignmentapp.baas.DeviceActivation;
import com.srijan.taskassignmentapp.baas.LoadDeviceListAysncOM;
import com.srijan.taskassignmentapp.utils.AppUtils;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class TaskCreatingActivity extends Activity{

	EditText taskTitle, taskDescription;//, taskCreater ;
	Spinner selectAssignee;
	Button assignTask;
	Handler handlerDeviceList, handlerAssignTask;
	TaskObject to;
	SharedPreferences mSharedPreferences;
	Vector assigneeDeviceIds;
	Vector assignees;
	String TAG = TaskCreatingActivity.class.getName();
	
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
		
		LoadDeviceListAysncOM mAsyncLoadSpinners = new LoadDeviceListAysncOM(this, handlerDeviceList);
		mAsyncLoadSpinners.execute();
		
		
	}

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		mSharedPreferences = getSharedPreferences(AppUtils.APP_PREFERENCES, Context.MODE_PRIVATE);
		
		setContentView(R.layout.activity_task_create);
		to = new TaskObject();
		initializeControls();
		initializeHandlers();
		
		
		
	}

	private void initializeHandlers() {
		// TODO Auto-generated method stub
		
		handlerDeviceList = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				if(msg.what == 1)
				{
					
					Map map=(Map) msg.obj;
        			assigneeDeviceIds= (Vector) map.get(LoadDeviceListAysncOM.DEVICE_IDS);
        			assignees=(Vector) map.get(LoadDeviceListAysncOM.ASSIGNEES);
        			
        			String selectedCustomer = "";
        			String selectedSpecialist = "";
        			//Load the specialist customers
        			ArrayAdapter<CharSequence> selectAssigneAdapter = new ArrayAdapter<CharSequence>(TaskCreatingActivity.this, android.R.layout.simple_spinner_item);
        			int selectedCustomerPosition = -1;
        			selectAssigneAdapter.add("--Select Assignee--");
        			int size = assignees.size();
        			for(int i=0; i<size; i++)
        			{
        				String local = (String)assignees.get(i);
        				Log.v(TAG,"Assignee-"+i+" is "+local+" and its device id is-"+assigneeDeviceIds.get(i));
        				selectAssigneAdapter.add(local);
        				if(local.equals(selectedCustomer))
        				{
        					selectedCustomerPosition = i;
        				}
        			}
        			
        			selectAssigneAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        			selectAssignee.setAdapter(selectAssigneAdapter);
        			selectAssignee.setSelection(selectedCustomerPosition+1, true);
        			assignTask.setEnabled(true);
					
				}
				
				
			}
			
			
		};
		
		handlerAssignTask = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				if(msg.what == 1)
				{
					Toast.makeText(TaskCreatingActivity.this,"Record successfully saved",1).show();
				}
				
			}
			
			
		};
		
		
	}

	private void initializeControls() {
		// TODO Auto-generated method stub
		
		taskTitle = (EditText)findViewById(R.id.task_title);
		taskDescription = (EditText)findViewById(R.id.task_description);
	//	taskCreater = (EditText)findViewById(R.id.task_creater);
		selectAssignee = (Spinner)findViewById(R.id.select_assignee);
		assignTask = (Button)findViewById(R.id.assign_task);
		
		assignTask.setEnabled(false);
		
		
		
		assignTask.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
		
				to.setTaskTitle(taskTitle.getText().toString());
				to.setTaskDesc(taskDescription.getText().toString());
				//to.setTask("admin");//taskCreater.getText().toString());
				
				Log.v(TAG,"Task Creater device id is "+mSharedPreferences.getString(AppUtils.DEVICE_ID_OVER_BAAS, ""));
				to.setTaskCreaterDeviceId(mSharedPreferences.getString(AppUtils.DEVICE_ID_OVER_BAAS, ""));
				
				to.setTaskStatus("0");
				to.setTaskMobileTimeStamp(""+System.currentTimeMillis());
				
				Log.v(TAG, "Selected assignee position is -"+(selectAssignee.getSelectedItemPosition()-1));
				Log.v(TAG, "Selected assignee is -"+ (String)assignees.get(selectAssignee.getSelectedItemPosition()-1) );
				to.setTaskAssigneeDeviceId((String)assigneeDeviceIds.get(selectAssignee.getSelectedItemPosition()-1));
				
				CreateTaskOverServerAsyncOM mCreateTaskOverServerAsyncOM = new CreateTaskOverServerAsyncOM(TaskCreatingActivity.this, handlerAssignTask, to);
				mCreateTaskOverServerAsyncOM.execute();
			}
		});
		
	}
	
	

}
