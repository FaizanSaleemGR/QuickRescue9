package com.entities;

import java.io.Serializable;

public class ContactLoginDetails implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private Integer contactLoginId;	// PK of this class
	private Contact contact; // One to one relation between Contact and ContactLoginDetails



	private String username;
	private String password;

	public Integer getContactLoginId() {
		return contactLoginId;
	}
	public void setContactLoginId(Integer contactLoginId) {
		this.contactLoginId = contactLoginId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}


}
