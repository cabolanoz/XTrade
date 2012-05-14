package com.xtrade.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.xtrade.android.util.ActionConstant;

public class XTradeActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		Button buttonClient = (Button) findViewById(R.id.buttonClient);
		buttonClient.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent(ActionConstant.CLIENT_LIST));
			}
		});
	}
	
}