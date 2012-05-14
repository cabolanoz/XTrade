package com.xtrade.android.provider;

import java.util.ArrayList;
import java.util.List;

import com.xtrade.android.provider.DatabaseContract.Classification;

import android.database.Cursor;
import android.provider.BaseColumns;

public class ClassificationTranslator {

	public List<com.xtrade.android.object.Classification> translate(Cursor cursor) {
		List<com.xtrade.android.object.Classification> classifications = new ArrayList<com.xtrade.android.object.Classification>();
		
		while(cursor.moveToNext()) {
			com.xtrade.android.object.Classification classification = new com.xtrade.android.object.Classification(
					cursor.getString(cursor.getColumnIndex(BaseColumns._ID)),
					cursor.getString(cursor.getColumnIndex(Classification.NAME)));
			
			classifications.add(classification);
		}
		
		cursor.close();
		
		return classifications;
	}
	
}
