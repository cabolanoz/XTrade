package com.xtrade.android;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.xtrade.android.adapter.ContactAdapter;
import com.xtrade.android.provider.ContactTranslator;
import com.xtrade.android.util.EventConstant;

public class TraderContactFragment extends SherlockFragment implements EventConstant {

	private BaseAdapter adapter;
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.trader_tab_contact, container, false);
		
		Cursor cursor = getActivity().getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		
		adapter = new ContactAdapter(getActivity(), new ContactTranslator().translate(cursor));
		
		ListView listView = (ListView) fragmentView.findViewById(R.id.lvwContact);
		listView.setAdapter(adapter);
		
		return fragmentView;
	}
	
}
