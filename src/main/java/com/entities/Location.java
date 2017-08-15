package com.entities;

import java.io.Serializable;
import java.util.List;

public class Location implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer locationId;	// PK of this class
	private String city;
	private String country;

	private List<AlertProfile> profiles;	// Many to many relation between AlertProfile and Location.

	public Location() {
		super();
	}

	public Integer getLocationId() {
		return locationId;
	}

	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public List<AlertProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<AlertProfile> profiles) {
		this.profiles = profiles;
	}


}
