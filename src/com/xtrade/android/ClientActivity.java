package com.xtrade.android;

import org.apache.commons.lang.StringUtils;

import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.provider.DatabaseContract.ClientColumns;
import com.xtrade.android.util.Debug;

/**
 * 
 * @author <a href="mailto:cesar20904@gmail.com">César Bolaños</a>
 *
 */
public class ClientActivity extends BaseActivity {

	@Override
	public void onCreate(Bundle savedIntanceState) {
		super.onCreate(savedIntanceState);
		setContentView(R.layout.client);
		
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
					
					Uri clientUri = getContentResolver().insert(DatabaseContract.Client.CONTENT_URI, contentValues);
					Debug.info(this, clientUri.getQuery());							
				}
			}
		});
	}
	
}
