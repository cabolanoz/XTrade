package com.xtrade.android;

import org.apache.commons.lang.StringUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.Debug;
import com.xtrade.android.util.LoginParameter;
import com.xtrade.android.util.Parameter;

public class LoginActivity extends BaseActivity {

	private LoginBroadcastReceiver receiver;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		receiver = new LoginBroadcastReceiver();

		Button btnLogin = (Button) findViewById(R.id.buttonLogin);
		//TODO: check there is internetAccess
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String username= ((EditText)findViewById(R.id.textUsername)).getText().toString();
				String password= ((EditText)findViewById(R.id.textPassword)).getText().toString();
				//TODO: handle the 
				if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
					Intent loginIntent=new Intent(ActionConstant.LOGIN);
					loginIntent.putExtra(LoginParameter.USERNAME, username);
					loginIntent.putExtra(LoginParameter.PASSWORD, password);
					serviceHelper.invokeService(loginIntent);
				}else{
					//TODO: handle this with a Dialog API of XTrade (Do the Dialog API :D)
				}
				
			}
		});

		

	}

	@Override
	public void onResume() {
		IntentFilter filter = new IntentFilter();
		filter.addAction(ActionConstant.LOGIN);
		registerReceiver(receiver, filter);
		super.onResume();

	}

	@Override
	public void onPause() {
		unregisterReceiver(receiver);
		super.onPause();
	}

	public class LoginBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getBooleanExtra(Parameter.SUCCESS, false)){
				startActivity(ActionConstant.CLIENT_LIST);
			}else{
				Debug.info(this, "Auth failed please ");
				//TODO: handle the authentication failed and why?
			}
		}

	}

}
