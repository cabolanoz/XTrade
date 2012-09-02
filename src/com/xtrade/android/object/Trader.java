package com.xtrade.android.object;

import com.google.gson.annotations.SerializedName;

public class Trader {

	public String id;
	public String name;
	public String website;
	public String address;
	public String logo;
	//TODO: this might come handy to be a collection of locations
	//TODO: ignore this until resolve serialization problems
	//public Location location;
	
	@SerializedName("favorite")
	public boolean isFavorite;

	public Trader(String id, String name, String website, String address, boolean isFavorite) {
		this.id = id;
		this.name = name;
		this.website = website;
		this.address = address;
		
		this.isFavorite = isFavorite;
	}

	@Override
	public String toString() {
		return "Trader [id=" + id + ", name=" + name + ", website=" + website
				+ ", address=" + address + ", location: " //+ location.toString()
				+ ", isFavorite=" + isFavorite + "]";
	}

	
	public static class Location {
		public double lat;
		public double lon;
		
		public String toString(){
			return "lat: "+lat+" lon: "+lon;
		}
	}
	
}