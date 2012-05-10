package com.xtrade.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

public class ClientActivity extends BaseActivity {

	private Intent intent;

	@Override
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		setContentView(R.layout.client_tab);

		// Getting the Intent which called this activity
		intent = getIntent();

		// Getting the current action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayShowTitleEnabled(false);
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// General client tab
		Tab generalTab = actionBar.newTab();
		generalTab.setIcon(R.drawable.clientgeneral_24x24);
		generalTab.setTag("general");
		generalTab.setTabListener(new ClientTabListener(new ClientGeneralFragment(intent)));
		actionBar.addTab(generalTab);

		// Detail client tab
		Tab detailTab = actionBar.newTab();
		detailTab.setIcon(R.drawable.clientdetail_24x24);
		detailTab.setTag("detail");
		detailTab.setTabListener(new ClientTabListener(new ClientDetailFragment(intent)));
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
//			String clientName = txtClientName.getText().toString();
//			String clientPhone = txtClientPhone.getText().toString();
//			String clientAddress = txtClientAddress.getText().toString();
//
//			// Evaluate if the EditText's content is empty or not
//			if (!StringUtils.isEmpty(clientName) && !StringUtils.isEmpty(clientPhone) && !StringUtils.isEmpty(clientAddress)) {
//				ContentValues contentValues = new ContentValues();
//
//				contentValues.put(ClientColumns.NAME, clientName);
//				contentValues.put(ClientColumns.PHONE, clientPhone);
//				contentValues.put(ClientColumns.ADDRESS, clientAddress);
//
//				Uri clientUri = null;
//
//				// We build a result variable which will be set on default value for canceled
//				int result = RESULT_CANCELED;
//
//				if (extra == CREATE_REQUEST_CODE) {
//					clientUri = getContentResolver().insert(DatabaseContract.Client.CONTENT_URI, contentValues);
//					result = clientUri == null ? RESULT_CANCELED : RESULT_OK;
//				} else if (extra == UPDATE_REQUEST_CODE) {
//					clientUri = Client.buildUri(clientId);
//					result = getContentResolver().update(clientUri, contentValues, null, null) == 0 ? RESULT_CANCELED : RESULT_OK;
//				}
//
//				setResult(result);
//				finish();
//			}
			return true;
		default:
			return super.onOptionsItemSelected(menuItem);
		}
	}

	class ClientTabListener implements ActionBar.TabListener {

		private SherlockFragment mFragment;

		public ClientTabListener(SherlockFragment _mFragment) {
			this.mFragment = _mFragment;
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			ft.add(mFragment, mFragment.getTag());
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			if (mFragment != null)
				ft.detach(mFragment);
		}

		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) { }

	}

}