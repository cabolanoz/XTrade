package com.xtrade.android;

import android.os.Bundle;
import android.webkit.WebView;


public class AboutActivity extends BaseActivity{

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		
		WebView webView = (WebView) findViewById(R.id.wvwAbout);
		webView.loadUrl("file:///android_asset/about.html");
	}
	
}
