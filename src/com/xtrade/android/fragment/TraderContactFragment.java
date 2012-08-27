package com.xtrade.android.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.BaseActivity;
import com.xtrade.android.R;
import com.xtrade.android.adapter.ContactAdapter;
import com.xtrade.android.provider.ContactTranslator;
import com.xtrade.android.provider.DatabaseContract.Contact;
import com.xtrade.android.provider.DatabaseContract.ContactColumns;
import com.xtrade.android.provider.DatabaseContract.TraderColumns;
import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.EventConstant;

public class TraderContactFragment extends SherlockFragment implements
		EventConstant {

	private BaseAdapter adapter;
	private ActionMode mActionMode;
	private int selectedPosition = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View fragmentView = inflater.inflate(R.layout.trader_tab_list_contact, container, false);

		// Getting the activity intent
		Intent intent = getActivity().getIntent();
		if (intent != null) {
			String traderId = intent.getStringExtra(TraderColumns.TRADER_ID);
			if (traderId != null && !"".equals(traderId)) {
				Cursor cursor = getActivity().getContentResolver().query(Contact.CONTENT_URI, null, ContactColumns.TRADER_ID + " = '" + traderId + "'", null, null);

				adapter = new ContactAdapter(getActivity(), new ContactTranslator().translate(cursor));

				ListView listView = (ListView) fragmentView.findViewById(R.id.lvwContact);
				listView.setAdapter(adapter);

				listView.setOnItemLongClickListener(new OnItemLongClickListener() {
					public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
						if (mActionMode != null)
							return false;

						selectedPosition = position;

						mActionMode = ((BaseActivity) getActivity()).startActionMode(mActionModeCallback);
						
						view.setSelected(true);
						
						return true;
					}
				});
			}
		}

		return fragmentView;
	}

	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {

		@Override
		public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
			return false;
		}

		@Override
		public void onDestroyActionMode(ActionMode mode) {
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
				intent.putExtra(ContactColumns.CONTACT_ID, ((com.xtrade.android.object.Contact) adapter.getItem(selectedPosition)).getContactId());
				intent.putExtra(TraderColumns.TRADER_ID, getActivity().getIntent().getStringExtra(TraderColumns.TRADER_ID));
				startActivityForResult(intent, CONTACT_UPDATE_REQUEST_CODE);
				return true;
			default:
				return false;
			}
		}
	};

}
