package com.xtrade.android;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;

public class ClientDetailFragment extends SherlockFragment {

	private Intent intent;
	
	public ClientDetailFragment(Intent _intent) {
		this.intent = _intent;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.client_tab_detail, container, false);
		
		return view;
	}
	
}
