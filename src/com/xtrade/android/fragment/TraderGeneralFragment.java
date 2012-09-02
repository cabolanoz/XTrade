package com.xtrade.android.fragment;

import android.content.Intent;
import android.database.Cursor;
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
import com.xtrade.android.PictureDialog;
import com.xtrade.android.R;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.util.Debug;
import com.xtrade.android.util.EventConstant;
import com.xtrade.android.util.Settings;

public class TraderGeneralFragment extends SherlockFragment implements EventConstant {
	
	private ImageButton txtTraderPhoto;
	private EditText txtTraderName;
	private EditText txtTraderAddress;
	private EditText txtTraderWebsite;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.trader_tab_general, container, false);
		
		// We obtain the intent which calls the activity
		Intent intent = getActivity().getIntent();
		
		// We get the ACTION_TYPE extra which tells us what operation we must perform (Save or Update)
		int extra = intent.getIntExtra("ACTION_TYPE", -1);
		
		txtTraderPhoto = (ImageButton) fragmentView.findViewById(R.id.ivwTraderPhoto);
		txtTraderPhoto.setClickable(true);
		txtTraderPhoto.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				FragmentTransaction ft = getFragmentManager().beginTransaction();
				Fragment f = getFragmentManager().findFragmentByTag("picture_dialog");
				if (f != null)
					ft.remove(f);
				ft.addToBackStack(null);
				
				DialogFragment dialogFragment = PictureDialog.newInstance(TraderGeneralFragment.this);
				dialogFragment.show(ft, "picture_dialog");
			}
		});	
		
		txtTraderName = (EditText) fragmentView.findViewById(R.id.etxTraderName);
		txtTraderWebsite = (EditText) fragmentView.findViewById(R.id.etxTraderWebsite);
		txtTraderAddress = (EditText) fragmentView.findViewById(R.id.etxTraderAddress);

		// Setting default values while we're on developer mode
		if (Settings.DEBUG && extra == TRADER_CREATE_REQUEST_CODE) {
			txtTraderName.setText("Coca-Cola");
			txtTraderWebsite.setText(parseUrl("www.coca-cola.com"));
			txtTraderAddress.setText("Aenean lacinia bibendum nulla sed consectetur");
		}

		// If the ACTION_TYPE extra is for Updating the client info, we get the client date from database, then we set it on corresponding EditTexts
		if (extra == TRADER_UPDATE_REQUEST_CODE)
			if (extra != -1 && intent.getStringExtra(DatabaseContract.TraderColumns.TRADER_ID) != null && !"".equals(intent.getStringExtra(DatabaseContract.TraderColumns.TRADER_ID))) {
				String traderId = intent.getStringExtra(DatabaseContract.TraderColumns.TRADER_ID);

				CursorLoader cursorLoader = new CursorLoader(getActivity().getBaseContext(), DatabaseContract.TraderEntity.buildUri(traderId), null, null, null, null);
				Cursor cursor = cursorLoader.loadInBackground();
				if (cursor != null) {
					if (cursor.moveToNext()) {
						txtTraderName.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TraderColumns.NAME)));
						txtTraderWebsite.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TraderColumns.WEBSITE)));
						txtTraderAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.TraderColumns.ADDRESS)));
					}
					cursor.close();
				}
			}
		
		return fragmentView;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == FragmentActivity.RESULT_OK) {
			if (txtTraderPhoto != null) {
				if (requestCode == TRADER_PHOTO_GALLERY_REQUEST)
					txtTraderPhoto.setImageURI(data.getData());
				else if (requestCode == TRADER_PHOTO_CAMERA_REQUEST) {
					Debug.info(this, "Ok this dialog " + data.getExtras());	
					//txtClientPhoto.setImageBitmap((Bitmap) data.getExtras().get("data"));
				}
			}
		}
	}
	
	private String parseUrl(String website) {
		if (!website.startsWith("http://") && !website.startsWith("https://"))
			return "http://" + website;
		return website;
	}
	
}
