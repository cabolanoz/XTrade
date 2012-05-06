package com.xtrade.android.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public  abstract class BaseTable implements BaseColumns {
		
		public static String getId(Uri uri) {
			if (uri.getPathSegments().size() >= 2)
				return uri.getPathSegments().get(1);
			return null;
		}
		
	}