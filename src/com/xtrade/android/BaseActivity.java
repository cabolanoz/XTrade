package com.xtrade.android;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.xtrade.android.service.ServiceHelper;

public class BaseActivity extends SherlockFragmentActivity {
	
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
