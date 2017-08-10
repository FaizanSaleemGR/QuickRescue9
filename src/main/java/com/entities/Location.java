package com.entities;

import java.io.Serializable;
import java.util.Set;

public class Location implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer locationId;
	private String city;
	private String country;

	private Set<AlertProfile> profiles;

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

	public Set<AlertProfile> getProfiles() {
		return profiles;
	}

	public void setProfiles(Set<AlertProfile> profiles) {
		this.profiles = profiles;
	}


}
