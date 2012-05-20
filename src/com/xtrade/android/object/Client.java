package com.xtrade.android.object;

public class Client {

	private String id;
	private String description;
	private String website;
	private String location;
	private String note;

	public Client(String id, String description, String website, String location, String note) {
		this.id = id;
		this.description = description;
		this.website = website;
		this.location = location;
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}