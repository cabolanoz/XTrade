package com.xtrade.android.http;

import java.net.URL;
import java.util.Map;



public interface HttpCaller {

	boolean call(URL urlResource, RestOption.Method restMethodType, Map<RestOption.Parameter,String> parameters);
	boolean call(URL urlResource, Object... params);
	String getResult();
	
}
