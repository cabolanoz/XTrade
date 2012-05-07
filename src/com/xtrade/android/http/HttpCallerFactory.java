package com.xtrade.android.http;

import android.os.Build;

import com.xtrade.android.util.Settings;

/**
 * Factory to create HttpCallers depending on the version it can return 
 * diferent implementation for the HttpCaller
 * 
 * */
public class HttpCallerFactory {

	private static HttpCallerFactory instance;
	
	
	//access modifier protected ensure that the getInstance method gets called
	protected HttpCallerFactory(){}
	
	public static HttpCallerFactory getInstance(){
		if(instance==null)
			instance=new HttpCallerFactory();
		
		return instance;
	}
	
	public HttpCaller createCaller(){
		

		if(Settings.MOCK_APPLICATION)
			return new HttpCallerMockImpl();
		else if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB)
			return new HttpCallerUrlImpl();
		
		return new HttpCallerApacheImpl();
	
	}
	
}
