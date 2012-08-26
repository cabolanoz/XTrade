package com.xtrade.android.object;

public class Contact {

	private String contactId;
	private String name;
	private String type;
	private String email;
	private String phone;
	private String traderId;
	
	public Contact(String _contactId, String _name, String _type, String _email, String _phone, String _traderId) {
		this.contactId = _contactId;
		this.name = _name;
		this.type = _type;
		this.email = _email;
		this.phone = _phone;
		this.traderId = _traderId;
	}
	
	public String getContactId() {
		return contactId;
	}

	public void setContactId(String contactId) {
		this.contactId = contactId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getTraderId() {
		return traderId;
	}

	public void setTraderId(String traderId) {
		this.traderId = traderId;
	}
	
}
