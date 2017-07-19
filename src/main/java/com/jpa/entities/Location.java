package com.jpa.entities;

import java.util.Set;

public class Location {

	private int locationId;
	private int profileId;
	private String city;
	private String country;
	
	private Set<AlertProfile> profiles;

	public Location() {
		super();
	}

	public int getLocationId() {
		return locationId;
	}

	public void setLocationId(int locationId) {
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

	public int getProfileId() {
		return profileId;
	}

	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	
}
