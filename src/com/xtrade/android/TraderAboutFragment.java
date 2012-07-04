package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.xtrade.android.provider.DatabaseContract.Trader;
import com.xtrade.android.provider.DatabaseContract.TraderColumns;
import com.xtrade.android.util.EventConstant;

public class TraderAboutFragment extends SherlockFragment implements EventConstant {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.trader_tab_about, container, false);
		
		// Getting the activity intent
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			long traderId = intent.getLongExtra(Trader.TRADER_ID, -1);
			if (traderId != -1) {
				CursorLoader cursorLoader = new CursorLoader(getActivity().getBaseContext(), Trader.buildUri(String.valueOf(traderId)), null, null, null, null);
				Cursor cursor = cursorLoader.loadInBackground();
				if (cursor != null) {
					TextView tvwTraderName = (TextView) fragmentView.findViewById(R.id.tvwTraderName);
					TextView tvwTraderWebsite = (TextView) fragmentView.findViewById(R.id.tvwTraderWebsite);
					EditText etxTraderAddress = (EditText) fragmentView.findViewById(R.id.etxTraderAddress);
					
					tvwTraderName.setText(cursor.getString(cursor.getColumnIndexOrThrow(TraderColumns.NAME)));
					tvwTraderWebsite.setText(cursor.getString(cursor.getColumnIndex(TraderColumns.WEBSITE)));
					etxTraderAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow(TraderColumns.ADDRESS)));
				}
			}
		}
		
		return fragmentView;
	}
	
}
