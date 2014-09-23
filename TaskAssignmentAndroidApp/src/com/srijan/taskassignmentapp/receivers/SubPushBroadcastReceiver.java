package com.srijan.taskassignmentapp.receivers;

import org.openmobster.core.mobileCloud.api.ui.framework.push.PushBroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SubPushBroadcastReceiver extends PushBroadcastReceiver {

	String TAG = this.getClass().getName();
	@Override
	public void onReceive(Context ctx, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(ctx, intent);
		
		Log.v(TAG, "In onReceive Method");
		
		try{
		 String message = intent.getStringExtra("message");
         String title = intent.getStringExtra("title");
         String detail = intent.getStringExtra("detail");
         
		Log.v(TAG, "Title is "+title+", Message is "+message+", detail is "+detail );
		Log.v(TAG, "keyset is "+intent.getBundleExtra("bundle").keySet());
		
		
		}
		catch(Exception e)
		{
			Log.v(TAG, "Exception is "+e.getMessage());
		}
	}

	
	
	
	
}
