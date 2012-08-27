package com.xtrade.android.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.IntentService;
import android.content.Intent;

import com.xtrade.android.http.HttpCaller;
import com.xtrade.android.http.HttpCallerFactory;
import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.Debug;
import com.xtrade.android.util.LoginParameter;
import com.xtrade.android.util.Settings;

public class XTradeBaseService extends IntentService {

	public XTradeBaseService() {
		super(XTradeBaseService.class.getSimpleName());
	}

	// this method run on a different thread
	@Override
	protected void onHandleIntent(Intent intent) {
		// depending on the action we execute an action currently
		// there will be few action so we can handle it here
		if (intent.getAction().equals(ActionConstant.LOGIN)) {
			String username = intent.getStringExtra(LoginParameter.USERNAME);
			String password = intent.getStringExtra(LoginParameter.PASSWORD);

			boolean success = false;
			if (username.equals(Settings.DEFAULT_USERNAME) && password.equals(Settings.DEFAULT_PASSWORD)) {
				// we resend the login intent with extra parameters
				success = true;
			} else if (intent.getAction().equals(ActionConstant.TRADER)) {
				HttpCaller caller = HttpCallerFactory.getInstance().createCaller();
				try {
					caller.call(new URL("file:///android_asset/client.mock"));
				} catch (MalformedURLException murle) {
					murle.printStackTrace();
				}
			}

			intent.putExtra(LoginParameter.SUCCESS, success);

			sendBroadcast(intent);
		} else if (intent.getAction().equals(ActionConstant.TRADER)) {
			Debug.info(this, "Llega aquí");
			HttpCaller httpCaller = HttpCallerFactory.getInstance().createCaller();
			try {
				httpCaller.call(new URL("http://nomadas.com"));
				Debug.info(this, httpCaller.getResult());
			} catch (MalformedURLException murle) {
				murle.printStackTrace();
			}
		}
	}

}