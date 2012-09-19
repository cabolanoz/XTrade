package com.xtrade.android.http;

import java.net.URL;

import org.apache.http.entity.StringEntity;

public interface HttpCaller {

	boolean call(URL urlResource, RestMethod restMethodType, StringEntity stringEntity);
	boolean call(URL urlResource, Object... params);
	String getResult();
	
}
