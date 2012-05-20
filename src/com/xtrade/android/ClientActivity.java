package com.xtrade.android;

import org.apache.commons.lang.StringUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.provider.DatabaseContract.Client;
import com.xtrade.android.provider.DatabaseContract.ClientColumns;
import com.xtrade.android.util.EventConstant;

public class ClientActivity extends BaseActivity implements EventConstant {

	private Intent intent;
	
	@Override
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);

		// Getting the intent which call this activity
		intent = getIntent();
		
		// Getting the current action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// General client tab
		Tab generalTab = actionBar.newTab();
		generalTab.setIcon(R.drawable.clientgeneral);
		generalTab.setTag("general");
		generalTab.setTabListener(new ClientTabListener<ClientGeneralFragment>("General", ClientGeneralFragment.class));
		actionBar.addTab(generalTab);

		// Detail client tab
		Tab detailTab = actionBar.newTab();
		detailTab.setIcon(R.drawable.clientdetail);
		detailTab.setTag("detail");
		detailTab.setTabListener(new ClientTabListener<ClientDetailFragment>("Detail", ClientDetailFragment.class));
		actionBar.addTab(detailTab);

		// TODO: handle the lifecycle when orientation changes to save the values
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.client_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		super.onOptionsItemSelected(menuItem);
		
		switch(menuItem.getItemId()) {
		case R.id.mniSaveClient:
			// We get the ACTION_TYPE extra which tells us what operation we must perform (Save or Update)
			int extra = intent.getIntExtra("ACTION_TYPE", -1);
			
			EditText txtTraderDescription = (EditText) findViewById(R.id.etxTraderDescription);
			EditText txtTraderWebsite = (EditText) findViewById(R.id.etxTraderWebsite);
			EditText txtTraderLocation = (EditText) findViewById(R.id.etxTraderLocation);
			EditText txtTraderNote = (EditText) findViewById(R.id.etxTraderNote);
			
			String traderDescription = txtTraderDescription.getText().toString();
			String traderWebsite = txtTraderWebsite.getText().toString();
			String traderLocation = txtTraderLocation.getText().toString();
			String traderNote = txtTraderNote.getText().toString();

			// Evaluate if the EditText's content is empty or not
			if (!StringUtils.isEmpty(traderDescription) && !StringUtils.isEmpty(traderWebsite) && !StringUtils.isEmpty(traderLocation) && !StringUtils.isEmpty(traderNote)) {
				ContentValues contentValues = new ContentValues();

				contentValues.put(ClientColumns.DESCRIPTION, traderDescription);
				contentValues.put(ClientColumns.WEBSITE, traderWebsite);
				contentValues.put(ClientColumns.LOCATION, traderLocation);
				contentValues.put(ClientColumns.NOTE, traderNote);

				Uri clientUri = null;

				// We build a result variable which will be set on default value for canceled
				int result = RESULT_CANCELED;

				if (extra == CLIENT_CREATE_REQUEST_CODE) {
					clientUri = getContentResolver().insert(DatabaseContract.Client.CONTENT_URI, contentValues);
					result = clientUri == null ? RESULT_CANCELED : RESULT_OK;
				} else if (extra == CLIENT_UPDATE_REQUEST_CODE) {
					String clientId = intent.getStringExtra(DatabaseContract.ClientColumns.TRADER_ID);
					clientUri = Client.buildUri(clientId);
					result = getContentResolver().update(clientUri, contentValues, null, null) == 0 ? RESULT_CANCELED : RESULT_OK;
				}

				setResult(result);
				finish();
			}
			return true;
		default:
			return super.onOptionsItemSelected(menuItem);
		}
	}

	class ClientTabListener<T extends Fragment> implements ActionBar.TabListener {

		private Fragment fragment;
	    private final String tag;
	    private final Class<T> mClass;

		public ClientTabListener(String tag,Class<T> mClass) {
			this.tag = tag;
			this.mClass = mClass;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			 // Check if the fragment is already initialized
	        if (fragment == null) {
	            // If not, instantiate and add it to the activity
	            fragment = Fragment.instantiate(ClientActivity.this, mClass.getName());
	            ft.add(android.R.id.content, fragment, tag);
	        } else {
	            // If it exists, simply attach it in order to show it
	            ft.attach(fragment);
	        }
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			 if (fragment != null) {
		            // Detach the fragment, because another one is being attached
		            ft.detach(fragment);
		        }	
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) { }

	}

}