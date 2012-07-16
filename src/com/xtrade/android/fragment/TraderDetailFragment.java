package com.xtrade.android.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.xtrade.android.R;
import com.xtrade.android.R.layout;
import com.xtrade.android.util.EventConstant;

public class TraderDetailFragment extends SherlockFragment implements EventConstant {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.trader_tab_detail, container, false);
		
		// We obtain the intent which calls the activity
//		Intent intent = getActivity().getIntent();
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
