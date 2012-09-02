package com.xtrade.android.service;

import java.lang.reflect.Type;
import java.util.Collection;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xtrade.android.object.Trader;
import com.xtrade.android.provider.DatabaseContract.TraderEntity;
import com.xtrade.android.util.Debug;

public class TraderProcessor extends ProcessorBase{
	
	public TraderProcessor(Context context) {
		super(context);
	}

	public void process(String jsonTrader){
		Debug.info(jsonTrader);
		Gson gson=new Gson();
		Type collectionType = new TypeToken<Collection<Trader>>(){}.getType();
		Collection<Trader> traders=(Collection<Trader>)gson.fromJson(jsonTrader, collectionType);
		
		
		ContentResolver contentResolver=context.getContentResolver();
		for(Trader trader: traders){
			//TODO: bulk this in a transactoin
			ContentValues values =new ContentValues();
			values.put(TraderEntity.ADDRESS, trader.address);
			values.put(TraderEntity.NAME, trader.name);
			//values.put(TraderEntity.LATITUDE, trader.location.lat);
			//values.put(TraderEntity.LONGITUDE, trader.location.lon);
			values.put(TraderEntity.TRADER_ID, trader.id);
			values.put(TraderEntity.WEBSITE, trader.website);
			values.put(TraderEntity.ISFAVORITE, trader.isFavorite);
			values.put(TraderEntity.ISFAVORITE, trader.isFavorite);
			
			contentResolver.insert(TraderEntity.CONTENT_URI, values);
			
			Debug.info(trader.toString());
		}
	}
	
}
