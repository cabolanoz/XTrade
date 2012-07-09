package com.xtrade.android.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xtrade.android.R;
import com.xtrade.android.object.Contact;
import com.xtrade.android.util.EventConstant;

public class ContactAdapter extends BaseAdapter implements EventConstant {

	private Context context;
	private List<Contact> contactList;

	public ContactAdapter(Context _context, List<Contact> contactList) {
		this.context = _context;
		this.contactList = contactList;
	}
	
	public List<Contact> getContactList() {
		return contactList;
	}

	public void setContactList(List<Contact> _contactList) {
		this.contactList = _contactList;
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return contactList.size();
	}

	@Override
	public Object getItem(int position) {
		return contactList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.trader_tab_list_contact_item, null);
		}
		
		Contact contact = getContactList().get(position);
		
		if (position % 2 == 0)
			convertView.setBackgroundResource(R.drawable.list_bg_odd);
		
		TextView tvwContactName = (TextView) convertView.findViewById(R.id.tvwContactName);
		tvwContactName.setText(contact.getName());
		
		TextView tvwContactType = (TextView) convertView.findViewById(R.id.tvwContactType);
		tvwContactType.setText(contact.getType());
		
		TextView tvwContactEmail = (TextView) convertView.findViewById(R.id.tvwContactEmail);
		tvwContactEmail.setText(contact.getEmail());
		
		TextView tvwContactPhone = (TextView) convertView.findViewById(R.id.tvwContactPhone);
		tvwContactPhone.setText(contact.getPhone());
		
		return convertView;
	}

	
	
}
