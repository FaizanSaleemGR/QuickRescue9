package com.entities;

import java.io.Serializable;

import com.dao.AccountDao;
import com.dao.impl.AccountDaoImpl;

public class Contact implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer contactId; // PK of this class
	public Account account; // One to many relation between Account and Contact
	private ContactLoginDetails loginDetails; // One to One relation between Contact and ContactLoginDetails

	private String firstName;
	private String lastName;
	private String emailAddress;
	private char gender;
	private String phone;
	private Boolean status;
	private String streetAddress;
	private String city;
	private String state;
	private String country;
	private Boolean hasLogin;


	private boolean editable;

	public Contact() {
		super();
	}

	public Contact(String firstName, String lastName, String emailAddress, char gender, String phone,
			Boolean status, String streetAddress, String city, String state, String country, Account account) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.gender = gender;
		this.phone = phone;
		this.status = status;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.country = country;
		this.account = account;
	}

	public Contact(String firstName, String lastName, String emailAddress, char gender, String phone,
			Boolean status, String streetAddress, String city, String state, String country) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.gender = gender;
		this.phone = phone;
		this.status = status;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.country = country;
	}

	public Contact(String firstName, String lastName, String emailAddress, char gender, String phone,
			Boolean status, String streetAddress, String city, String state, String country, String accountName) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.emailAddress = emailAddress;
		this.gender = gender;
		this.phone = phone;
		this.status = status;
		this.streetAddress = streetAddress;
		this.city = city;
		this.state = state;
		this.country = country;

		AccountDao utils = new AccountDaoImpl();
		this.account = utils.findAccountByName(accountName);
	}

	public Integer getContactId() {
		return contactId;
	}
	public void setContactId(Integer contactId) {
		this.contactId = contactId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public char getGender() {
		return gender;
	}
	public void setGender(char gender) {
		this.gender = gender;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public Boolean  getStatus() {
		return status;
	}
	public void setStatus(Boolean status) {
		this.status = status;
	}
	public String getStreetAddress() {
		return streetAddress;
	}
	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
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
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Account getAccount() {
		return account;
	}
	public void setAccount(Account account) {
		this.account = account;
	}

	public Boolean getHasLogin() {
		return hasLogin;
	}

	public void setHasLogin(Boolean hasLogin) {
		this.hasLogin = hasLogin;
	}

	public ContactLoginDetails getLoginDetails() {
		return loginDetails;
	}

	public void setLoginDetails(ContactLoginDetails loginDetails) {
		this.loginDetails = loginDetails;
	}

	public boolean getEditable() {
		return editable;
	}

	public void setEditable(boolean editable) {
		this.editable = editable;
	}

}
