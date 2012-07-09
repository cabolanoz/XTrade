package com.xtrade.android.provider;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;

import com.xtrade.android.object.Contact;
import com.xtrade.android.provider.DatabaseContract.ContactColumns;

public class ContactTranslator {

	public List<Contact> translate(Cursor _cursor) {
		List<Contact> contacts = new ArrayList<Contact>();
		
		while(_cursor.moveToNext()) {			
			Contact contact = new Contact(
					_cursor.getString(_cursor.getColumnIndex(ContactColumns.NAME)),
					_cursor.getString(_cursor.getColumnIndex(ContactColumns.TYPE)),
					_cursor.getString(_cursor.getColumnIndex(ContactColumns.EMAIL)),
					_cursor.getString(_cursor.getColumnIndex(ContactColumns.PHONE)));
			
			contacts.add(contact);
		}
		
		_cursor.close();
		
		return contacts;
	}
	
}
