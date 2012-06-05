package com.xtrade.android.object;

public class Trader {

	private String id;
	private String name;
	private String address;
	private String posX;
	private String posY;
	private String note;

	public Trader(String id, String _name, String _address, String _posX, String _posY, String note) {
		this.id = id;
		this.name = _name;
		this.address = _address;
		this.posX = _posX;
		this.posY = _posY;
		this.note = note;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPosX() {
		return posX;
	}

	public void setPosX(String posX) {
		this.posX = posX;
	}

	public String getPosY() {
		return posY;
	}

	public void setPosY(String posY) {
		this.posY = posY;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

}