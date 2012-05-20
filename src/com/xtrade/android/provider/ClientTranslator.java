package com.xtrade.android.provider;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.provider.BaseColumns;

import com.xtrade.android.provider.DatabaseContract.Client;

public class ClientTranslator {

	public List<com.xtrade.android.object.Client> translate(Cursor cursor) {
		List<com.xtrade.android.object.Client> clients = new ArrayList<com.xtrade.android.object.Client>();
		
		while (cursor.moveToNext()) {
			com.xtrade.android.object.Client client = new com.xtrade.android.object.Client(
					cursor.getString(cursor.getColumnIndex(BaseColumns._ID)),
					cursor.getString(cursor.getColumnIndex(Client.DESCRIPTION)),
					cursor.getString(cursor.getColumnIndex(Client.WEBSITE)),
					cursor.getString(cursor.getColumnIndex(Client.LOCATION)),
					cursor.getString(cursor.getColumnIndex(Client.NOTE)));

			clients.add(client);
		}

		// So this guy will not cause trouble
		cursor.close();
		
		return clients;
	}

}
