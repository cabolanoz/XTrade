package com.xtrade.android.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.CursorLoader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.xtrade.android.R;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.provider.DatabaseContract.TraderColumns;
import com.xtrade.android.util.EventConstant;

public class TraderAboutFragment extends SherlockFragment implements EventConstant {

	private TextView tvwTraderName;
	private TextView tvwTraderWebsite;
	private TextView tvwTraderAddress;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.trader_tab_about, container, false);

		// Getting the activity intent
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			long traderId = intent.getLongExtra(TraderColumns.TRADER_ID,-1);
			if (traderId != -1) {
				CursorLoader cursorLoader = new CursorLoader(getActivity().getBaseContext(), DatabaseContract.Trader.buildUri(String.valueOf(traderId)), null, null, null, null);
				Cursor cursor = cursorLoader.loadInBackground();
				if (cursor != null) {
					if (cursor.moveToNext()) {
						tvwTraderName = (TextView) fragmentView.findViewById(R.id.tvwTraderName);
						tvwTraderWebsite = (TextView) fragmentView.findViewById(R.id.tvwTraderWebsite);
						tvwTraderAddress = (TextView) fragmentView.findViewById(R.id.tvwTraderAddress);
						
						tvwTraderName.setText(cursor.getString(cursor.getColumnIndexOrThrow(TraderColumns.NAME)));
						tvwTraderWebsite.setText(parseUrl(cursor.getString(cursor.getColumnIndex(TraderColumns.WEBSITE))));
						tvwTraderAddress.setText(cursor.getString(cursor.getColumnIndexOrThrow(TraderColumns.ADDRESS)));
						
						tvwTraderWebsite.setLinkTextColor(Color.BLUE);
						tvwTraderWebsite.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View view) {
								Intent newintent = new Intent(Intent.ACTION_VIEW, Uri.parse(tvwTraderWebsite.getText().toString()));
								startActivity(newintent);
							}
							
						});
					}
					cursor.close();
				}
			}
		}
		
		return fragmentView;
	}
	
	private String parseUrl(String website) {
		if (!website.startsWith("http://") && !website.startsWith("https://"))
			return "http://" + website;
		return website;
	}
	
}
