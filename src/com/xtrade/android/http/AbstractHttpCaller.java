package com.xtrade.android.http;

import android.content.Context;

public abstract class AbstractHttpCaller implements HttpCaller {

	protected static final int BUFFER_LIMIT=1000;
	protected String result="";
	protected Context context;
	
	public AbstractHttpCaller(Context context){
		this.context=context;
	}
	
	public String getResult(){
		return result;
	}
	
}
