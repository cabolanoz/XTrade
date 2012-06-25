package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.internal.widget.IcsAdapterView;
import com.actionbarsherlock.view.ActionMode;
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
	private ActionMode mActionMode;
	

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
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener(){

			@Override
			public boolean onItemLongClick(AdapterView<?> adapter, View view,
					int position, long id) {
				if (mActionMode != null) {
		            return false;
		        }

		        // Start the CAB using the ActionMode.Callback defined above
		        mActionMode = startActionMode(mActionModeCallback);
		        view.setSelected(true);
		        return true;
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
			Intent intent = new Intent(ActionConstant.TRADER);
			intent.putExtra("ACTION_TYPE", TRADER_CREATE_REQUEST_CODE);
			startActivityForResult(intent, TRADER_CREATE_REQUEST_CODE);
			break;
		case R.id.mniAbout:
			
			startActivity(new Intent(ActionConstant.ABOUT));
			
			break;
		
		
			
		}
		return super.onOptionsItemSelected(menuItem);
	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

	    // Called when the action mode is created; startActionMode() was called
	    @Override
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        // Inflate a menu resource providing context menu items
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.trader_context_menu, menu);
	        return true;
	    }

	    // Called each time the action mode is shown. Always called after onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    @Override
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	        return false;
	    }

	    // Called when the user selects a contextual menu item
	    @Override
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	        Debug.info(this, "Actionbar is being clicked");
			return false;
	    }

	    // Called when the user exits the action mode
	    @Override
	    public void onDestroyActionMode(ActionMode mode) {
	        mActionMode = null;
	    }
	};
	
}
