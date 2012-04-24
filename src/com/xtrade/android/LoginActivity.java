package com.xtrade.android;

import com.xtrade.android.util.ActionConstant;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LoginActivity extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		Button btnLogin = (Button)findViewById(R.id.buttonLogin);
		btnLogin.setOnClickListener(new OnClickListener(){
			public void onClick(View view){
				//TODO: do the login mockup login here
				startActivity(ActionConstant.CLIENT_LIST);
			}
		});
		
	}
	
}
