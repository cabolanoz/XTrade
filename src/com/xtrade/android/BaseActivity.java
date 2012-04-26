package com.xtrade.android;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.xtrade.android.service.ServiceHelper;
import com.xtrade.android.util.Settings;

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
	
	/**
	 * SharedPreferences object
	 * 
	 * */
	protected SharedPreferences getAppSharedPreference(){
		SharedPreferences xTradeSettings = getSharedPreferences(Settings.SHARED_PREFERENCES, MODE_PRIVATE);  
		
		return xTradeSettings;
	}
	
}
