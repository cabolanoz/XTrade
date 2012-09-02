package com.xtrade.android.service;

import android.content.Context;

public abstract class ProcessorBase {

	protected Context context;
	
	public ProcessorBase(Context context){
		this.context=context;
	}
	
	public abstract void process(String json);
	
}
