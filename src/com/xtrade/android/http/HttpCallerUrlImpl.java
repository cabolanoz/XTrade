package com.xtrade.android.http;

import java.net.URL;
import java.util.Map;

import android.content.Context;

import com.xtrade.android.http.RestOption.Method;
import com.xtrade.android.http.RestOption.Parameter;


/**
 * This is a default iplementation for HttpCaller 
 * it uses the Native Http Java mecanism to deal with request
 * this implementation should be used for HoneyComb or higher version
 * since it will be supported and optimize for the next platform
 * 
 * */
public class HttpCallerUrlImpl extends AbstractHttpCaller{


	public HttpCallerUrlImpl(Context context) {
		super(context);
	}

	@Override
	public boolean call(URL urlResource, Object... params) {
		return false;
	}

	@Override
	public boolean call(URL urlResource, Method restMethodType, Map<Parameter, String> parameters) {
		// TODO Auto-generated method stub
		return false;
	}

}
