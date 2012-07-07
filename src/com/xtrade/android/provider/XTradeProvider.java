package com.xtrade.android.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

import com.xtrade.android.provider.DatabaseContract.Classification;
import com.xtrade.android.provider.DatabaseContract.Contact;
import com.xtrade.android.provider.DatabaseContract.Position;
import com.xtrade.android.provider.DatabaseContract.Trader;

public class XTradeProvider extends ContentProvider {

	public static final int TRADER = 1000;
	public static final int TRADER_ID = 1001;
	public static final int CONTACT = 1002;
	public static final int CONTACT_ID = 1003;
	public static final int CLASSIFICATION = 1004;
	public static final int CLASSIFICATION_ID = 1005;
	public static final int POSITION = 1006;
	public static final int POSITION_ID = 1007;

//	private final static int LIMIT_CALLS = 10;

	private DatabaseHelper databaseHelper;

	private static final UriMatcher uriMatcher = buildMatcher();

	public static UriMatcher buildMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String CONTENT_AUTHORITY = DatabaseContract.CONTENT_AUTHORITY;

		matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_TRADER, TRADER);
		matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_TRADER + "/*", TRADER_ID);
		matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_CONTACT, CONTACT);
		matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_CONTACT + "/*", CONTACT_ID);
		matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_CLASSIFICATION, CLASSIFICATION);
		matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_CLASSIFICATION + "/*", CLASSIFICATION_ID);
		matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_POSITION, POSITION);
		matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_POSITION + "/*", POSITION_ID);

		return matcher;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		String finalWhere = null;
		String table = null;
		switch (uriMatcher.match(uri)) {
		case POSITION:
			table = DatabaseHelper.Tables.POSITION;
			break;
		case POSITION_ID:
			table = DatabaseHelper.Tables.POSITION;
			finalWhere = BaseColumns._ID + " = " + Position.getId(uri);
			if (selection != null)
				finalWhere = finalWhere + " AND " + selection;
			break;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}

		int count = db.delete(table, finalWhere, selectionArgs);

		getContext().getContentResolver().notifyChange(uri, null);

		return count;
	}

	@Override
	public String getType(Uri uri) {
		final int match = uriMatcher.match(uri);

		switch (match) {
		case TRADER:
			return Trader.CONTENT_TYPE;
		case TRADER_ID:
			return Trader.CONTENT_ITEM_TYPE;
		case CONTACT:
			return Contact.CONTENT_TYPE;
		case CONTACT_ID:
			return Contact.CONTENT_ITEM_TYPE;
		case CLASSIFICATION:
			return Classification.CONTENT_TYPE;
		case CLASSIFICATION_ID:
			return Classification.CONTENT_ITEM_TYPE;
		case POSITION:
			return Position.CONTENT_TYPE;
		case POSITION_ID:
			return Position.CONTENT_ITEM_TYPE;
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		final SQLiteDatabase db = databaseHelper.getWritableDatabase();
		final int match = uriMatcher.match(uri);
		switch (match) {
		case TRADER:
			db.insertOrThrow(DatabaseHelper.Tables.TRADER, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Trader.buildUri(values.getAsString(BaseColumns._ID));
		case CONTACT:
			db.insertOrThrow(DatabaseHelper.Tables.CONTACT, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Contact.buildUri(values.getAsString(BaseColumns._ID));
		case CLASSIFICATION:
			db.insertOrThrow(DatabaseHelper.Tables.CLASSIFICATION, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Classification.buildUri(values.getAsString(BaseColumns._ID));
		case POSITION:
			db.insertOrThrow(DatabaseHelper.Tables.POSITION, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Position.buildUri(values.getAsString(BaseColumns._ID));
		default:
			throw new UnsupportedOperationException("Unknown uri: " + uri);
		}
	}

	@Override
	public boolean onCreate() {
		databaseHelper = new DatabaseHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		final int match = uriMatcher.match(uri);
		final SQLiteDatabase db = databaseHelper.getWritableDatabase();

		SQLiteQueryBuilder builder = buildExpandedSelection(uri, match);

		sortOrder = buildSortOrder(match, sortOrder);
		
		Cursor c = builder.query(db, projection, selection, selectionArgs, null, null, sortOrder);

		return c;
	}

	private String buildSortOrder(int match, String sortOrder) {
		if (sortOrder == null) {
			if (match == TRADER || match == TRADER_ID)
				sortOrder = Trader.DEFAULT_SORT;
			if (match == CONTACT || match == CONTACT_ID)
				sortOrder = Contact.DEFAULT_SORT;
			else if (match == CLASSIFICATION || match == CLASSIFICATION_ID)
				sortOrder = Classification.DEFAULT_SORT;
			else if (match == POSITION || match == POSITION_ID)
				sortOrder = Position.DEFAULT_SORT;
		}

		return sortOrder;
	}

	private SQLiteQueryBuilder buildExpandedSelection(Uri uri, int match) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		String id = null;

		switch (match) {
		case TRADER:
			builder.setTables(DatabaseHelper.Tables.TRADER);
			break;
		case TRADER_ID:
			id = Trader.getId(uri);
			builder.setTables(DatabaseHelper.Tables.TRADER);
			builder.appendWhere(BaseColumns._ID + " = " + id);
			break;
		case CONTACT:
			builder.setTables(DatabaseHelper.Tables.CONTACT);
			break;
		case CONTACT_ID:
			id = Contact.getId(uri);
			builder.setTables(DatabaseHelper.Tables.CONTACT);
			builder.appendWhere(BaseColumns._ID + " = " + id);
			break;
		case CLASSIFICATION:
			builder.setTables(DatabaseHelper.Tables.CLASSIFICATION);
			break;
		case CLASSIFICATION_ID:
			id = Classification.getId(uri);
			builder.setTables(DatabaseHelper.Tables.CLASSIFICATION);
			builder.appendWhere(BaseColumns._ID + " = " + id);
			break;
		case POSITION:
			builder.setTables(DatabaseHelper.Tables.POSITION);
			break;
		case POSITION_ID:
			id = Position.getId(uri);
			builder.setTables(DatabaseHelper.Tables.POSITION);
			builder.appendWhere(BaseColumns._ID + " = " + id);
			break;
		default:
			// If the URI doesn't match any of the known patterns, throw an
			// exception.
			throw new IllegalArgumentException("Unknown URI on XTradeProvider sure it is implemented?" + uri);
		}

		return builder;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		int count;

		String id = null;
		String finalWhere = null;

		int match = uriMatcher.match(uri);
		switch (match) {
		case TRADER:
			count = db.update(DatabaseHelper.Tables.TRADER, values, selection, selectionArgs);
			break;
		case TRADER_ID:
			id = Trader.getId(uri);
			finalWhere = BaseColumns._ID + " = " + id;
			if (selection != null)
				finalWhere = finalWhere + " AND " + selection;

			count = db.update(DatabaseHelper.Tables.TRADER, values, finalWhere, selectionArgs);
			break;
		case CONTACT:
			count = db.update(DatabaseHelper.Tables.CONTACT, values, selection, selectionArgs);
			break;
		case CONTACT_ID:
			id = Contact.getId(uri);
			finalWhere = BaseColumns._ID + " = " + id;
			if (selection != null)
				finalWhere = finalWhere + " AND " + selection;

			count = db.update(DatabaseHelper.Tables.CONTACT, values, finalWhere, selectionArgs);
			break;
		case CLASSIFICATION:
			count = db.update(DatabaseHelper.Tables.CLASSIFICATION, values, selection, selectionArgs);
			break;
		case CLASSIFICATION_ID:
			id = Classification.getId(uri);
			finalWhere = BaseColumns._ID + " = " + id;
			if (selection != null)
				finalWhere = finalWhere + " AND " + selection;
			
			count = db.update(DatabaseHelper.Tables.CLASSIFICATION, values, finalWhere, selectionArgs);
			break;
		case POSITION:
			count = db.update(DatabaseHelper.Tables.POSITION, values, selection, selectionArgs);
			break;
		case POSITION_ID:
			id = Position.getId(uri);
			finalWhere = BaseColumns._ID + " = " + id;
			if (selection != null)
				finalWhere = finalWhere + " AND " + selection;

			count = db.update(DatabaseHelper.Tables.POSITION, values, finalWhere, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);

		}

		return count;
	}

}