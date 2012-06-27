package com.xtrade.android.listener;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public 	class TraderTabListener<T extends Fragment> implements ActionBar.TabListener {

	private Fragment fragment;
	private SherlockFragmentActivity mActivity;
    private final String mTag;
    private final Class<T> mClass;

	public TraderTabListener(SherlockFragmentActivity mActivity, String mTag, Class<T> mClass) {
		this.mActivity = mActivity;
		this.mTag = mTag;
		this.mClass = mClass;
	}

	public void onTabSelected(Tab tab, FragmentTransaction ft) {
		 // Check if the fragment is already initialized
        if (fragment == null) {
            // If not, instantiate and add it to the activity
            fragment = Fragment.instantiate(mActivity, mClass.getName());
            ft.add(android.R.id.content, fragment, mTag);
        } else {
            // If it exists, simply attach it in order to show it
            ft.attach(fragment);
        }
	}

	public void onTabUnselected(Tab tab, FragmentTransaction ft) {
		 if (fragment != null) {
	            // Detach the fragment, because another one is being attached
	            ft.detach(fragment);
	        }	
	}

	public void onTabReselected(Tab tab, FragmentTransaction ft) { }

}
