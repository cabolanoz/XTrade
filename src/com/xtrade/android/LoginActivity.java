package com.xtrade.android;

import org.apache.commons.lang.StringUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.Debug;
import com.xtrade.android.util.LoginParameter;
import com.xtrade.android.util.Parameter;

public class LoginActivity extends BaseActivity {

	private LoginBroadcastReceiver receiver;
	private EditText textUsername;
	private EditText textPassword;
	private CheckBox checkRememberMe;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		receiver = new LoginBroadcastReceiver();

		textUsername=((EditText)findViewById(R.id.textUsername));
		textPassword=((EditText)findViewById(R.id.textPassword));
		checkRememberMe=((CheckBox)findViewById(R.id.checkRememberMe));
		
		Button btnLogin = (Button) findViewById(R.id.buttonLogin);
		//TODO: check there is internetAccess
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String username= textUsername.getText().toString();
				String password= textPassword.getText().toString();
				boolean rememberMe=checkRememberMe.isChecked();
				//TODO: handle the 
				if(!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)){
					Intent loginIntent=new Intent(ActionConstant.LOGIN);
					loginIntent.putExtra(LoginParameter.USERNAME, username);
					loginIntent.putExtra(LoginParameter.PASSWORD, password);
					loginIntent.putExtra(LoginParameter.REMEMBER_ME, rememberMe);
					serviceHelper.invokeService(loginIntent);
				}else{
					//TODO: handle this with a Dialog API of XTrade (Do the Dialog API :D)
				}
				
			}
		});

		if(getAppSharedPreference().getBoolean(LoginParameter.REMEMBER_ME, false)){
			//if the remember me is checked on the preferences
			loadSavedValues();
		}
		

	}
	
	private void loadSavedValues(){
		textUsername.setText(getAppSharedPreference().getString(LoginParameter.USERNAME, null));
		textPassword.setText(getAppSharedPreference().getString(LoginParameter.PASSWORD, null));
		checkRememberMe.setChecked(true);
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

	public  class LoginBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getBooleanExtra(Parameter.SUCCESS, false)){
				if(intent.getBooleanExtra(LoginParameter.REMEMBER_ME, false)){
					//Saving the preferences values
					Editor editor=getAppSharedPreference().edit();
					editor.putBoolean(LoginParameter.REMEMBER_ME, true);
					editor.putString(LoginParameter.USERNAME, intent.getStringExtra(LoginParameter.USERNAME));
					editor.putString(LoginParameter.PASSWORD, intent.getStringExtra(LoginParameter.PASSWORD));
					editor.commit();
				}
				
				startActivity(ActionConstant.CLIENT_LIST);
			}else{
				Debug.info(this, "Auth failed please ");
				//TODO: handle the authentication failed and why?
			}
		}

	}

}
