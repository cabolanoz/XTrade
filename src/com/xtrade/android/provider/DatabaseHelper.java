package com.xtrade.android.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.xtrade.android.provider.DatabaseContract.ClassificationColumns;
import com.xtrade.android.provider.DatabaseContract.ClientColumns;
import com.xtrade.android.provider.DatabaseContract.ContactTypeColumns;
import com.xtrade.android.provider.DatabaseContract.PositionColumns;

public class DatabaseHelper extends SQLiteOpenHelper {

	public static final String database = "xtrade";
	private static final int VERSION = 1;

	/**
	 * Represents all the tables name on the database
	 * 
	 * */
	interface Tables {
		String CONTACT_TYPE = "ContactType";
		String POSITION = "Position";
		String CLASSIFICATION = "Classification";
		String CLIENT = "Client";
	}

	public DatabaseHelper(Context context) {
		super(context, database, null, VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// Client table
		db.execSQL("CREATE TABLE " + Tables.CLIENT + " (" + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + ClientColumns.CLIENT_ID + " TEXT, "
				+ ClientColumns.NAME + " TEXT, "
				+ ClientColumns.PHONE + " TEXT, "
				+ ClientColumns.ADDRESS + " TEXT)");
		
		// Classification table
		db.execSQL("CREATE TABLE " + Tables.CLASSIFICATION + " (" + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + ClassificationColumns.CLASSIFICATION_ID + " TEXT, "
				+ ClassificationColumns.NAME + " TEXT)");
	
		// Position table (Even though I don't know what is this for)
		db.execSQL("CREATE TABLE " + Tables.POSITION + " (" + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + PositionColumns.POSITION_ID + " TEXT, "
				+ PositionColumns.NAME + " TEXT)");
		
		// Contact table
		db.execSQL("CREATE TABLE " + Tables.CONTACT_TYPE + " (" + BaseColumns._ID
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, " + ContactTypeColumns.CONTACT_TYPE_ID + " TEXT, "
				+ ContactTypeColumns.NAME + " TEXT)");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}
}
