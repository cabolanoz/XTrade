package com.xtrade.android;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.actionbarsherlock.app.SherlockFragment;
import com.xtrade.android.provider.ClassificationTranslator;
import com.xtrade.android.util.EventConstant;

public class ClientDetailFragment extends SherlockFragment implements EventConstant {

	private Spinner spnClassification;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.client_tab_detail, container, false);
		
		// We obtain the intent which calls the activity
		Intent intent = getActivity().getIntent();
		
		Cursor cursor = getActivity().getContentResolver().query(com.xtrade.android.provider.DatabaseContract.Classification.CONTENT_URI, null, null, null, null);
		
		ArrayAdapter arrayAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, new ClassificationTranslator().translate(cursor));
		
		spnClassification = (Spinner) view.findViewById(R.id.spnClassification);
		spnClassification.setAdapter(arrayAdapter);
		
		Button btnAddClassification = (Button) view.findViewById(R.id.btnAddClassification);
		btnAddClassification.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
			}
		});
		
		return view;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
	}
	
}
