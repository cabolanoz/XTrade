package com.xtrade.android.http;

import java.net.URL;

public interface HttpCaller {

	boolean call(URL urlResource, RestMethod restMethodType);
	boolean call(URL urlResource, Object... params);
	String getResult();
	
}
