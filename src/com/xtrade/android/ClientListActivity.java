package com.xtrade.android;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

public class ClientListActivity extends BaseActivity {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_list);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.client_menu, menu);
		return true;
	}
	
}
