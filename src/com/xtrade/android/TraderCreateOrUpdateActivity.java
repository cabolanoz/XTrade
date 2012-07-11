package com.xtrade.android;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.listener.TraderTabListener;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.provider.DatabaseContract.Trader;
import com.xtrade.android.provider.DatabaseContract.TraderColumns;
import com.xtrade.android.util.EventConstant;

public class TraderCreateOrUpdateActivity extends BaseActivity implements EventConstant {

	private Intent intent;
	
	@Override
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);

		// Getting the intent which call this activity
		intent = getIntent();
		
		// Getting the current action bar
		ActionBar actionBar = getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

		// General client tab
		Tab generalTab = actionBar.newTab();
		generalTab.setTag("about");
		generalTab.setText(R.string.about);
		generalTab.setTabListener(new TraderTabListener<TraderGeneralFragment>(this, "About", TraderGeneralFragment.class));
		actionBar.addTab(generalTab);

		// Detail client tab
		Tab detailTab = actionBar.newTab();
		detailTab.setTag("detail");
		detailTab.setText(R.string.detail);
		detailTab.setTabListener(new TraderTabListener<TraderDetailFragment>(this, "Detail", TraderDetailFragment.class));
		actionBar.addTab(detailTab);

		// TODO: handle the lifecycle when orientation changes to save the values
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.trader_tab_general_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch(menuItem.getItemId()) {
		case R.id.mniSaveTrader:
			// We get the ACTION_TYPE extra which tells us what operation we must perform (Save or Update)
			int extra = intent.getIntExtra("ACTION_TYPE", -1);
			
			EditText txtTraderName = (EditText) findViewById(R.id.etxTraderName);
			EditText txtTraderWebsite = (EditText) findViewById(R.id.etxTraderWebsite);
			EditText txtTraderAddress = (EditText) findViewById(R.id.etxTraderAddress);
			
			String traderName = txtTraderName.getText().toString();
			String traderWebsite = parseUrl(txtTraderWebsite.getText().toString());
			String traderAddress = txtTraderAddress.getText().toString();

			if (!isValidUrl(traderWebsite)) {
				Toast.makeText(getApplicationContext(), "", Toast.LENGTH_LONG);
				break;
			}
			
			// Evaluate if the EditText's content is empty or not
			if (!StringUtils.isEmpty(traderName) && !StringUtils.isEmpty(traderWebsite) && !StringUtils.isEmpty(traderAddress)) {
				ContentValues contentValues = new ContentValues();

				contentValues.put(TraderColumns.NAME, traderName);
				contentValues.put(TraderColumns.WEBSITE, traderWebsite);
				contentValues.put(TraderColumns.ADDRESS, traderAddress);

				Uri clientUri = null;

				// We build a result variable which will be set on default value for canceled
				int result = RESULT_CANCELED;

				if (extra == TRADER_CREATE_REQUEST_CODE) {
					clientUri = getContentResolver().insert(DatabaseContract.Trader.CONTENT_URI, contentValues);
					result = clientUri == null ? RESULT_CANCELED : RESULT_OK;
				} else if (extra == TRADER_UPDATE_REQUEST_CODE) {
					String clientId = intent.getStringExtra(DatabaseContract.TraderColumns.TRADER_ID);
					clientUri = Trader.buildUri(clientId);
					result = getContentResolver().update(clientUri, contentValues, null, null) == 0 ? RESULT_CANCELED : RESULT_OK;
				}

				setResult(result);
				finish();
			}
			return true;
		default:
			return super.onOptionsItemSelected(menuItem);
		}
		
		return super.onOptionsItemSelected(menuItem);
	}
	
	private String parseUrl(String website) {
		if (!website.startsWith("http://") && !website.startsWith("https://"))
			return "http://" + website;
		return website;
	}
	
	private boolean isValidUrl(String website) {
		String regex = "<\\b(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]>";
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(website);
		
		return matcher.matches();
	}

}