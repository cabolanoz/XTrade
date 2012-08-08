package com.xtrade.android.fragment;

import com.xtrade.android.BaseActivity;
import com.xtrade.android.R;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockFragment;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.xtrade.android.R.id;
import com.xtrade.android.R.layout;
import com.xtrade.android.R.menu;
import com.xtrade.android.adapter.TraderAdapter;
import com.xtrade.android.provider.DatabaseContract.TraderColumns;
import com.xtrade.android.provider.TraderTranslator;
import com.xtrade.android.util.ActionConstant;
import com.xtrade.android.util.Debug;
import com.xtrade.android.util.EventConstant;

public class TraderListFragment extends SherlockFragment implements EventConstant {

	private BaseAdapter adapter;
	private ActionMode mActionMode;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		final View fragmentView = inflater.inflate(R.layout.trader_tab_list, container, false);
		
		// load data from database
		Cursor cursor = getActivity().getContentResolver().query(com.xtrade.android.provider.DatabaseContract.Trader.CONTENT_URI, null, null, null, null);

		adapter = new TraderAdapter(getActivity(), new TraderTranslator().translate(cursor));

		ListView listView = (ListView) fragmentView.findViewById(R.id.lvwTrader);
		listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
				Intent intent = new Intent(ActionConstant.TRADER_DETAIL);
				intent.putExtra(TraderColumns.TRADER_ID, ((com.xtrade.android.object.Trader) adapter.getItem(position)).getId());
				startActivity(intent);
			}
		});
		
		listView.setOnItemLongClickListener(new OnItemLongClickListener() {
			public boolean onItemLongClick(AdapterView<?> adapter, View view, int position, long id) {
				if (mActionMode != null)
		            return false;

		        // Start the CAB using the ActionMode.Callback defined above
				mActionMode =  ((BaseActivity) getActivity()).startActionMode(mActionModeCallback);
		        view.setSelected(true);
		        return true;
			}
		});
		
		return fragmentView;
	}
	
	private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
	    
		// Called when the action mode is created; startActionMode() was called
	    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
	        // Inflate a menu resource providing context menu items
	        MenuInflater inflater = mode.getMenuInflater();
	        inflater.inflate(R.menu.trader_context_menu, menu);
	        return true;
	    }

	    // Called each time the action mode is shown. Always called after onCreateActionMode, but
	    // may be called multiple times if the mode is invalidated.
	    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
	        return false;
	    }

	    // Called when the user selects a contextual menu item
	    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
	    	switch (item.getItemId()) {
	    	case R.id.mniEditTrader:
	    		mode.finish();
	    		return true;
	    	case R.id.mniFavorite:
	    		item.setChecked(!item.isChecked());
        		item.setIcon(item.isChecked() ? android.R.drawable.btn_star_big_on : android.R.drawable.btn_star);
	    		return true;
	    	default:
	    		return false;
	    	}
	    }

	    // Called when the user exits the action mode
	    public void onDestroyActionMode(ActionMode mode) {
	        mActionMode = null;
	    }
	};
	
}
