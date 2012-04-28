package com.xtrade.android.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;

import com.xtrade.android.provider.DatabaseContract.Client;
import com.xtrade.android.provider.DatabaseContract.Position;

public class XTradeProvider extends ContentProvider {

	public static final int CLIENT = 1000;
	public static final int CLIENT_ID = 1001;
	public static final int POSITION = 1002;
	public static final int POSITION_ID = 1003;

	private final static int LIMIT_CALLS = 10;

	private DatabaseHelper databaseHelper;

	private static final UriMatcher uriMatcher = buildMatcher();

	public static UriMatcher buildMatcher() {
		final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
		final String CONTENT_AUTHORITY = DatabaseContract.CONTENT_AUTHORITY;

		matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_CLIENT, CLIENT);
		matcher.addURI(CONTENT_AUTHORITY, DatabaseContract.PATH_CLIENT + "/*", CLIENT_ID);
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
		case CLIENT:
			db.insertOrThrow(DatabaseHelper.Tables.CLIENT, null, values);
			getContext().getContentResolver().notifyChange(uri, null);
			return Client.buildUri(values.getAsString(BaseColumns._ID));
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
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		final int match = uriMatcher.match(uri);

		final SQLiteDatabase db = databaseHelper.getWritableDatabase();

		SQLiteQueryBuilder builder = buildExpandedSelection(uri, match);
		Cursor c = builder.query(db, projection, selection, selectionArgs,
				null, null, sortOrder);

		return c;
	}

	private SQLiteQueryBuilder buildExpandedSelection(Uri uri, int match) {
		SQLiteQueryBuilder builder = new SQLiteQueryBuilder();
		String id = null;
		switch (match) {
		case POSITION:
			builder.setTables(DatabaseHelper.Tables.POSITION);
			break;
		case POSITION_ID:
			id = Position.getId(uri);
			builder.setTables(DatabaseHelper.Tables.POSITION);
			builder.appendWhere(BaseColumns._ID + "=" + id);
			break;
		default:
			// If the URI doesn't match any of the known patterns, throw an
			// exception.
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		return builder;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		SQLiteDatabase db = databaseHelper.getWritableDatabase();
		int count;

		String id = null;
		String finalWhere = null;

		int match = uriMatcher.match(uri);
		switch (match) {
		case POSITION:
			count = db.update(DatabaseHelper.Tables.POSITION, values,
					selection, selectionArgs);
			break;
		case POSITION_ID:
			id = Position.getId(uri);
			finalWhere = BaseColumns._ID + " = " + id;
			if (selection != null)
				finalWhere = finalWhere + " AND " + selection;

			count = db.update(DatabaseHelper.Tables.POSITION, values,
					finalWhere, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);

		}

		return count;
	}

}