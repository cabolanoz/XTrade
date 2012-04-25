package com.xtrade.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

public class ServiceHelper {

	private Map<String, Intent> serviceMap=new HashMap<String,Intent>(); 
	private Context context;
	private ServiceHelper instance;
	
	public ServiceHelper getInstance(Context context){
		if(instance == null){
			instance = new ServiceHelper(context);
		}
		if(this.context!=context)
		return instance;
	}
	
	protected ServiceHelper(Context context){
		this.context=context;
	}
	
	
	public void invokeService(Intent serviceIntent){
		context.startService(serviceIntent);
	}
	
}
