package com.xtrade.android.provider;

import android.net.Uri;

import com.xtrade.android.provider.DatabaseContract.XTradeBaseColumns;

public  abstract class BaseTable {
		public static String getId(Uri uri) {
			if (uri.getPathSegments().size() >= 2)
				return uri.getPathSegments().get(1);
			return null;
		}
		
	}