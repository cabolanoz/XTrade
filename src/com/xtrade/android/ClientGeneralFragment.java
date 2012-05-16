package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.actionbarsherlock.app.SherlockFragment;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.util.Debug;
import com.xtrade.android.util.Settings;

public class ClientGeneralFragment extends SherlockFragment {

	private static final int CREATE_REQUEST_CODE = 100;
	private static final int UPDATE_REQUEST_CODE = 101;
	public static final int GALLERY_REQUEST = 102;
	public static final int CAMERA_REQUEST = 103;
	
	private ImageButton txtClientPhoto;
	private EditText txtClientName;
	private EditText txtClientPhone;
	private EditText txtClientAddress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.client_tab_general, container, false);
		
		Intent intent=getActivity().getIntent();
		
		// We get the ACTION_TYPE extra which tells us what operation we must perform (Save or Update)
		int extra = intent.getIntExtra("ACTION_TYPE", -1);
		
		txtClientPhoto = (ImageButton) fragmentView.findViewById(R.id.txtClientPhoto);
		txtClientPhoto.setClickable(true);
		txtClientPhoto.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				Fragment f = getFragmentManager().findFragmentByTag("picture_dialog");
				if (f != null)
					ft.remove(f);
				ft.addToBackStack(null);
				
				DialogFragment dialogFragment = PictureDialog.newInstance(ClientGeneralFragment.this);
				dialogFragment.show(ft, "picture_dialog");
			}
		});
		
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
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		if (resultCode == FragmentActivity.RESULT_OK){
			if (txtClientPhoto != null){
				
				if (requestCode == GALLERY_REQUEST)
					txtClientPhoto.setImageURI(data.getData());
				else if (requestCode == CAMERA_REQUEST){
					Debug.info(this, "Ok this dialog "+data.getExtras());	
					//txtClientPhoto.setImageBitmap((Bitmap) data.getExtras().get("data"));
				}
			}
			
		}
	}
	 

	
}
