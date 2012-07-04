package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.provider.DatabaseContract.TraderColumns;
import com.xtrade.android.util.EventConstant;

public class TraderAboutFragment extends SherlockFragment implements EventConstant {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.trader_tab_about, container, false);

		// Getting the activity intent
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			String traderId = intent.getStringExtra(TraderColumns.TRADER_ID);
			if (traderId != null && !"".equals(traderId)) {
				CursorLoader cursorLoader = new CursorLoader(getActivity().getBaseContext(), DatabaseContract.Trader.buildUri(traderId), null, null, null, null);
				Cursor cursor = cursorLoader.loadInBackground();
				if (cursor != null) {
					if (cursor.moveToNext()) {
						TextView tvwTraderName = (TextView) fragmentView.findViewById(R.id.tvwTraderName);
						TextView tvwTraderWebsite = (TextView) fragmentView.findViewById(R.id.tvwTraderWebsite);
						TextView tvwTraderAddress = (TextView) fragmentView.findViewById(R.id.tvwTraderAddress);
						
						tvwTraderName.setText(cursor.getString(cursor.getColumnIndexOrThrow(TraderColumns.NAME)));
						tvwTraderWebsite.setText(cursor.getString(cursor.getColumnIndex(TraderColumns.WEBSITE)));
						tvwTraderAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow(TraderColumns.ADDRESS)));
					}
					cursor.close();
				}
			}
		}
		
		return fragmentView;
	}
	
}
