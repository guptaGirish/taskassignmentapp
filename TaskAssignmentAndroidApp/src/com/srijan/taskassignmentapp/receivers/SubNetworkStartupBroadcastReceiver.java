package com.srijan.taskassignmentapp.receivers;

import org.openmobster.core.mobileCloud.push.NetworkStartupBroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class SubNetworkStartupBroadcastReceiver extends NetworkStartupBroadcastReceiver{
	
	String TAG = this.getClass().getName();
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		// TODO Auto-generated method stub
		super.onReceive(arg0, arg1);
		
		Log.v(TAG, "In onReceive Method");
		
		
	}


}
