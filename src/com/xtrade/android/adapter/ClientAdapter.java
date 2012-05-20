package com.xtrade.android.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.xtrade.android.BaseActivity;
import com.xtrade.android.ClientActivity;
import com.xtrade.android.R;
import com.xtrade.android.object.Client;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.util.EventConstant;

public class ClientAdapter extends BaseAdapter implements EventConstant {

	private Context context;
	private List<Client> clientList;

	public ClientAdapter(Context _context, List<Client> _clientList) {
		this.context = _context;
		this.clientList = _clientList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.client_item, null);
		}

		final Client client = getClientList().get(position);
		
		TextView tvwTraderDescription = (TextView) convertView.findViewById(R.id.tvwTraderDescription);
		tvwTraderDescription.setText(client.getDescription());

		TextView tvwTraderWebsite = (TextView) convertView.findViewById(R.id.tvwTraderWebsite);
		tvwTraderWebsite.setText(client.getWebsite());

		Button btnEditClient = (Button) convertView.findViewById(R.id.btnEditClient);
		btnEditClient.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(context, ClientActivity.class);
				intent.putExtra("ACTION_TYPE", CLIENT_UPDATE_REQUEST_CODE);
				intent.putExtra(DatabaseContract.ClientColumns.TRADER_ID, client.getId());
				((BaseActivity) context).startActivityForResult(intent, CLIENT_UPDATE_REQUEST_CODE);
			}
		});

		return convertView;
	}
	
	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return clientList.size();
	}

	@Override
	public Object getItem(int position) {
		return clientList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
