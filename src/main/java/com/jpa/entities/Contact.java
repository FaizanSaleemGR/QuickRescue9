package com.jpa.entities;

import java.io.Serializable;

import com.dao.AccountDao;
import com.dao.impl.AccountDaoImpl;

public class Contact implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer contactLoginId;
	private Integer contactId;

	private String firstName;
	private String lastName;
	private String emailAddress;
	private char gender;
	private String phone;
	private Integer status;
	private String streetAddress;
	private String city;
	private String state;
	private String country;

	private Integer accountId;
	private Boolean hasLogin;

	public Account account;
	private ContactLoginDetails loginDetails;

	public Contact() {
		super();
	}

	public Contact(String firstName, String lastName, String emailAddress, char gender, String phone,
			Integer status, String streetAddress, String city, String state, String country, Account account) {
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
			Integer status, String streetAddress, String city, String state, String country) {
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
			Integer status, String streetAddress, String city, String state, String country, String accountName) {
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
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
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

	public Integer getAccountId() {
		return accountId;
	}

	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
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

	public Integer getContactLoginId() {
		return contactLoginId;
	}

	public void setContactLoginId(Integer contactLoginId) {
		this.contactLoginId = contactLoginId;
	}

}
