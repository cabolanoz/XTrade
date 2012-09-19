package com.xtrade.android.service;

import android.content.Context;

import com.xtrade.android.util.ActionConstant;

public class ProcessorFactory {

	public static ProcessorBase getProcessor(String action, Context context) {
		if(action.equals(ActionConstant.REQUEST_DATA))
			return new TraderProcessor(context);
		
		return null;
	}

}
