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
import com.xtrade.android.TraderActivity;
import com.xtrade.android.R;
import com.xtrade.android.object.Trader;
import com.xtrade.android.provider.DatabaseContract;
import com.xtrade.android.util.EventConstant;

public class TraderAdapter extends BaseAdapter implements EventConstant {

	private Context context;
	private List<Trader> traderList;

	public TraderAdapter(Context _context, List<Trader> _traderList) {
		this.context = _context;
		this.traderList = _traderList;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.trader_item, null);
		}

		final Trader trader = getTraderList().get(position);
		
		TextView tvwTraderName = (TextView) convertView.findViewById(R.id.tvwTraderName);
		tvwTraderName.setText(trader.getName());

		TextView tvwTraderAddress = (TextView) convertView.findViewById(R.id.tvwTraderAddress);
		tvwTraderAddress.setText(trader.getAddress());

		Button btnEditClient = (Button) convertView.findViewById(R.id.btnEditTrader);
		btnEditClient.setOnClickListener(new OnClickListener() {
			public void onClick(View view) {
				Intent intent = new Intent(context, TraderActivity.class);
				intent.putExtra("ACTION_TYPE", TRADER_UPDATE_REQUEST_CODE);
				intent.putExtra(DatabaseContract.TraderColumns.TRADER_ID, trader.getId());
				((BaseActivity) context).startActivityForResult(intent, TRADER_UPDATE_REQUEST_CODE);
			}
		});

		return convertView;
	}
	
	public List<Trader> getTraderList() {
		return traderList;
	}

	public void setTraderList(List<Trader> _traderList) {
		this.traderList = _traderList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return traderList.size();
	}

	@Override
	public Object getItem(int position) {
		return traderList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

}
