package com.jpa.entities;

import java.io.Serializable;
import java.util.Set;

public class AlertProfile implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int locationId;
	private int profileId;
	private String profileName;
	private int accountId;
	private Set<Location> locations;
	private Account account;
	
	public AlertProfile() {
		super();
	}
	public int getProfileId() {
		return profileId;
	}
	public void setProfileId(int profileId) {
		this.profileId = profileId;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Set<Location> getLocations() {
		return locations;
	}
	public void setLocations(Set<Location> locations) {
		this.locations = locations;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
}
