package com.xtrade.android;

import android.os.Bundle;

import com.google.android.maps.MapActivity;

public class ClientMapActivity extends MapActivity{

	@Override
	public void onCreate(Bundle savedIntanceState){
		super.onCreate(savedIntanceState);
		setContentView(R.layout.client_map);
	}

	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

	
}
