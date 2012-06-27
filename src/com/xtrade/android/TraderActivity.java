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

public class TraderActivity extends BaseActivity implements EventConstant {

	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		
		// Getting the current action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// Trader list tab
		Tab traderListTab = actionBar.newTab();
		traderListTab.setTag("list");
		traderListTab.setText(R.string.trader);
		traderListTab.setTabListener(new TraderTabListener<TraderListActivity>(this, "Trader", TraderListActivity.class));
		
		// Trader today tab
		Tab traderTodayTab = actionBar.newTab();
		traderTodayTab.setTag("today");
		traderTodayTab.setText(R.string.today);
		traderTodayTab.setTabListener(new TraderTabListener<TraderTodayActivity>(this, "Today", TraderTodayActivity.class));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.trader_tab_list_menu, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
		case R.id.mniNewTrader:
			Intent intent = new Intent(ActionConstant.TRADER_CREATE_UPDATE);
			intent.putExtra("ACTION_TYPE", TRADER_CREATE_REQUEST_CODE);
			startActivityForResult(intent, TRADER_CREATE_REQUEST_CODE);
			break;
		case R.id.mniAbout:
			startActivity(new Intent(ActionConstant.ABOUT));
			break;
		}
		return super.onOptionsItemSelected(menuItem);
	}
	
}
