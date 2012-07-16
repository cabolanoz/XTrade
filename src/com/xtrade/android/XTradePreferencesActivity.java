package com.xtrade.android;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockPreferenceActivity;
import com.actionbarsherlock.view.MenuItem;

public class XTradePreferencesActivity extends SherlockPreferenceActivity {

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Getting the action bar
		ActionBar actionBar = getSupportActionBar();
		if (actionBar != null) {
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayUseLogoEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(true);
			actionBar.setIcon(getResources().getDrawable(R.drawable.ic_logo_text));
		}

		addPreferencesFromResource(R.xml.preferences);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
		case android.R.id.home:
			finish();
			break;
		
		}
		
		return super.onOptionsItemSelected(item);
	}
	
	

}
