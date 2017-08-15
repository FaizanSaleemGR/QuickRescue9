package com.entities;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class Account implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer accountId; // PK of This Class
	private String name;
	private String emailDomain;
	private String timeZoneCity;
	private Set<Contact> contacts; // One to Many relation between Account and Contact
	private List<AlertProfile> alertProfiles; // One to Many relation between Account and AlertProfile

	private AccountContract accountContract; // One to one relation between Account and AccountContract


	private boolean editable;

	public Account() {
		super();
	}

	public Account(Integer accountId, String name, String emailDomain, String timeZoneCity, Set<Contact> contacts,
			List<AlertProfile> alertProfiles) {
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

	public AccountContract getAccountContract() {
		return accountContract;
	}

	public void setAccountContract(AccountContract accountContract) {
		this.accountContract = accountContract;
	}

	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

	public List<AlertProfile> getAlertProfiles() {
		return alertProfiles;
	}

	public void setAlertProfiles(List<AlertProfile> alertProfiles) {
		this.alertProfiles = alertProfiles;
	}


}
