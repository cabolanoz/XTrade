package com.xtrade.android;

import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.xtrade.android.listener.TraderTabListener;
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
	
}
