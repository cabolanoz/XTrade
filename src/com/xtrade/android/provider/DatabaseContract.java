package com.xtrade.android.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

	public static final String REFRESH_PARAM = "refresh";

	
	public interface XTradeBaseColumns extends BaseColumns{
		//useful when a record have been updated and need to be synced
		public static final String FLAG_STATE="dirty";
	}
	public interface TraderColumns extends XTradeBaseColumns{
		String TRADER_ID = "TraderId";
		String NAME = "Name";
		String WEBSITE = "Website";
		String ADDRESS = "Address";
		String LONGITUDE = "longitude";
		String LATITUDE = "latitude";
		String ISFAVORITE = "IsFavorite";
	}
	
	public interface ContactColumns extends XTradeBaseColumns{
		String CONTACT_ID = "ContactId";
		String NAME = "Name";
		String TYPE = "Type";
		String EMAIL = "Email";
		String PHONE = "Phone";
		String TRADER_ID = "TraderId";
	}
	
	public interface ContactTypeColumns extends XTradeBaseColumns{
		String CONTACT_TYPE_ID = "ContactTypeId";
		String NAME = "Name";
	}

	public static final String CONTENT_AUTHORITY = "com.xtrade.android";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	public static final String PATH_TRADER = "trader";
	public static final String PATH_CONTACT = "contact";
	public static final String PATH_CONTACT_TYPE = "contact_type";

	public static class TraderEntity extends BaseTable implements TraderColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TRADER).build();
		
		/**
		 * Specify the content for the record and for this entity
		 */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xtrade.android.trader";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xtrade.android.trader";
		
		/**
		 * Default sorting for this entity
		 */
		public static final String DEFAULT_SORT = TRADER_ID + " ASC";
		
		public static Uri buildUri(String traderId) {
			return CONTENT_URI.buildUpon().appendPath(traderId).build();
		}
		
		public static Uri buildUri(long traderId) {
			return CONTENT_URI.buildUpon().appendPath(String.valueOf(traderId)).build();
		}
	}
	
	public static class Contact extends BaseTable implements ContactColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTACT).build();
		
		/**
		 * Specify the content for the record and for this entity
		 */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xtrade.android.contact";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xtrade.android.contact";
		
		/**
		 * Default sorting for this entity
		 */
		public static final String DEFAULT_SORT = CONTACT_ID + " ASC";
		
		public static Uri buildUri(String contactId) {
			return CONTENT_URI.buildUpon().appendPath(contactId).build();
		}
		public static Uri buildUri(long contactId) {
			return CONTENT_URI.buildUpon().appendPath(String.valueOf(contactId)).build();
		}
	}
	
	public static class ContactType extends BaseTable implements ContactTypeColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTACT_TYPE).build();

		/**
		 * Specify the content for the record and for this entity
		 */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xtrade.android.contacttype";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xtrade.android.contacttype";

		/**
		 * Default sorting for this entity
		 */
		public static final String DEFAULT_SORT = CONTACT_TYPE_ID + " ASC";

		public static Uri buildUri(String contactTypeId) {
			return CONTENT_URI.buildUpon().appendPath(contactTypeId).build();
		}
	}

}