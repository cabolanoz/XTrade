package com.xtrade.android.provider;

import java.util.ArrayList;
import java.util.List;

import android.database.Cursor;
import android.provider.ContactsContract.PhoneLookup;

import com.xtrade.android.object.Contact;

public class ContactTranslator {

	public List<Contact> translate(Cursor _cursor) {
		List<Contact> contacts = new ArrayList<Contact>();
		
		while(_cursor.moveToNext()) {
//			String contactId = _cursor.getString(_cursor.getColumnIndex(ContactsContract.Contacts._ID));
			
			Contact contact = new Contact(
					_cursor.getString(_cursor.getColumnIndex(PhoneLookup.DISPLAY_NAME)),
					"tumadreenbola",
					_cursor.getString(_cursor.getColumnIndex(PhoneLookup.NUMBER)));
			
			contacts.add(contact);
		}
		
		_cursor.close();
		
		return contacts;
	}
	
}
