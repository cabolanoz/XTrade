package com.xtrade.android;

import org.apache.commons.lang.StringUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.provider.DatabaseContract.ClientColumns;

public class ClientActivity extends BaseActivity {

	private final int CREATE_REQUEST_CODE = 100;
	private final int UPDATE_REQUEST_CODE = 101;
	
	@Override
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		setContentView(R.layout.client);
		//TODO: handle the lifecycle when orientation changed to save the values
		Button btnAddClient = (Button) findViewById(R.id.btnAddClient);
		btnAddClient.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				String clientName = ((EditText) findViewById(R.id.txtClientName)).getText().toString();
				String clientPhone = ((EditText) findViewById(R.id.txtClientPhone)).getText().toString();
				String clientAddress = ((EditText) findViewById(R.id.txtClientAddress)).getText().toString();
				
				if (!StringUtils.isEmpty(clientName) && !StringUtils.isEmpty(clientPhone) && !StringUtils.isEmpty(clientAddress)) {
					ContentValues contentValues = new ContentValues();
					
					contentValues.put(ClientColumns.NAME, clientName);
					contentValues.put(ClientColumns.PHONE, clientPhone);
					contentValues.put(ClientColumns.ADDRESS, clientAddress);
					
					Intent intent = getIntent();
					int extra = intent.getIntExtra("ACTION_TYPE", -1);
					if (extra == -1)
						setResult(RESULT_CANCELED);
					
					Uri clientUri = null;
					if (extra == CREATE_REQUEST_CODE)
						clientUri = getContentResolver().insert(DatabaseContract.Client.CONTENT_URI, contentValues);
					else if (extra == UPDATE_REQUEST_CODE)
						clientUri = null;
					
					if (clientUri != null)
						setResult(RESULT_OK);
					else
						setResult(RESULT_CANCELED);
					
					finish();	
				}
			}
		});
	}
	
}
