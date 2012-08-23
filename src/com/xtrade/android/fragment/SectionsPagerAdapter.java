package com.xtrade.android.fragment;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.xtrade.android.TraderActivity;

/**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the primary
     * sections of the app.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {
    	
    	private Class<? extends Fragment> fragmentsClass[];
    	private Fragment fragments[];
    	private Context context;
    	private String[] titles;
        public SectionsPagerAdapter(FragmentManager fm,Class<? extends Fragment>[] fragmentsClass,String[] titles,Context context) {
        	super(fm);
        	this.fragmentsClass=fragmentsClass;
        	fragments=new Fragment[fragmentsClass.length];
        	this.titles= titles;
        	this.context=context;
        }

        @Override
        public Fragment getItem(int i) {
        	if(fragments[i]==null)
        		fragments[i]=Fragment.instantiate(context, fragmentsClass[i].getName());
        	
           return fragments[i];
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            
            return titles[position];
        }
    }

	
