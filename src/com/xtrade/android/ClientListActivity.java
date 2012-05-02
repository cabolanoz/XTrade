package com.xtrade.android;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.adapter.ClientAdapter;
import com.xtrade.android.object.Client;
import com.xtrade.android.provider.ClientTranslator;
import com.xtrade.android.util.ActionConstant;

public class ClientListActivity extends BaseActivity  {
	
	private ArrayAdapter<Client> adapter;
	private List<Client> clientList = new ArrayList<Client>();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.client_list);
		
		//load data from database
		Cursor cursor=getContentResolver().query(com.xtrade.android.provider.DatabaseContract.Client.CONTENT_URI, null, null, null, null);
		
		adapter = new ClientAdapter(this, new ClientTranslator().translate(cursor));
		
		ListView listView = (ListView) findViewById(R.id.lvwClient);
		listView.setAdapter(adapter);
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
			startActivity(ActionConstant.CLIENT);
			return true;
		case R.id.mniDeleteClient:
			return true;
		default:
			return super.onOptionsItemSelected(menuItem);
		}
	}
	
}
