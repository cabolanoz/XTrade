package com.xtrade.android.service;

import android.app.IntentService;
import android.content.Intent;

import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.LoginParameter;
import com.xtrade.android.util.Parameter;
import com.xtrade.android.util.Settings;

public class XTradeBaseService extends IntentService{

	
	public XTradeBaseService() {
		super(XTradeBaseService.class.getSimpleName());
	}

	//this method run on a different thread
	@Override
	protected void onHandleIntent(Intent intent) {
		//depending on the action we execute an action currently 
		//there will be few action so we can handle it here
		if(intent.getAction().equals(ActionConstant.LOGIN)){
			String username = intent.getStringExtra(LoginParameter.USERNAME);
			String password= intent.getStringExtra(LoginParameter.PASSWORD);
			
			boolean success=false;
			if(username.equals(Settings.DEFAULT_USERNAME) && password.equals(Settings.DEFAULT_PASSWORD) ){
				
				//we resend the login intent with extra parameters
				success=true;
			}
			
			intent.removeExtra(LoginParameter.USERNAME);
			intent.removeExtra(LoginParameter.PASSWORD);
			intent.putExtra(LoginParameter.SUCCESS, success);
			
			sendBroadcast(intent);
		}
		
		
	}
	
	

}
