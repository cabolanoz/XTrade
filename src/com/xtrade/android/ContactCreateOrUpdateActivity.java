package com.xtrade.android;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.provider.DatabaseContract.ContactTypeColumns;
import com.xtrade.android.util.Debug;
import com.xtrade.android.util.EventConstant;

public class ContactCreateOrUpdateActivity extends BaseActivity implements EventConstant {

	private SimpleCursorAdapter adapter;
	
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		setContentView(R.layout.contact);
		
		Cursor cursor = getContentResolver().query(DatabaseContract.ContactType.CONTENT_URI, 
				new String[] {BaseColumns._ID, ContactTypeColumns.CONTACT_TYPE_ID, ContactTypeColumns.NAME},
				null, 
				null,
				null);

		Debug.info(this, "Valores " + cursor.getCount());
		
		adapter = new SimpleCursorAdapter(this, R.layout.contact_type, cursor, new String[] {ContactTypeColumns.NAME}, new int[] {R.id.tvwContactTypeName}, 0);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner spnContactType = (Spinner) findViewById(R.id.spnContactType);
		spnContactType.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu _menu) {
		MenuInflater menuInflater = getSupportMenuInflater();
		menuInflater.inflate(R.menu.contact_menu, _menu);
		return true;
	}
	
	public boolean onOptionsItemSelected(MenuItem _menuItem) {
		switch (_menuItem.getItemId()) {
		case R.id.mniSaveContact:
			break;
		}
		
		return super.onOptionsItemSelected(_menuItem);
	}
	
}
