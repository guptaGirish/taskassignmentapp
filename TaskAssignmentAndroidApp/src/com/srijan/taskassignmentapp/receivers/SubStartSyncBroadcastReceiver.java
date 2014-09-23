package com.srijan.taskassignmentapp.receivers;

import org.openmobster.core.mobileCloud.push.StartSyncBroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class SubStartSyncBroadcastReceiver extends StartSyncBroadcastReceiver {
	String TAG = this.getClass().getName();
	@Override
	public void onReceive(Context ctx, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(ctx, intent);
		
		Log.v(TAG, "In onReceive Method");
		Bundle shared = intent.getBundleExtra("bundle");
        final String channel = shared.getString("channel");
        final String silent = shared.getString("silent");
        
        
		Log.v(TAG, "Received parameters are - "+"channel is -"+channel+", silent is-"+silent);
		Log.v(TAG,"Keyset of bundle is-"+shared.keySet().toString());
		
		
	}

	

}
