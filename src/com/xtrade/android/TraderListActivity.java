package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.adapter.TraderAdapter;
import com.xtrade.android.provider.TraderTranslator;
import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.Debug;
import com.xtrade.android.util.EventConstant;

public class TraderListActivity extends BaseActivity implements EventConstant {

	private BaseAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.trader_list);
		
		// load data from database
		Cursor cursor = getContentResolver().query(com.xtrade.android.provider.DatabaseContract.Trader.CONTENT_URI, null, null, null, null);

		adapter = new TraderAdapter(this, new TraderTranslator().translate(cursor));

		ListView listView = (ListView) findViewById(R.id.lvwTrader);
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Debug.info(this, "Id selected is: " + id);
			}
		});
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(this, "The operation didn't finish properly", Toast.LENGTH_LONG).show();
			return;
		}

		if (resultCode == RESULT_OK && (requestCode == TRADER_CREATE_REQUEST_CODE || requestCode == TRADER_UPDATE_REQUEST_CODE)) {
			Cursor cursor = getContentResolver().query(com.xtrade.android.provider.DatabaseContract.Trader.CONTENT_URI, null, null, null, null);
			((TraderAdapter) adapter).setTraderList(new TraderTranslator().translate(cursor));
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.trader_list_menu, menu);
		return true;
	}
	

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		super.onOptionsItemSelected(menuItem);
		
		switch (menuItem.getItemId()) {
		case R.id.mniNewTrader:
			Intent intent = new Intent(ActionConstant.TRADER_LIST);
			intent.putExtra("ACTION_TYPE", TRADER_CREATE_REQUEST_CODE);
			startActivityForResult(intent, TRADER_CREATE_REQUEST_CODE);
			break;
		case R.id.mniAbout:
			
			startActivity(new Intent(ActionConstant.ABOUT));
			
			break;
		
		
			
		}
		return super.onOptionsItemSelected(menuItem);
	}
	
}
