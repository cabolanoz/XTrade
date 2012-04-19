package com.xtrade.android;

import android.app.Activity;
import android.os.Bundle;

import com.xtrade.android.R;

public class XTradeActivity extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
}