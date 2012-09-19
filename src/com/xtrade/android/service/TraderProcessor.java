package com.xtrade.android.service;

import java.lang.reflect.Type;
import java.util.Collection;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtrade.android.object.Contact;
import com.xtrade.android.object.Trader;
import com.xtrade.android.provider.DatabaseContract.ContactColumns;
import com.xtrade.android.provider.DatabaseContract.ContactEntity;
import com.xtrade.android.provider.DatabaseContract.TraderEntity;
import com.xtrade.android.util.Debug;

public class TraderProcessor extends ProcessorBase {
	
//	private GsonBuilder gson=new GsonBuilder();
	
	public TraderProcessor(Context context) {
		super(context);
	}

	public void process(String jsonTrader){
		Debug.info(jsonTrader);
		
		Gson gson = new Gson();
		
		Type collectionType = new TypeToken<Collection<Trader>>(){}.getType();
		Collection<Trader> traders = (Collection<Trader>) gson.fromJson(jsonTrader, collectionType);
		
		ContentResolver contentResolver = context.getContentResolver();
		for (Trader trader: traders) {
			//TODO: bulk this in a transaction
			ContentValues values = new ContentValues();
			values.put(TraderEntity.ADDRESS, trader.address);
			values.put(TraderEntity.NAME, trader.name);
			values.put(TraderEntity.LATITUDE, trader.location.lat);
			values.put(TraderEntity.LONGITUDE, trader.location.lon);
			values.put(TraderEntity.TRADER_ID, trader.id);
			values.put(TraderEntity.WEBSITE, trader.website);
			values.put(TraderEntity.ISFAVORITE, trader.isFavorite);
			
			Uri traderUri = contentResolver.insert(TraderEntity.CONTENT_URI, values);
			
			for (Contact contact : trader.contacts){
				ContentValues contentValues= new ContentValues();
				contentValues.put(ContactColumns.FIRST_NAME, contact.firstName);
				contentValues.put(ContactColumns.LAST_NAME, contact.lastName);
				contentValues.put(ContactColumns.EMAIL, contact.email);
				contentValues.put(ContactColumns.PHONE, contact.phone);
				contentValues.put(ContactColumns.TRADER_ID, TraderEntity.getId(traderUri));
				Uri contactUri = contentResolver.insert(ContactEntity.CONTENT_URI, contentValues);
				Debug.info("Contacts "+contactUri);
			}
		}
	}
	
}
