package com.gui.controllers;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;

import com.ejb.services.ContactService;

@ManagedBean(name="contactLoginController")
public class ContactLoginController {


	@EJB
	private ContactService contactService;

	private String contactUsername;
	private String contactPassword;

	public String getContactUsername() {
		return contactUsername;
	}

	public void setContactUsername(String contactUsername) {
		this.contactUsername = contactUsername;
	}

	public String getContactPassword() {
		return contactPassword;
	}

	public void setContactPassword(String contactPassword) {
		this.contactPassword = contactPassword;
	}


	public void loginContact(String contactUsername, String contactPassword) {
		System.out.println("in loginContact(String, String)");
		contactService.loginContact(contactUsername, contactPassword);
	}

	public void loginContact() {
		System.out.println("in loginContact()");
	}
}
