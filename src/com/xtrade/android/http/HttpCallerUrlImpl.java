package com.xtrade.android.http;

import java.net.URL;


/**
 * This is a default iplementation for HttpCaller 
 * it uses the Native Http Java mecanism to deal with request
 * this implementation should be used for HoneyComb or higher version
 * since it will be supported and optimize for the next platform
 * 
 * */
public class HttpCallerUrlImpl extends AbstractHttpCaller{

	@Override
	public boolean call(URL urlResource) {
		return false;
	}

	@Override
	public boolean call(URL urlResource, Object... params) {
		return false;
	}

}
