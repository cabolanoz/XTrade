package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.adapter.ClientAdapter;
import com.xtrade.android.object.Client;
import com.xtrade.android.provider.ClientTranslator;
import com.xtrade.android.util.Debug;

public class ClientListActivity extends BaseActivity {

	private ArrayAdapter<Client> adapter;
	private final int CREATE_REQUEST_CODE = 100;
	private final int UPDATE_REQUEST_CODE = 101;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_list);

		// load data from database
		Cursor cursor = getContentResolver().query(com.xtrade.android.provider.DatabaseContract.Client.CONTENT_URI, null, null, null, null);

		adapter = new ClientAdapter(this, new ClientTranslator().translate(cursor));

		ListView listView = (ListView) findViewById(R.id.lvwClient);
		listView.setAdapter(adapter);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == RESULT_CANCELED) {
			Toast.makeText(this, "The operation didn't finish properly", Toast.LENGTH_LONG).show();
			return;
		}
		
		// TODO: Should refresh the list with the inserted or updated values
		if (requestCode == CREATE_REQUEST_CODE && resultCode == RESULT_OK) {
			Debug.info(this, "Insertion was made successfully!!!");
		} else if (requestCode == UPDATE_REQUEST_CODE && resultCode == RESULT_OK) {
			Debug.info(this, "Update was made successfully!!!");
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
		switch (menuItem.getItemId()) {
		case R.id.mniNewClient:
			Intent intent = new Intent(this, ClientActivity.class);
			intent.putExtra("ACTION_TYPE", CREATE_REQUEST_CODE);
			startActivityForResult(intent, CREATE_REQUEST_CODE);
			return true;
		default:
			return super.onOptionsItemSelected(menuItem);
		}
	}

//	private void setClientList() {
//		Cursor cursor = getContentResolver().query(com.xtrade.android.provider.DatabaseContract.Client.CONTENT_URI, null, null, null, null);
//		ClientTranslator clientTranslator = new ClientTranslator();
//		clientTranslator.translate(cursor);
//		adapter.notifyDataSetChanged();
//	}
	
}
