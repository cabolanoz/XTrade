package com.xtrade.android.service;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.entity.StringEntity;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.IntentService;
import android.content.Intent;

import com.xtrade.android.http.HttpCaller;
import com.xtrade.android.http.HttpCallerFactory;
import com.xtrade.android.http.RestMethod;
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
		if (intent.getAction().equals(ActionConstant.LOGIN)) {
			boolean success = false;
			
			String username = intent.getStringExtra(LoginParameter.USERNAME);
			String password = intent.getStringExtra(LoginParameter.PASSWORD);

			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("username", username);
				jsonObject.put("password", password);
			} catch (JSONException jsone) {
				jsone.printStackTrace();
			}
			
			StringEntity entity = null; 
			try {
				entity = new StringEntity(jsonObject.toString());
				entity.setContentType("application/json");
			} catch (UnsupportedEncodingException uee) {
				uee.printStackTrace();
			}
			
			HttpCaller httpCaller = HttpCallerFactory.getInstance().createCaller();
			try {
				boolean result = httpCaller.call(new URL(Settings.getServerURL() + "login/"), RestMethod.POST, entity);
				if (result) {
					try {
						JSONObject json = new JSONObject(httpCaller.getResult());
						success = json.getBoolean("success");
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			} catch (MalformedURLException murle) {
				murle.printStackTrace();
			}
			
			intent.putExtra(LoginParameter.SUCCESS, success);
			sendBroadcast(intent);
		} else if (intent.getAction().equals(ActionConstant.REQUEST_DATA)) {
			HttpCaller httpCaller = HttpCallerFactory.getInstance().createCaller();
			try {
				boolean result = httpCaller.call(new URL(Settings.getServerURL() + "traders/"), RestMethod.GET, null);
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