package com.xtrade.android.http;

public abstract class AbstractHttpCaller implements HttpCaller {

	protected static final int BUFFER_LIMIT=1000;
	protected String result;
	
	public String getResult(){
		return result;
	}
	
}
