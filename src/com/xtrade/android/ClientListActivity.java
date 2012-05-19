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
import com.xtrade.android.adapter.ClientAdapter;
import com.xtrade.android.provider.ClientTranslator;
import com.xtrade.android.util.Debug;
import com.xtrade.android.util.EventConstant;

public class ClientListActivity extends BaseActivity implements EventConstant {

	private BaseAdapter adapter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_list);
		
		// load data from database
		Cursor cursor = getContentResolver().query(com.xtrade.android.provider.DatabaseContract.Client.CONTENT_URI, null, null, null, null);

		adapter = new ClientAdapter(this, new ClientTranslator().translate(cursor));

		ListView listView = (ListView) findViewById(R.id.lvwClient);
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

		if (resultCode == RESULT_OK && (requestCode == CLIENT_CREATE_REQUEST_CODE || requestCode == CLIENT_UPDATE_REQUEST_CODE)) {
			Cursor cursor = getContentResolver().query(com.xtrade.android.provider.DatabaseContract.Client.CONTENT_URI, null, null, null, null);
			((ClientAdapter) adapter).setClientList(new ClientTranslator().translate(cursor));
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.client_list_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		super.onOptionsItemSelected(menuItem);
		
		switch (menuItem.getItemId()) {
		case R.id.mniNewClient:
			Intent intent = new Intent(this, ClientActivity.class);
			intent.putExtra("ACTION_TYPE", CLIENT_CREATE_REQUEST_CODE);
			startActivityForResult(intent, CLIENT_CREATE_REQUEST_CODE);
			return true;
		default:
			return super.onOptionsItemSelected(menuItem);
		}
	}
	
}
