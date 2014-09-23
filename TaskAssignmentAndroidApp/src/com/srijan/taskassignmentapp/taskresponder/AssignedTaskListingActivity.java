package com.srijan.taskassignmentapp.taskresponder;

import java.util.ArrayList;
import java.util.Map;
import java.util.Vector;

import org.openmobster.android.api.sync.MobileBean;
import org.openmobster.core.mobileCloud.android_native.framework.CloudService;
import org.openmobster.core.mobileCloud.android_native.framework.ViewHelper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.srijan.taskassignmentapp.R;
import com.srijan.taskassignmentapp.TaskObject;
import com.srijan.taskassignmentapp.baas.DeviceActivation;
import com.srijan.taskassignmentapp.baas.LoadAssignedTaskListAsyncTask;
import com.srijan.taskassignmentapp.baas.LoadCreatedTaskListAsyncTask;
import com.srijan.taskassignmentapp.listadapters.TaskListingAdapter;
import com.srijan.taskassignmentapp.taskcreater.FullTaskDetailActivity;
import com.srijan.taskassignmentapp.taskcreater.TaskListingActivity;





public class AssignedTaskListingActivity extends Activity{

	//public static MobileBean[] activeBeans;
	
	//public static TaskObject[] createdTask;
	ListView taskList;
	public static ArrayList<TaskObject> listTaskObjects;
	TaskListingAdapter mTaskListingAdapter; 
	
	String TAG = TaskListingActivity.class.getName();
	private boolean syncInProgress=false;
	private boolean syncComplete = false;
	
	Vector taskServerIdList;
	Vector taskSyncIdList;
	Vector taskTitleList;
	Vector taskDescList;
	Vector taskCreatedTimeStampList;
	Vector taskCreaterDeviceList;
	Vector taskAssigneeDeviceList;
	
	Activity activity = this;
	
	Handler handler;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Log.v(TAG, "In onCreate");
		
		setContentView(R.layout.activity_task_listing);
		taskList = (ListView)findViewById(R.id.task_list);
		
		taskList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> listView, View arg1, int position,
					long arg3) {
				// TODO Auto-generated method stub
				
				TaskObject to = (TaskObject)listView.getItemAtPosition(position);
				
				Intent i = new Intent(activity, AssignedTaskFullDetailsActivity.class);
				//i.putExtra(TaskObject.TASK_SYNC_ID, to.getTaskSyncId());
				//i.putExtra(TaskObject.SERVER_TASK_ID,to.getServerTaskId());
				i.putExtra("position",position);
				startActivity(i);
				
				
			}
			
		});
		
		
		
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
		//showTaskList();
		initializeHandler();
		LoadAssignedTaskListAsyncTask l = new LoadAssignedTaskListAsyncTask(this, handler);
		l.execute();
	}

	
	
	void initializeHandler()
	{
		handler = new Handler(){

			@Override
			public void handleMessage(Message msg) {
				// TODO Auto-generated method stub
				super.handleMessage(msg);
				
				if(msg.what == 1)
				{
					
					Map map=(Map) msg.obj;
        			taskServerIdList= (Vector) map.get(LoadCreatedTaskListAsyncTask.TASK_SERVER_ID_LIST);
        			taskSyncIdList= (Vector) map.get(LoadCreatedTaskListAsyncTask.TASK_SYNC_ID_LIST);
        			taskTitleList= (Vector) map.get(LoadCreatedTaskListAsyncTask.TASK_TITLE_LIST);
        			taskDescList= (Vector) map.get(LoadCreatedTaskListAsyncTask.TASK_DESC_LIST);
        			taskCreatedTimeStampList= (Vector) map.get(LoadCreatedTaskListAsyncTask.TASK_CREATED_TIMESTAMP_LIST);
        			taskCreaterDeviceList = (Vector) map.get(LoadCreatedTaskListAsyncTask.TASK_CREATER_DEVICE_ID_LIST);
        			taskAssigneeDeviceList = (Vector) map.get(LoadCreatedTaskListAsyncTask.TASK_ASSIGNEE_DEVICE_ID_LIST);
        			
        			
        			listTaskObjects = new ArrayList<TaskObject>();
        			
        			int size = taskServerIdList.size();
        			
        			for(int i=0; i<size; i++)
        			{
        				TaskObject to = new TaskObject();
        				to.setServerTaskId(""+taskServerIdList.get(i));
        				to.setTaskSyncId(""+taskSyncIdList.get(i));
        				to.setTaskTitle(""+taskTitleList.get(i));
        				to.setTaskDesc(""+taskDescList.get(i));
        				to.setTaskServerTimeStamp(""+taskCreatedTimeStampList.get(i));
        				
        				if(taskCreaterDeviceList.get(i) != null)
        				to.setTaskCreaterDeviceId(""+taskCreaterDeviceList.get(i));
        				
        				if(taskAssigneeDeviceList.get(i) != null)
        					to.setTaskAssigneeDeviceId(""+taskAssigneeDeviceList.get(i));
        				listTaskObjects.add(to);
        			}
        			
        			
        			mTaskListingAdapter = new TaskListingAdapter(activity, 1, listTaskObjects);
					taskList.setAdapter(mTaskListingAdapter);
        
					
					
				}
				
			}
			
			
		};
		
		
		
		
	}
	
	
	/*
	
	
	
	public void showTaskList()
	{
		Log.v(TAG, "In showTaskList");
		//Read all the CRM Ticket instances synced locally with the device
		
			try{
			
				if(MobileBean.isBooted("task_creation_channel"))
				{
					Log.v(TAG, "task_creation_channel is booted");
					activeBeans = MobileBean.readAll("task_creation_channel");
					
					listTaskObjects = new ArrayList<TaskObject>();
					
					for(int i=0; i<activeBeans.length; i++)
					{
						
						MobileBean local = activeBeans[i];
						
						
						Log.v(TAG,"Task Server id1 "+local.getServerId());
						Log.v(TAG,"Task Server id2 "+local.getId());
						TaskObject to = new TaskObject();
						//to.setDeviceId(local.getValue(TaskObject.))
						to.setServerTaskId(local.getValue(TaskObject.SERVER_TASK_ID));
						Log.v(TAG,"Task Server id "+local.getValue(TaskObject.SERVER_TASK_ID));
						
						to.setMobileTaskId(local.getValue(TaskObject.MOBILE_TASK_ID));
						Log.v(TAG,"Task mobile id "+local.getValue(TaskObject.MOBILE_TASK_ID));
						to.setTaskSyncId(local.getValue(TaskObject.TASK_SYNC_ID));
						Log.v(TAG,"Task Sync id "+local.getValue(TaskObject.TASK_SYNC_ID));
						to.setTaskAssigneeDeviceId(local.getValue(TaskObject.TASK_ASSIGNEE_DEVICE_ID));
						to.setTaskCreaterDeviceId(local.getValue(TaskObject.TASK_CREATOR_DEVICE_ID));
						Log.v(TAG,"Task creater device id "+local.getValue(TaskObject.TASK_CREATOR_DEVICE_ID));
						to.setTaskTitle(local.getValue(TaskObject.TASK_TITLE));
						Log.v(TAG,"Task Title id "+local.getValue(TaskObject.TASK_TITLE));
						
						to.setTaskStatus(local.getValue(TaskObject.TASK_STATUS));
						to.setTaskDesc(local.getValue(TaskObject.TASK_DESC));
						to.setTaskMobileTimeStamp(local.getValue(TaskObject.TASK_MOBILE_TIMESTAMP));
						to.setTaskServerTimeStamp(local.getValue(TaskObject.TASK_SERVER_TIMESTAMP));
						
						Log.v(TAG, to.toString());
						listTaskObjects.add(to);
						
					}
					
					mTaskListingAdapter = new TaskListingAdapter(this, 1, listTaskObjects);
					taskList.setAdapter(mTaskListingAdapter);
					
				}
				else
				{
					Log.v(TAG, "channel is not booted.");
					//Tickets not found...put up a Sync in progress message and wait for data to be downloaded 
					//from the Backend
					//if(!TaskListingActivity.syncInProgress && !TaskListingActivity.syncComplete)
					if(!syncInProgress && !syncComplete)
					{
						//TaskListingActivity.syncInProgress = true;

						syncInProgress = true;
						SyncInProgressAsyncTask task = new SyncInProgressAsyncTask();
						task.execute();
					}
				}
			}
			catch(Exception e)
			{
				Log.v(TAG, "Exception Raised is "+e.getMessage());
			}
				
				
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		
			
	}

	
	
	private class SyncInProgressAsyncTask extends AsyncTask<Void,Void,String>
	{
		private ProgressDialog dialog = null;
		
		private SyncInProgressAsyncTask()
		{
			
		}
		
		@Override
		protected void onPreExecute()
		{
			dialog = new ProgressDialog(TaskListingActivity.this);		
			dialog.setMessage("Sync in Progress....");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected String doInBackground(Void... arg0)
		{
			try
			{
				//Check if the CRM Ticket channel has data to be read
				boolean isBooted = MobileBean.isBooted("task_creation_channel");
				int counter = 20;
				while(!isBooted)
				{
					Thread.sleep(2000);
					
					if(counter > 0)
					{
						isBooted = MobileBean.isBooted("task_creation_channel");
						counter--;
					}
					else
					{
						break;
					}
				}
				
				return ""+isBooted;
			}
			catch(Exception e)
			{
				return "failure";
			}
		}
		
		@Override
		protected void onPostExecute(String result)
		{
			this.dialog.dismiss();
			
			if(result.equals(Boolean.TRUE.toString()) || result.equals(Boolean.FALSE.toString()))
			{
				//TaskListingActivity.syncInProgress = false;
				//TaskListingActivity.syncComplete = true;
				syncInProgress = false;
				syncComplete = true;
				
				showTaskList();
			}
			else
			{
				final AlertDialog dialog = ViewHelper.getOkModalWithCloseApp(TaskListingActivity.this, "Sync Failure", "Data Sync Failed. Please restart the App");
				dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
						new DialogInterface.OnClickListener() {

							public void onClick(DialogInterface dialog, int status)
							{
								dialog.dismiss();
								//TaskListingActivity.syncInProgress = false;
								//TaskListingActivity.syncComplete = false;
								syncInProgress = false;
								syncComplete = false;
								
								
								TaskListingActivity.this.finish();
								
							}
				});
				dialog.show();
			}
		}
	}
	
	*/
}
