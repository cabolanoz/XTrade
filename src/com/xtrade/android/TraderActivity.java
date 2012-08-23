package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
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
		
		 sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());	       

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
	
	/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            switch(i){
            case 0:
            	return Fragment.instantiate(TraderActivity.this, TraderListFragment.class.getName());
            case 1:
            	return Fragment.instantiate(TraderActivity.this, TraderTodayFragment.class.getName());
            
            }
            return null;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
            //TODO: this should go to string.xml
                case 0: return "Traders";
                case 1: return "Events";
            }
            return null;
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
		// TODO Auto-generated method stub
		
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
