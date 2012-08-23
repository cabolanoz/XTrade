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
import com.xtrade.android.adapter.ContactAdapter;
import com.xtrade.android.fragment.TraderAboutFragment;
import com.xtrade.android.fragment.TraderContactFragment;
import com.xtrade.android.listener.TraderTabListener;
import com.xtrade.android.provider.ContactTranslator;
import com.xtrade.android.provider.DatabaseContract.Contact;
import com.xtrade.android.provider.DatabaseContract.TraderColumns;
import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.EventConstant;

public class TraderDetailActivity extends BaseActivity implements EventConstant {

	private ActionBar actionBar;
	
	@Override
	public void onCreate(Bundle savedIntanceState) {
		
		super.onCreate(savedIntanceState);
		
		// Getting the current action bar
		actionBar = getSupportActionBar();
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
		traderContactTab.setTabListener(new TraderTabListener<TraderContactFragment>(this, "Contacts", TraderContactFragment.class));
		actionBar.addTab(traderContactTab);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_CANCELED)
			return;
		
		if (resultCode == RESULT_OK && requestCode == CONTACT_CREATE_REQUEST_CODE) {
			ListView lvwContact = (ListView) findViewById(R.id.lvwContact);
			if (lvwContact != null) {
				Cursor cursor = getContentResolver().query(Contact.CONTENT_URI, null, null, null, null);
				((ContactAdapter) lvwContact.getAdapter()).setContactList(new ContactTranslator().translate(cursor));
			}
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
	
}
