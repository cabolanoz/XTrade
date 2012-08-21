package com.xtrade.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.xtrade.android.R;
import com.xtrade.android.object.Trader;
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
			convertView = inflater.inflate(R.layout.trader_tab_list_item, null);
		}

		final Trader trader = getTraderList().get(position);
		
		if (position % 2 == 0)
			convertView.setBackgroundResource(R.drawable.list_bg_odd);
		
		TextView tvwTraderName = (TextView) convertView.findViewById(R.id.tvwTraderName);
		tvwTraderName.setText(trader.getName());

		TextView tvwTraderWebsite = (TextView) convertView.findViewById(R.id.tvwTraderWebsite);
		tvwTraderWebsite.setText(trader.getAddress());

		CheckBox chbFavorite = (CheckBox) convertView.findViewById(R.id.chbFavorite);
		String isFavorite = trader.getIsFavorite();
		if (isFavorite != null && !"".equals(isFavorite) && "1".equals(isFavorite))
			chbFavorite.setButtonDrawable(android.R.drawable.btn_star_big_on);
		else
			chbFavorite.setButtonDrawable(android.R.drawable.btn_star);
		
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
