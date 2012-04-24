package com.xtrade.android;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;

public class BaseActivity extends FragmentActivity {

	
	/**
	 * Parent method for activites call through actions on the application
	 * 
	 * */
	protected void startActivity(String action){
		startActivity(new Intent(action));
	}
	
}
