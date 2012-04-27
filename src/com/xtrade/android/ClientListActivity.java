package com.xtrade.android;

import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.util.ActionConstant;

public class ClientListActivity extends BaseActivity  {
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_list);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.client_list_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.mniNewClient:
			startActivity(ActionConstant.CLIENT);
			return true;
		case R.id.mniDeleteClient:
			return true;
		default:
			return super.onOptionsItemSelected(menuItem);
		}
	}
	
}
