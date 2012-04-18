package com.xtrade.android.provider;

import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

	public static final String REFRESH_PARAM = "refresh";

	interface ClassificationColumns {
		String CLASSIFFICATION_ID = "ClassificationId";
		String NAME = "Name";
	}

	interface PositionColumns {
		String POSITION_ID = "PositionId";
		String NAME = "Name";
	}

	interface ContactTypeColumns {
		String POSITION_ID = "ContactTypeId";
		String NAME = "Name";
	}

	public static final String CONTENT_AUTHORITY = "com.xtrade.android";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

	private static final String PATH_CLASSIFICATION = "classification";
	private static final String PATH_POSITION = "position";
	private static final String PATH_CONTACT_TYPE = "contact_type";

	public static abstract class BaseTable implements BaseColumns {

		public String getId(Uri uri) {

			if (uri.getPathSegments().size() >= 2)
				return uri.getPathSegments().get(1);
			return null;
		}

	}

	public static class Classification extends BaseTable implements ClassificationColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CLASSIFICATION).build();

		/**
		 * Specify the content for the record and for this entity
		 * */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xtrade.android.classification";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xtrade.android.classification";

		/**
		 * Default sorting for this entity
		 * */
		public static final String DEFAULT_SORT = ClassificationColumns.CLASSIFFICATION_ID + " ASC";

		public static Uri buildUri(String classificationId) {
			return CONTENT_URI.buildUpon().appendPath(classificationId).build();
		}

	}
	
	public static class Position extends BaseTable implements PositionColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_POSITION).build();

		/**
		 * Specify the content for the record and for this entity
		 * */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xtrade.android.position";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xtrade.android.position";

		/**
		 * Default sorting for this entity
		 * */
		public static final String DEFAULT_SORT = PositionColumns.POSITION_ID + " ASC";

		public static Uri buildUri(String positionId) {
			return CONTENT_URI.buildUpon().appendPath(positionId).build();
		}

	}
	
	public static class ContactType extends BaseTable implements ContactTypeColumns {
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_CONTACT_TYPE).build();

		/**
		 * Specify the content for the record and for this entity
		 * */
		public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.xtrade.android.contacttype";
		public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.xtrade.android.contacttype";

		/**
		 * Default sorting for this entity
		 * */
		public static final String DEFAULT_SORT = ContactTypeColumns.POSITION_ID + " ASC";

		public static Uri buildUri(String contactTypeId) {
			return CONTENT_URI.buildUpon().appendPath(contactTypeId).build();
		}

	}
	
	

}
