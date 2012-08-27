package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.adapter.TraderAdapter;
import com.xtrade.android.fragment.SectionsPagerAdapter;
import com.xtrade.android.fragment.TraderListFragment;
import com.xtrade.android.fragment.TraderTodayFragment;
import com.xtrade.android.provider.TraderTranslator;
import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.EventConstant;

public class TraderActivity extends BaseActivity implements ActionBar.TabListener, EventConstant {

	
	private ViewPager viewPager;
	private SectionsPagerAdapter sectionsPagerAdapter;
	
	public void onCreate(Bundle savedIntanceState) {
		upLevel=false;
		super.onCreate(savedIntanceState);
		setContentView(R.layout.trader);
		// Getting the current action bar
		final ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		 sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
				 new  Class[]{TraderListFragment.class,TraderTodayFragment.class},
				 new String[]{getString(R.string.traders),getString(R.string.today)}, this);	       

	    // Set up the ViewPager with the sections adapter.
	    viewPager = (ViewPager) findViewById(R.id.pager);
	    viewPager.setAdapter(sectionsPagerAdapter);
	    
	    viewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                actionBar.setSelectedNavigationItem(position);
            }
        });
		
	    for (int i = 0; i < sectionsPagerAdapter.getCount(); i++) {
            // Create a tab with text corresponding to the page title defined by the adapter.
            // Also specify this Activity object, which implements the TabListener interface, as the
            // listener for when this tab is selected.
            actionBar.addTab(
                    actionBar.newTab()
                            .setText(sectionsPagerAdapter.getPageTitle(i))
                            .setTabListener(this));
        }
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
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
//		case R.id.mniNewTrader:
//			Intent intent = new Intent(ActionConstant.TRADER_CREATE_UPDATE);
//			intent.putExtra("ACTION_TYPE", TRADER_CREATE_REQUEST_CODE);
//			startActivityForResult(intent, TRADER_CREATE_REQUEST_CODE);
//			break;
		case R.id.mniSettings:
			startActivity(new Intent(ActionConstant.SETTINGS));
			break;
		case R.id.mniAbout:
			startActivity(new Intent(ActionConstant.ABOUT));
			break;
		}
		
		return super.onOptionsItemSelected(menuItem);
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		viewPager.setCurrentItem(tab.getPosition());
		
	}

	@Override
	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTabReselected(Tab tab, FragmentTransaction ft) {
		// TODO Auto-generated method stub
		
	}
	
}
