package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockFragment;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.util.Settings;

public class ClientGeneralFragment extends SherlockFragment {

	private final int CREATE_REQUEST_CODE = 100;
	private final int UPDATE_REQUEST_CODE = 101;
	
	
	private EditText txtClientName;
	private EditText txtClientPhone;
	private EditText txtClientAddress;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.client_tab_general, container, false);
		
		Intent intent=getActivity().getIntent();
		
		// We get the ACTION_TYPE extra which tells us what operation we must perform (Save or Update)
		int extra = intent.getIntExtra("ACTION_TYPE", -1);
		
		txtClientName = (EditText) fragmentView.findViewById(R.id.txtClientName);
		txtClientPhone = (EditText) fragmentView.findViewById(R.id.txtClientPhone);
		txtClientAddress = (EditText) fragmentView.findViewById(R.id.txtClientAddress);

		// Setting default values while we're on developer mode
		if (Settings.DEBUG && extra == CREATE_REQUEST_CODE) {
			txtClientName.setText("Loren ipsum");
			txtClientPhone.setText("222222");
			txtClientAddress.setText("Aenean lacinia bibendum nulla sed consectetur.");
		}

		// If the ACTION_TYPE extra is for Updating the client info, we get the client date from database, then we set it on corresponding EditTexts
		if (extra == UPDATE_REQUEST_CODE)
			if (extra != -1 && intent.getStringExtra(DatabaseContract.ClientColumns.CLIENT_ID) != null && !"".equals(intent.getStringExtra(DatabaseContract.ClientColumns.CLIENT_ID))) {
				String clientId = intent.getStringExtra(DatabaseContract.ClientColumns.CLIENT_ID);

				CursorLoader cursorLoader = new CursorLoader(getActivity().getBaseContext(), DatabaseContract.Client.buildUri(clientId), null, null, null, null);
				Cursor cursor = cursorLoader.loadInBackground();
				if (cursor != null) {
					if (cursor.moveToNext()) {
						txtClientName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClientColumns.NAME)));
						txtClientPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClientColumns.PHONE)));
						txtClientAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.ClientColumns.ADDRESS)));
					}
					cursor.close();
				}
			}
		
		return fragmentView;
	}
	
}
