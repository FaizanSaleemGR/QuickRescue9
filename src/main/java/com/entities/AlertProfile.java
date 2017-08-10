package com.entities;

import java.io.Serializable;
import java.util.Set;

public class AlertProfile implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer profileId; // PK of this class
	private String profileName;
	private Account account; // One to Many relation between Account and AlertProfile

	private Set<Location> locations;

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
}
