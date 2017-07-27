package com.jpa.entities;

import java.io.Serializable;
import java.util.Set;

public class Account implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer accountId;
	private String name;
	private String emailDomain;
	private String timeZoneCity;
	private boolean canEdit = false;

	private Set<Contact> contacts;
	private Set<AlertProfile> alertProfiles;

	public Account() {

	}

	public Account(Integer accountId, String name, String emailDomain, String timeZoneCity, Set<Contact> contacts,
			Set<AlertProfile> alertProfiles) {
		super();
		this.accountId = accountId;
		this.name = name;
		this.emailDomain = emailDomain;
		this.timeZoneCity = timeZoneCity;
		this.contacts = contacts;
		this.alertProfiles = alertProfiles;
	}



	public Account(String name, String emailDomain, String timeZoneCity) {
		this.name = name;
		this.emailDomain = emailDomain;
		this.timeZoneCity = timeZoneCity;
	}

	public Set<Contact> getContacts() {
		return contacts;
	}

	public void setContacts(Set<Contact> contacts) {
		this.contacts = contacts;
	}

	public Set<AlertProfile> getAlertProfiles() {
		return alertProfiles;
	}

	public void setAlertProfiles(Set<AlertProfile> alertProfiles) {
		this.alertProfiles = alertProfiles;
	}

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailDomain() {
		return emailDomain;
	}

	public void setEmailDomain(String emailDomain) {
		this.emailDomain = emailDomain;
	}

	public String getTimeZoneCity() {
		return timeZoneCity;
	}

	public void setTimeZoneCity(String timeZoneCity) {
		this.timeZoneCity = timeZoneCity;
	}

	public boolean isCanEdit() {
		return canEdit;
	}

	public void setCanEdit(boolean canEdit) {
		this.canEdit = canEdit;
	}




}
