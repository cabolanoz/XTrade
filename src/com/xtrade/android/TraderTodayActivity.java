package com.xtrade.android;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.actionbarsherlock.app.SherlockFragment;
import com.xtrade.android.util.EventConstant;

public class TraderTodayActivity extends SherlockFragment implements
		EventConstant {

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view=inflater.inflate(R.layout.today_list,container, false);
		
		return view;
		
	}

}
