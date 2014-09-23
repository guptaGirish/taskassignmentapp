package com.srijan.taskassignmentapp;

import com.srijan.taskassignmentapp.taskcreater.TaskCreatingActivity;
import com.srijan.taskassignmentapp.taskcreater.TaskListingActivity;
import com.srijan.taskassignmentapp.taskresponder.AssignedTaskListingActivity;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.widget.SimpleCursorAdapter.ViewBinder;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements View.OnClickListener {

	Button registerDevice, createTask, createdTaskListing, assignedTaskListing;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		initializeControls();
	}

	void initializeControls()
	{
		
		registerDevice = (Button)findViewById(R.id.registerDevice);
		createTask = (Button)findViewById(R.id.createTask);
		createdTaskListing = (Button)findViewById(R.id.createdTaskListing);
		assignedTaskListing = (Button)findViewById(R.id.assignedTaskListing);
		
		registerDevice.setOnClickListener(this);
		createTask.setOnClickListener(this);
		createdTaskListing.setOnClickListener(this);
		assignedTaskListing.setOnClickListener(this);
		
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent i = new Intent();
		
		switch (v.getId()) {
		case R.id.registerDevice:
		{
			i = new Intent(this,RegisterDeviceActivity.class);
		}
			break;
		case R.id.createTask:
		{
			i = new Intent(this,TaskCreatingActivity.class);
		}
			break;
		case R.id.createdTaskListing:
		{
			i = new Intent(this,TaskListingActivity.class);
		}
			break;
		case R.id.assignedTaskListing:
		{
			i = new Intent(this,AssignedTaskListingActivity.class);
		}
			break;
				
		default:
			break;
		}
		
		startActivity(i);
		
	}

}
