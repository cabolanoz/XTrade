package com.xtrade.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.ListView;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.fragment.SectionsPagerAdapter;
import com.xtrade.android.fragment.TraderAboutFragment;
import com.xtrade.android.fragment.TraderContactFragment;
import com.xtrade.android.provider.DatabaseContract.ContactEntity;
import com.xtrade.android.provider.DatabaseContract.ContactColumns;
import com.xtrade.android.provider.DatabaseContract.TraderColumns;
import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.EventConstant;

public class TraderDetailActivity extends BaseActivity implements ActionBar.TabListener , EventConstant {
	private ViewPager viewPager;
	private SectionsPagerAdapter sectionsPagerAdapter;
	private ActionBar actionBar;

	@Override
	public void onCreate(Bundle savedIntanceState) {

		super.onCreate(savedIntanceState);
		setContentView(R.layout.trader);
		// Getting the current action bar
		actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		sectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager(),
				new Class[] { TraderAboutFragment.class,
						TraderContactFragment.class },
				new String[] { getString(R.string.about),
						getString(R.string.contacts) }, this);

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
			// Create a tab with text corresponding to the page title defined by
			// the adapter.
			// Also specify this Activity object, which implements the
			// TabListener interface, as the
			// listener for when this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(sectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == RESULT_CANCELED)
			return;

		if (resultCode == RESULT_OK && (requestCode == CONTACT_CREATE_REQUEST_CODE || requestCode == CONTACT_UPDATE_REQUEST_CODE)) {
			ListView lvwContact = (ListView) findViewById(R.id.lvwContact);
			if (lvwContact != null)
				getContentResolver().query(ContactEntity.CONTENT_URI, null, ContactColumns.TRADER_ID + " = '" + getIntent().getLongExtra(TraderColumns.TRADER_ID, -1) + "'", null, null);

		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu _menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.trader_tab_contact_menu, _menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem _menuItem) {
		switch (_menuItem.getItemId()) {
		case R.id.mniNewContact:
			Intent intent = new Intent(ActionConstant.CONTACT_CREATE_UPDATE);
			intent.putExtra("ACTION_TYPE", CONTACT_CREATE_REQUEST_CODE);
			intent.putExtra(TraderColumns.TRADER_ID, getIntent().getStringExtra(TraderColumns.TRADER_ID));
			startActivityForResult(intent, CONTACT_CREATE_REQUEST_CODE);
			return true;
		default:
			return super.onOptionsItemSelected(_menuItem);
		}
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
