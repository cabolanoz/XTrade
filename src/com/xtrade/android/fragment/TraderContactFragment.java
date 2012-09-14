package com.xtrade.android.fragment;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.BaseActivity;
import com.xtrade.android.R;
import com.xtrade.android.provider.DatabaseContract.ContactColumns;
import com.xtrade.android.provider.DatabaseContract.ContactEntity;
import com.xtrade.android.provider.DatabaseContract.TraderColumns;
import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.EventConstant;

public class TraderContactFragment extends SherlockFragment implements EventConstant, LoaderManager.LoaderCallbacks<Cursor> {

	private CursorAdapter adapter;
	private ActionMode mActionMode;
	private long contactId = -1;
	private View selectedView = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.trader_tab_list_contact, container, false);

		// Getting the activity intent
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			long traderId = intent.getLongExtra(TraderColumns.TRADER_ID, -1);
			if (traderId !=-1 ) {
				adapter = new ContactAdapter(getActivity());

				ListView listView = (ListView) fragmentView.findViewById(R.id.lvwContact);
				listView.setAdapter(adapter);

				listView.setOnItemLongClickListener(new OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
						if (mActionMode != null)
							return false;

						contactId = id;
						selectedView = view;

						mActionMode = ((BaseActivity) getActivity()).startActionMode(mActionModeCallback);
						
						view.setSelected(true);
						
						return true;
					}
				});
			}
		}

		return fragmentView;
	}

	public void onResume() {
		super.onResume();
		getActivity().getSupportLoaderManager().restartLoader(0, null, this);
	}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		
		if (resultCode == FragmentActivity.RESULT_CANCELED)
			return;
		
		if (requestCode == CONTACT_UPDATE_REQUEST_CODE && mActionMode.isUiFocusable())
			mActionMode.finish();
		
		if (resultCode == FragmentActivity.RESULT_OK && (requestCode == CONTACT_CREATE_REQUEST_CODE || requestCode == CONTACT_UPDATE_REQUEST_CODE))
			getActivity().getSupportLoaderManager().restartLoader(0, null, TraderContactFragment.this);
	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
			if (selectedView != null)
				selectedView.setSelected(false);
			mActionMode = null;
		}

		@Override
		public boolean onCreateActionMode(ActionMode mode, Menu menu) {
			MenuInflater inflater = mode.getMenuInflater();
			inflater.inflate(R.menu.contact_context_menu, menu);
			return true;
		}

		@Override
		public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
			switch (item.getItemId()) {
			case R.id.mniEditContact:
				Intent intent = new Intent(ActionConstant.CONTACT_CREATE_UPDATE);
				intent.putExtra("ACTION_TYPE", CONTACT_UPDATE_REQUEST_CODE);
				intent.putExtra(ContactColumns.CONTACT_ID, contactId);
				intent.putExtra(TraderColumns.TRADER_ID, getActivity().getIntent().getLongExtra(TraderColumns.TRADER_ID, -1));
				startActivityForResult(intent, CONTACT_UPDATE_REQUEST_CODE);
				return true;
			}
			
			return false;
		}
	};

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle data) {
		long traderId = getActivity().getIntent().getLongExtra(TraderColumns.TRADER_ID, -1);
		Loader<Cursor> loader = new CursorLoader(getActivity(), ContactEntity.CONTENT_URI, null, ContactColumns.TRADER_ID + " = '" + traderId + "'", null, ContactEntity.DEFAULT_SORT);
		return loader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
		if (getActivity() == null)
			return;
		
		adapter.changeCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) { }

	public class ContactAdapter extends CursorAdapter implements EventConstant {

		public ContactAdapter(Context context) {
			super(context, null, false);
		}

		@Override
		public View newView(Context context, Cursor cursor, ViewGroup parent) {
			View view = getActivity().getLayoutInflater().inflate(R.layout.trader_tab_list_contact_item, parent, false);
			return view;
		}
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			convertView = super.getView(position, convertView, parent);
			if (position % 2 == 0)
				convertView.setBackgroundResource(R.drawable.list_bg_odd);
			else
				convertView.setBackgroundResource(R.drawable.list_bg);
			
			convertView.setClickable(true);
			convertView.setFocusable(true);
			
			return convertView;
		}
		
		@Override
		public void bindView(View view, Context context, Cursor cursor) {
			String contactId = cursor.getString(cursor.getColumnIndexOrThrow(ContactEntity._ID));
			if (contactId == null)
				return;
			
			TextView tvwContactName = (TextView) view.findViewById(R.id.tvwContactName);
			tvwContactName.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactColumns.NAME)));
			
			TextView tvwContactType = (TextView) view.findViewById(R.id.tvwContactType);
			tvwContactType.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactColumns.TYPE)));
			
			TextView tvwContactEmail = (TextView) view.findViewById(R.id.tvwContactEmail);
			tvwContactEmail.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactColumns.EMAIL)));
			tvwContactEmail.setClickable(true);
			tvwContactEmail.setFocusable(true);
			
			tvwContactEmail.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String[] recipient = new String[] {((TextView) v).getText().toString()};
					
					Intent intent = new Intent(Intent.ACTION_SEND);
					
					intent.putExtra(Intent.EXTRA_EMAIL, recipient);
					intent.putExtra(Intent.EXTRA_SUBJECT, "subject");
					intent.putExtra(Intent.EXTRA_TEXT, "");

					intent.setType("text/plain");
					
					try {
						startActivity(Intent.createChooser(intent, "Send mail"));
					} catch (ActivityNotFoundException anfe) { }
				}
			});
			
			TextView tvwContactPhone = (TextView) view.findViewById(R.id.tvwContactPhone);
			tvwContactPhone.setText(cursor.getString(cursor.getColumnIndexOrThrow(ContactColumns.PHONE)));
			tvwContactPhone.setClickable(true);
			tvwContactPhone.setFocusable(true);
			
			tvwContactPhone.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					String phone = ((TextView) v).getText().toString();
					
					Intent intent = new Intent(Intent.ACTION_CALL);
					intent.setData(Uri.parse("tel:" + phone));
					
					startActivity(intent);
				}
			});
		}
		
	}
	
}
