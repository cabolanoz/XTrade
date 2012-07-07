package com.xtrade.android;

import android.os.Bundle;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.util.EventConstant;

public class ContactCreateOrUpdateActivity extends BaseActivity implements EventConstant {

	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		
		setContentView(R.layout.contact);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu _menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.contact_menu, _menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem _menuItem) {
		switch (_menuItem.getItemId()) {
		case R.id.mniSaveContact:
			break;
		}
		
		return super.onOptionsItemSelected(_menuItem);
	}
	
}
