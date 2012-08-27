package com.xtrade.android.provider;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.xtrade.android.provider.DatabaseContract.Trader;

public class TraderTranslator {

	public List<com.xtrade.android.object.Trader> translate(Cursor cursor) {
		List<com.xtrade.android.object.Trader> traders = new ArrayList<com.xtrade.android.object.Trader>();

		while (cursor.moveToNext()) {
			com.xtrade.android.object.Trader trader = new com.xtrade.android.object.Trader(
					cursor.getString(cursor.getColumnIndex(BaseColumns._ID)),
					cursor.getString(cursor.getColumnIndex(Trader.NAME)),
					cursor.getString(cursor.getColumnIndex(Trader.WEBSITE)),
					cursor.getString(cursor.getColumnIndex(Trader.ADDRESS)),
					cursor.getString(cursor.getColumnIndex(Trader.POSX)),
					cursor.getString(cursor.getColumnIndex(Trader.POSY)),
					cursor.getInt(cursor.getColumnIndex(Trader.ISFAVORITE)) == 1);

			traders.add(trader);
		}

		// So this guy will not cause trouble
		cursor.close();

		return traders;
	}

}
