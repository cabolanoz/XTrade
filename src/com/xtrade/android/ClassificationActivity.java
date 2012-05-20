package com.xtrade.android;

import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ClassificationActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		setContentView(R.layout.classification);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.classification_menu, menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		super.onOptionsItemSelected(menuItem);
		
		switch(menuItem.getItemId()) {
		
		}
		
		return true;
	}
	
}
