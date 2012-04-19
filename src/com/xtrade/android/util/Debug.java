package com.xtrade.android.util;

import android.util.Log;

public class Debug {
	
	public static void info(Object obj, String message){
		if(Settings.DEBUG){
			Log.i(obj.getClass().getName(),message);
		}
	}
	
}
