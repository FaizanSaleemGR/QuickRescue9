package com.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AlertProfile implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer profileId; // PK of this class
	private String profileName;
	private Account account; // One to Many relation between Account and AlertProfile

	private boolean editable;

	private List<Location> locations;	// Many to Many relation between AlertProfile and Location

	public AlertProfile() {
		super();
		locations = new ArrayList<>();
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

	public List<Location> getLocations() {
		return locations;
	}
	public void setLocations(List<Location> locations) {
		this.locations = locations;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}

	public void addLocation(Location location) {
		this.locations.add(location);
	}
	public boolean getEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}


}
