package com.xtrade.android.provider;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.xtrade.android.provider.DatabaseContract.Client;

public class ClientTranslator {

	public List<com.xtrade.android.object.Client> translate(Cursor cursor) {
		List<com.xtrade.android.object.Client> clients = new ArrayList<com.xtrade.android.object.Client>();
		while (cursor.moveToNext()) {
			com.xtrade.android.object.Client client = new com.xtrade.android.object.Client(
					cursor.getString(cursor.getColumnIndex(Client.NAME)),
					cursor.getString(cursor.getColumnIndex(Client.ADDRESS)),
					cursor.getString(cursor.getColumnIndex(Client.PHONE)));

			clients.add(client);
		}

		// So this guy will not cause trouble
		cursor.close();
		return clients;
	}

}
