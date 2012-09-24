package com.xtrade.android.service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.google.gson.Gson;
import com.xtrade.android.http.HttpCaller;
import com.xtrade.android.http.HttpCallerFactory;
import com.xtrade.android.http.RestOption.Method;
import com.xtrade.android.http.RestOption.Parameter;
import com.xtrade.android.object.User;
import com.xtrade.android.util.ActionConstant;
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
		HttpCaller httpCaller = HttpCallerFactory.getInstance().createCaller(this);
		if (intent.getAction().equals(ActionConstant.LOGIN)) {
			

			String username = intent.getStringExtra(LoginParameter.USERNAME);
			String password = intent.getStringExtra(LoginParameter.PASSWORD);

			User user = new User(username, password);
			// TODO: create someone that will create parameter for us
			Map<Parameter, String> parameters = new HashMap<Parameter, String>();
			Gson gson = new Gson();
			parameters.put(Parameter.BODY, gson.toJson(user));

			
			try {

				boolean result = httpCaller.call(new URL(Settings.getServerURL() + "login/"), Method.POST, parameters);
				if (result) {

					if (!StringUtils.isEmpty(httpCaller.getResult())) {
						SharedPreferences xTradeSettings = getSharedPreferences(Settings.SHARED_PREFERENCES, MODE_PRIVATE);
						Editor editor = xTradeSettings.edit();
						editor.putBoolean(Settings.LOGGED_PREF, true);
						editor.putString(Settings.COOKIE_PREF, httpCaller.getResult());
						intent.putExtra(LoginParameter.SUCCESS, true);
					}

				}
			} catch (MalformedURLException murle) {
				murle.printStackTrace();
			}

			
			sendBroadcast(intent);
		} else if (intent.getAction().equals(ActionConstant.REQUEST_DATA)) {
			
			try {
				boolean result = httpCaller.call(new URL(Settings.getServerURL() + "traders/"), Method.GET);
				if (result) {
					
					ProcessorBase processor = ProcessorFactory.getProcessor(intent.getAction(), this);
					processor.process(httpCaller.getResult());
				}
			} catch (MalformedURLException murle) {
				murle.printStackTrace();
			}
		}
	}

}