package com.xtrade.android.http;

import java.net.URL;

public interface HttpCaller {

	boolean call(URL urlResource);
	boolean call(URL urlResource, Object... params);
	String getResult();
	
}
