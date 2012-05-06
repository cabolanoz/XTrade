package com.xtrade.android;

import org.apache.commons.lang.StringUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.provider.DatabaseContract.Client;
import com.xtrade.android.provider.DatabaseContract.ClientColumns;
import com.xtrade.android.util.Settings;

public class ClientActivity extends BaseActivity {

	private final int CREATE_REQUEST_CODE = 100;
	private final int UPDATE_REQUEST_CODE = 101;
	
	private long clientId;
	private EditText txtClientName;
	private EditText txtClientPhone;
	private EditText txtClientAddress;
	
	@Override
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		setContentView(R.layout.client);
		
		txtClientName=(EditText) findViewById(R.id.txtClientName);
		txtClientPhone=((EditText) findViewById(R.id.txtClientPhone));
		txtClientAddress=(EditText) findViewById(R.id.txtClientAddress);
		
		if(Settings.DEBUG){
			txtClientName.setText("Loren ipsum");
			txtClientPhone.setText("222222");
			txtClientAddress.setText("Aenean lacinia bibendum nulla sed consectetur.");
		}
		
		Intent intent = getIntent();
		final int extra = intent.getIntExtra("ACTION_TYPE", -1);
		if (extra == UPDATE_REQUEST_CODE)
			if (extra != -1 && intent.getLongExtra(DatabaseContract.ClientColumns.CLIENT_ID, -1) >= 0) {
				clientId = intent.getLongExtra(DatabaseContract.ClientColumns.CLIENT_ID, -1);
				
				CursorLoader cursorLoader = new CursorLoader(getBaseContext(), DatabaseContract.Client.buildUri(String.valueOf(clientId)), null, null, null, null);
				Cursor cursor = cursorLoader.loadInBackground();
				if (cursor != null)
					if (cursor.moveToNext()) {
						txtClientName.setText(cursor.getColumnIndexOrThrow(DatabaseContract.ClientColumns.NAME));
						txtClientPhone.setText(cursor.getColumnIndexOrThrow(DatabaseContract.ClientColumns.PHONE));
						txtClientAddress.setText(cursor.getColumnIndexOrThrow(DatabaseContract.ClientColumns.ADDRESS));
					}
			}
		
		//TODO: handle the lifecycle when orientation changes to save the values
		Button btnAddClient = (Button) findViewById(R.id.btnAddClient);
		btnAddClient.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String clientName = txtClientName.getText().toString();
				String clientPhone = txtClientPhone.getText().toString();
				String clientAddress = txtClientAddress.getText().toString();
				
				if (!StringUtils.isEmpty(clientName) && !StringUtils.isEmpty(clientPhone) && !StringUtils.isEmpty(clientAddress)) {
					ContentValues contentValues = new ContentValues();
					
					contentValues.put(ClientColumns.NAME, clientName);
					contentValues.put(ClientColumns.PHONE, clientPhone);
					contentValues.put(ClientColumns.ADDRESS, clientAddress);
					
					Uri clientUri = null;
					
					int result=RESULT_CANCELED;
					
					if (extra == CREATE_REQUEST_CODE){
						clientUri = getContentResolver().insert(DatabaseContract.Client.CONTENT_URI, contentValues);
						result=clientUri==null?RESULT_CANCELED:RESULT_OK;
					}else if (extra == UPDATE_REQUEST_CODE){
						
						clientUri = Client.buildUri(String.valueOf(clientId));
						result=getContentResolver().update(clientUri, contentValues, null, null)==0?RESULT_CANCELED:RESULT_OK;
					    
					}
					
					setResult(result);
					finish();	
				}
			}
		});
	}
	
}
