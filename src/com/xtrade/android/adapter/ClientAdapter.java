package com.xtrade.android.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.xtrade.android.BaseActivity;
import com.xtrade.android.ClientActivity;
import com.xtrade.android.R;
import com.xtrade.android.object.Client;
import com.xtrade.android.provider.DatabaseContract;

public class ClientAdapter extends ArrayAdapter<Client> {

	private final int UPDATE_REQUEST_CODE = 101;
	
	public ClientAdapter(Context context, List<Client> objects) {
		super(context, R.layout.client_item, objects);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.client_item, null);
		}

		TextView tvwClientName = (TextView) convertView.findViewById(R.id.tvwClientName);
		tvwClientName.setText(getItem(position).getName());

		TextView tvwClientPhone = (TextView) convertView.findViewById(R.id.tvwClientPhone);
		tvwClientPhone.setText(getItem(position).getPhone());

		TextView tvwClientAddress = (TextView) convertView.findViewById(R.id.tvwClientAddress);
		tvwClientAddress.setText(getItem(position).getAddress());
		
		Button btnEditClient = (Button) convertView.findViewById(R.id.btnEditClient);
		btnEditClient.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(getContext(), ClientActivity.class);
				intent.putExtra("ACTION_TYPE", UPDATE_REQUEST_CODE);
				intent.putExtra(DatabaseContract.ClientColumns.CLIENT_ID, getItemId(position));
				intent.putExtra(DatabaseContract.ClientColumns.NAME, getItem(position).getName());
				intent.putExtra(DatabaseContract.ClientColumns.PHONE, getItem(position).getPhone());
				intent.putExtra(DatabaseContract.ClientColumns.ADDRESS, getItem(position).getAddress());
				((BaseActivity) getContext()).startActivityForResult(intent, UPDATE_REQUEST_CODE);
			}
		});
		
		return convertView;
	}

}
