package com.jpa.entities;

import java.io.Serializable;
import java.util.Set;

public class AlertProfile implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer locationId;
	private Integer profileId;
	private String profileName;
	private Integer accountId;
	private Set<Location> locations;
	private Account account;

	public AlertProfile() {
		super();
	}
	public Integer getProfileId() {
		return profileId;
	}
	public void setProfileId(Integer profileId) {
		this.profileId = profileId;
	}
	public String getProfileName() {
		return profileName;
	}
	public void setProfileName(String profileName) {
		this.profileName = profileName;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
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
	public Integer getLocationId() {
		return locationId;
	}
	public void setLocationId(Integer locationId) {
		this.locationId = locationId;
	}
}
