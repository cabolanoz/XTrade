package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.xtrade.android.adapter.ContactAdapter;
import com.xtrade.android.provider.ContactTranslator;
import com.xtrade.android.provider.DatabaseContract.Contact;
import com.xtrade.android.provider.DatabaseContract.ContactColumns;
import com.xtrade.android.provider.DatabaseContract.TraderColumns;
import com.xtrade.android.util.EventConstant;

public class TraderContactFragment extends SherlockFragment implements EventConstant {

	private BaseAdapter adapter;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.trader_tab_list_contact, container, false);
		
		// Getting the activity intent
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			String traderId = intent.getStringExtra(TraderColumns.TRADER_ID);
			if (traderId != null && !"".equals(traderId)) {
				Cursor cursor = getActivity().getContentResolver().query(Contact.CONTENT_URI, null, ContactColumns.TRADER_ID + " = '" + traderId + "'", null, null);
				
				adapter = new ContactAdapter(getActivity(), new ContactTranslator().translate(cursor));

				ListView listView = (ListView) fragmentView.findViewById(R.id.lvwContact);
				listView.setAdapter(adapter);
			}
		}
		
		return fragmentView;
	}
	
}
