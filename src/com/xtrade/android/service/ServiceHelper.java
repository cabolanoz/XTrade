package com.xtrade.android.service;

import java.util.HashMap;
import java.util.Map;

import com.xtrade.android.util.Debug;

import android.content.Context;
import android.content.Intent;

public class ServiceHelper {

	//TODO: do the serviceMap to avoid recall of service when is already on background
	private Map<String, Intent> serviceMap=new HashMap<String,Intent>(); 
	public Context context;
	private static ServiceHelper instance;
	
	public static ServiceHelper getInstance(Context context){
		if(instance == null){
			instance = new ServiceHelper(context);
		}
		if(instance.context!=context)
			instance.context=context;
		
		return instance;
	}
	
	protected ServiceHelper(Context context){
		this.context=context;
	}
	
	
	public void invokeService(Intent serviceIntent){
		Debug.info(this, "Service is being invoke!");
		context.startService(serviceIntent);
	}
	
}
