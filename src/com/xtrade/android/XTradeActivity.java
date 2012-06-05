package com.xtrade.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.view.Menu;
import com.xtrade.android.util.ActionConstant;

public class XTradeActivity extends BaseActivity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		Button buttonClient = (Button) findViewById(R.id.buttonTrader);
		buttonClient.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				startActivity(new Intent(ActionConstant.TRADER_LIST));
			}
		});
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		getSupportMenuInflater().inflate(R.menu.main, menu);
		
		return super.onCreateOptionsMenu(menu);
	}
	
}