package com.xtrade.android.http;

import android.content.Context;

/**
 * Factory to create HttpCallers depending on the version it can return 
 * diferent implementation for the HttpCaller
 * 
 * */
public class HttpCallerFactory {

	private static HttpCallerFactory instance;
	
	//access modifier protected ensure that the getInstance method gets called
	protected HttpCallerFactory() { }
	
	public static HttpCallerFactory getInstance() {
		if(instance == null)
			instance = new HttpCallerFactory();
		
		return instance;
	}
	
	public HttpCaller createCaller(Context context) {
		return new HttpCallerApacheImpl(context);
	}
	
}
