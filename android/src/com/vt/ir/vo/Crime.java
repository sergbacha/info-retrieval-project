/**
 * 
 */
package com.vt.ir.vo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @author 	Sergio Bernales
 * @date 	Apr 30, 2012
 *
 * Copyright 2012 Locomoti LLC. All rights reserved.
 *
 */
public class Crime implements Parcelable {
	String crimeDate;
	String id;
	String catagory;
	String bussiness;
	String county;
	String catagorie;
	String crimegroup;
	String description;
	String location;
	String city;
	String state;
	String zipcode;
	String service;
	String accuracy;
	String coordinates_latlong;
	String timestamp;
	
	public Crime() {
		
	}
	
	public String getCrimeDate() {
		return crimeDate;
	}
	public void setCrimeDate(String crimeDate) {
		this.crimeDate = crimeDate;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCatagory() {
		return catagory;
	}
	public void setCatagory(String catagory) {
		this.catagory = catagory;
	}
	public String getBussiness() {
		return bussiness;
	}
	public void setBussiness(String bussiness) {
		this.bussiness = bussiness;
	}
	public String getCounty() {
		return county;
	}
	public void setCounty(String county) {
		this.county = county;
	}
	public String getCatagorie() {
		return catagorie;
	}
	public void setCatagorie(String catagorie) {
		this.catagorie = catagorie;
	}
	public String getCrimegroup() {
		return crimegroup;
	}
	public void setCrimegroup(String crimegroup) {
		this.crimegroup = crimegroup;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public String getAccuracy() {
		return accuracy;
	}
	public void setAccuracy(String accuracy) {
		this.accuracy = accuracy;
	}
	public String getCoordinates_latlong() {
		return coordinates_latlong;
	}
	public void setCoordinates_latlong(String coordinates_latlong) {
		this.coordinates_latlong = coordinates_latlong;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	/* (non-Javadoc)
	 * @see android.os.Parcelable#describeContents()
	 */
	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 * @see android.os.Parcelable#writeToParcel(android.os.Parcel, int)
	 */
	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(crimeDate);
		dest.writeString(id);
		dest.writeString(catagory);
		dest.writeString(bussiness);
		dest.writeString(county);
		dest.writeString(catagorie);
		dest.writeString(crimegroup);
		dest.writeString(description);
		dest.writeString(location);
		dest.writeString(city);
		dest.writeString(state);
		dest.writeString(zipcode);
		dest.writeString(service);
		dest.writeString(accuracy);
		dest.writeString(coordinates_latlong);
		dest.writeString(timestamp);		
	}
	
	public static final Parcelable.Creator<Crime> CREATOR
	    = new Parcelable.Creator<Crime>() {
		
		public Crime createFromParcel(Parcel in) {
		    return new Crime(in);
		}
		
		public Crime[] newArray(int size) {
		    return new Crime[size];
		}
	};
	
	private Crime(Parcel in) {
		crimeDate = in.readString();
		id = in.readString();
		catagory = in.readString();
		bussiness = in.readString();
		county = in.readString();
		catagorie = in.readString();
		crimegroup = in.readString();
		description = in.readString();
		location = in.readString();
		city = in.readString();
		state = in.readString();
		zipcode = in.readString();
		service = in.readString();
		accuracy = in.readString();
		coordinates_latlong = in.readString();
		timestamp = in.readString();
	}
}
