package com.xtrade.android;

import android.content.Intent;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.listener.TraderTabListener;
import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.EventConstant;

public class TraderDetailActivity extends BaseActivity implements EventConstant {

	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		
		// Getting the current action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Trader about tab
		Tab traderAboutTab = actionBar.newTab();
		traderAboutTab.setTag("about");
		traderAboutTab.setText(R.string.about);
		traderAboutTab.setTabListener(new TraderTabListener<TraderAboutFragment>(this, "About", TraderAboutFragment.class));
		actionBar.addTab(traderAboutTab);
		
		// Trader contacts tab
		Tab traderContactTab = actionBar.newTab();
		traderContactTab.setTag("contact");
		traderContactTab.setText(R.string.contacts);
		traderContactTab.setTabListener(new TraderTabListener<TraderContactFragment>(this, "contacts", TraderContactFragment.class));
		actionBar.addTab(traderContactTab);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu _menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.trader_tab_contact_menu, _menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem _menuItem) {
		switch (_menuItem.getItemId()) {
		case R.id.mniNewContact:
			startActivity(new Intent(ActionConstant.CONTACT_CREATE_UPDATE));
			break;
		}
		return super.onOptionsItemSelected(_menuItem);
	}
	
}
