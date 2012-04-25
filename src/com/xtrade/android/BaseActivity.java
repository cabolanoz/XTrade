package com.xtrade.android;

import com.xtrade.android.service.ServiceHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {
	
	protected ServiceHelper serviceHelper;
	
	
	@Override
	public void onCreate(Bundle savedIntanceState ){
		super.onCreate(savedIntanceState);
		serviceHelper = ServiceHelper.getInstance(this);
	}
	
	/**
	 * Parent method for activites call through actions on the application
	 * 
	 * */
	protected void startActivity(String action){
		startActivity(new Intent(action));
	}
	
}
