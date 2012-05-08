package com.xtrade.android;

import org.apache.commons.lang.StringUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.provider.DatabaseContract.Client;
import com.xtrade.android.provider.DatabaseContract.ClientColumns;
import com.xtrade.android.util.Settings;

public class ClientActivity extends BaseActivity {

	private final int CREATE_REQUEST_CODE = 100;
	private final int UPDATE_REQUEST_CODE = 101;

	private Intent intent;
	private int extra;
	
	private String clientId;
	private EditText txtClientName;
	private EditText txtClientPhone;
	private EditText txtClientAddress;

	@Override
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		setContentView(R.layout.client_tab);
		
		// Getting the Intent which called this activity
		intent = getIntent();
		// We get the ACTION_TYPE extra which tells us what operation we must perform (Save or Update)
		extra = intent.getIntExtra("ACTION_TYPE", -1);
		
		// Getting the current action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		
		// General client tab
		ActionBar.Tab generalTab = actionBar.newTab();
		generalTab.setIcon(R.drawable.clientgeneral_24x24);
		generalTab.setTabListener(new ClientTabListener(new ClientFragment("General Info", R.layout.client_tab_general)));
		actionBar.addTab(generalTab);
		
		// Detail client tab
		ActionBar.Tab detailTab = actionBar.newTab();
		detailTab.setIcon(R.drawable.clientdetail_24x24);
		detailTab.setTabListener(new ClientTabListener(new ClientFragment("Detail Info", R.layout.client_tab_detail)));
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
		switch(menuItem.getItemId()) {
		case R.id.mniSaveClient:
			String clientName = txtClientName.getText().toString();
			String clientPhone = txtClientPhone.getText().toString();
			String clientAddress = txtClientAddress.getText().toString();

			// Evaluate if the EditText's content is empty or not
			if (!StringUtils.isEmpty(clientName) && !StringUtils.isEmpty(clientPhone) && !StringUtils.isEmpty(clientAddress)) {
				ContentValues contentValues = new ContentValues();

				contentValues.put(ClientColumns.NAME, clientName);
				contentValues.put(ClientColumns.PHONE, clientPhone);
				contentValues.put(ClientColumns.ADDRESS, clientAddress);

				Uri clientUri = null;

				// We build a result variable which will be set on default value for canceled
				int result = RESULT_CANCELED;

				if (extra == CREATE_REQUEST_CODE) {
					clientUri = getContentResolver().insert(DatabaseContract.Client.CONTENT_URI, contentValues);
					result = clientUri == null ? RESULT_CANCELED : RESULT_OK;
				} else if (extra == UPDATE_REQUEST_CODE) {
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
	
	class ClientTabListener implements ActionBar.TabListener {
		
		private ClientFragment clientFragment;
		
		public ClientTabListener(ClientFragment _clientFragment) {
			this.clientFragment = _clientFragment;
		}
		
		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.add(clientFragment, clientFragment.getText());
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			ft.remove(clientFragment);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) { }
		
	}

	class ClientFragment extends SherlockFragment {
		
		private String mTitle;
		private int mResource;
		
		public ClientFragment(String _mTitle, int _mResource) {
			this.mTitle = _mTitle;
			this.mResource = _mResource;
		}
		
		public String getText() {
			return mTitle;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			View fragmentView = inflater.inflate(mResource, container, false);

			// Getting the EditText from the general tab fragment
			if (R.layout.client_tab_general == mResource) {
				txtClientName = (EditText) fragmentView.findViewById(R.id.txtClientName);
				txtClientPhone = (EditText) fragmentView.findViewById(R.id.txtClientPhone);
				txtClientAddress = (EditText) fragmentView.findViewById(R.id.txtClientAddress);
				
				// Setting default values while we're on developer mode
				if (Settings.DEBUG && extra == CREATE_REQUEST_CODE) {
					txtClientName.setText("Loren ipsum");
					txtClientPhone.setText("222222");
					txtClientAddress.setText("Aenean lacinia bibendum nulla sed consectetur.");
				}

				// If the ACTION_TYPE extra is for Updating the client info, we get the client date from database, then we set it on corresponding EditTexts
				if (extra == UPDATE_REQUEST_CODE)
					if (extra != -1 && intent.getStringExtra(DatabaseContract.ClientColumns.CLIENT_ID) != null && !"".equals(intent.getStringExtra(DatabaseContract.ClientColumns.CLIENT_ID))) {
						clientId = intent.getStringExtra(DatabaseContract.ClientColumns.CLIENT_ID);
						
						CursorLoader cursorLoader = new CursorLoader(getBaseContext(), DatabaseContract.Client.buildUri(clientId), null, null, null, null);
						Cursor cursor = cursorLoader.loadInBackground();
						if (cursor != null) {
							if (cursor.moveToNext()) {
								txtClientName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClientColumns.NAME)));
								txtClientPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClientColumns.PHONE)));
								txtClientAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClientColumns.ADDRESS)));
							}
						cursor.close();
						}
				}
			}
			
			return fragmentView;
		}
		
	}
	
}