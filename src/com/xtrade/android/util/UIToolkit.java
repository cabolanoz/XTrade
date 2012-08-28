package com.xtrade.android.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;

public class UIToolkit {

	 @TargetApi(Build.VERSION_CODES.HONEYCOMB)
	    public static void setActivatedCompat(View view, boolean activated) {
	        if (hasHoneycomb()) {
	            view.setActivated(activated);
	        }
	    }
	  
	  public static boolean hasHoneycomb() {
	        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	    }
	
}
