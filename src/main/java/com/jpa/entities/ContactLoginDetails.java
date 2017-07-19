package com.jpa.entities;

public class ContactLoginDetails {
	
	private int contactLoginId;
	private String username;
	private String password;
	private int contactId;
	private Contact contact;
	
	public int getContactLoginId() {
		return contactLoginId;
	}
	public void setContactLoginId(int contactLoginId) {
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
	public int getContactId() {
		return contactId;
	}
	public void setContactId(int contactId) {
		this.contactId = contactId;
	}
	public Contact getContact() {
		return contact;
	}
	public void setContact(Contact contact) {
		this.contact = contact;
	}
	
	

}
