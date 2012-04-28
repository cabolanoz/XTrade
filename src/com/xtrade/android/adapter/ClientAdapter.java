package com.xtrade.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.xtrade.android.R;
import com.xtrade.android.object.Client;

/**
 * 
 * @author <a href="mailto:cesar20904@gmail.com">César Bolaños</a>
 *
 */
public class ClientAdapter extends ArrayAdapter<Client> {

	public ClientAdapter(Context context, List<Client> objects) {
		super(context, R.layout.client_item, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
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
		
		return convertView;
	}
	
	
}
