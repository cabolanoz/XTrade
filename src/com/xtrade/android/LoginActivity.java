package com.xtrade.android;

import org.apache.commons.lang.StringUtils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.Debug;
import com.xtrade.android.util.LoginParameter;
import com.xtrade.android.util.Parameter;
import com.xtrade.android.util.Settings;

public class LoginActivity extends BaseActivity {

	private LoginBroadcastReceiver receiver;
	private EditText textUsername;
	private EditText textPassword;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		receiver = new LoginBroadcastReceiver();
		
		findViewById(R.id.textForgotPassword).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this,ForgotPasswordActivity.class));
			}
			
		});
		
		findViewById(R.id.textSignIn).setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
			}
			
		});

		textUsername = ((EditText) findViewById(R.id.textUsername));
		textPassword = ((EditText) findViewById(R.id.textPassword));
		
		// if we are on development stage
		if (Settings.DEBUG) {
			textUsername.setText(Settings.DEFAULT_USERNAME);
			textPassword.setText(Settings.DEFAULT_PASSWORD);
		}

		textUsername = ((EditText) findViewById(R.id.textUsername));
		textPassword = ((EditText) findViewById(R.id.textPassword));
		//checkRememberMe = ((CheckBox) findViewById(R.id.checkRememberMe));

		// Checking internet access
		if (thereIsInternetAccess()) {
			Debug.info(this, "We have internet access, so we can deploy more information");
		}

		Button btnLogin = (Button) findViewById(R.id.buttonLogin);
		btnLogin.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startProgressDialog(getString(R.string.signing_in));
				String username = textUsername.getText().toString();
				String password = textPassword.getText().toString();
				
				if (!StringUtils.isEmpty(username) && !StringUtils.isEmpty(password)) {
					Intent loginIntent = new Intent(ActionConstant.LOGIN);
					loginIntent.putExtra(LoginParameter.USERNAME, username);
					loginIntent.putExtra(LoginParameter.PASSWORD, password);
					//loginIntent.putExtra(LoginParameter.REMEMBER_ME, rememberMe);
					
					serviceHelper.invokeService(loginIntent);
				} else {
					// TODO: handle this with a Dialog API of XTrade (Do the Dialog API :D)
				}

			}
		});

		if (getAppSharedPreference().getBoolean(LoginParameter.REMEMBER_ME, false)) {
			// if the remember me is checked on the preferences
			loadSavedValues();
		}

	}

	private boolean thereIsInternetAccess() {
		ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return networkInfo != null;
	}

	private void loadSavedValues() {
		textUsername.setText(getAppSharedPreference().getString(LoginParameter.USERNAME, null));
		textPassword.setText(getAppSharedPreference().getString(LoginParameter.PASSWORD, null));
		
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
			if (intent.getBooleanExtra(Parameter.SUCCESS, false)) {
				// Saving the preferences values
				Editor editor = getAppSharedPreference().edit();
				editor.putString(LoginParameter.USERNAME, intent.getStringExtra(LoginParameter.USERNAME));
				editor.putString(LoginParameter.PASSWORD, intent.getStringExtra(LoginParameter.PASSWORD));
				editor.commit();
				
				startActivity(ActionConstant.MAIN_XTRADE);

			} else {
				Debug.info(this, "Authentication failed!!!");
				Toast.makeText(context, getString(R.string.authentication_failed), Toast.LENGTH_LONG).show();
			}
			stopProgressDialog();
		}

	}
	


}
