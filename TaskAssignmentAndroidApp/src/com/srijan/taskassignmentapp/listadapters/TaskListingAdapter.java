package com.srijan.taskassignmentapp.listadapters;

import java.util.ArrayList;
import java.util.List;



import com.srijan.taskassignmentapp.R;
import com.srijan.taskassignmentapp.TaskObject;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TaskListingAdapter extends ArrayAdapter<TaskObject> {

	String TAG = this.getClass().getName();
	Context context;
	static int rowResourceId = R.layout.row_task_item;
	ArrayList<TaskObject> taskList;
	
	public TaskListingAdapter(Context context, int resource, ArrayList<TaskObject> objects) {
		super(context, rowResourceId, objects);
		// TODO Auto-generated constructor stub
		this.context = context;
		//this.rowResourceId = resource;
		this.taskList =  objects;
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		//return super.getView(position, convertView, parent);
		Log.v(TAG,"In getView");
		TaskObjectHolder mHolder = null;
		String responseImagePath;
        if (convertView == null) {
        	
        	Log.v(TAG,"Convert view is null and position of list item is "+position);
            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(rowResourceId, parent, false);
            mHolder = new TaskObjectHolder();
            mHolder.taskSyncId = (TextView)convertView.findViewById(R.id.id_sync_task);
            mHolder.taskServerId = (TextView)convertView.findViewById(R.id.id_server_task);
            mHolder.taskTitle = (TextView)convertView.findViewById(R.id.taskTitle);
            mHolder.taskDesc = (TextView)convertView.findViewById(R.id.taskDesc);
            mHolder.date= (TextView)convertView.findViewById(R.id.date);
            mHolder.creator = (TextView)convertView.findViewById(R.id.creator);
            
            
            convertView.setTag(mHolder);
        } else {
        	Log.v(TAG,"Convert view is not null and position of list item is "+position);
        	mHolder = (TaskObjectHolder) convertView.getTag();
        	
        }
        
        
        TaskObject to = taskList.get(position);
        
        mHolder.taskSyncId.setText(to.getTaskSyncId());
        mHolder.taskServerId.setText(to.getServerTaskId());
        mHolder.taskTitle.setText(to.getTaskTitle());
        mHolder.taskDesc.setText(to.getTaskDesc());
        mHolder.date.setText(to.getTaskServerTimeStamp());
        mHolder.creator.setText(to.getTaskCreaterDeviceId());
        
        return convertView;
		
		
		
	}

	
	class TaskObjectHolder {
	    TextView taskSyncId;
	    TextView taskServerId;
	    TextView taskTitle;
	    TextView taskDesc;
	    TextView date;
	    TextView creator;
	}

	
}
