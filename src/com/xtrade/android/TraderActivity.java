package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.adapter.TraderAdapter;
import com.xtrade.android.fragment.TraderListFragment;
import com.xtrade.android.fragment.TraderTodayFragment;
import com.xtrade.android.listener.TraderTabListener;
import com.xtrade.android.provider.TraderTranslator;
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
		traderListTab.setText(R.string.traders);
		traderListTab.setTabListener(new TraderTabListener<TraderListFragment>(this, "Trader", TraderListFragment.class));
		actionBar.addTab(traderListTab);
		
		// Trader today tab
		Tab traderTodayTab = actionBar.newTab();
		traderTodayTab.setTag("today");
		traderTodayTab.setText(R.string.today);
		traderTodayTab.setTabListener(new TraderTabListener<TraderTodayFragment>(this, "Today", TraderTodayFragment.class));
		actionBar.addTab(traderTodayTab);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_CANCELED)
			return;
		
		if (resultCode == RESULT_OK && (requestCode == TRADER_CREATE_REQUEST_CODE || requestCode == TRADER_UPDATE_REQUEST_CODE)) {
			ListView listView = (ListView) findViewById(R.id.lvwTrader);
			if (listView != null) {
				Cursor cursor = this.getContentResolver().query(com.xtrade.android.provider.DatabaseContract.Trader.CONTENT_URI, null, null, null, null);
				((TraderAdapter) listView.getAdapter()).setTraderList(new TraderTranslator().translate(cursor));
			}
		}
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
		case R.id.mniSettings:
			startActivity(new Intent(ActionConstant.SETTINGS));
			break;
		case R.id.mniAbout:
			startActivity(new Intent(ActionConstant.ABOUT));
			break;
					
		}
		
		return super.onOptionsItemSelected(menuItem);
	}
	
}
