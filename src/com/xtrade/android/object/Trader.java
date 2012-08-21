package com.xtrade.android.object;

public class Trader {

	private String id;
	private String name;
	private String website;
	private String address;
	private String posX;
	private String posY;
	private String isFavorite;

	public Trader(String _id, String _name, String _website, String _address, String _posX, String _posY, String _isFavorite) {
		this.id = _id;
		this.name = _name;
		this.website = _website;
		this.address = _address;
		this.posX = _posX;
		this.posY = _posY;
		this.isFavorite = _isFavorite;
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

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
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

	public String getIsFavorite() {
		return isFavorite;
	}

	public void setIsFavorite(String isFavorite) {
		this.isFavorite = isFavorite;
	}

}