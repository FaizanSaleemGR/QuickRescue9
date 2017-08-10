package com.controllers;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import com.services.ContactService;

@ManagedBean(name="contactLoginController")
@ViewScoped
public class ContactLoginController implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private ContactService contactService;

	private String contactUsername;
	private String contactPassword;

	private String errorMsg;

	public String loginContact(String contactUsername, String contactPassword) {
		FacesContext context = FacesContext.getCurrentInstance();
		System.out.println("in loginContact(String, String)");


		if(null==contactService.loginContact(contactUsername, contactPassword)) {
//			errorMsg = "Login Not Found.";
			context.addMessage(null, new FacesMessage("Unknown login, try again"));
		}

		return null;
	}

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

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
