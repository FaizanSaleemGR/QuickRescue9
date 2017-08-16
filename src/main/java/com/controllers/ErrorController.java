package com.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.entities.Contact;
import com.utils.Utils;

@ManagedBean(name="errorController")
@ViewScoped
public class ErrorController implements Serializable {


	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	String error = "";


	// Method to retrieve error message details
	public void retrieveErrorMessageDetails() {
		Utils.redirectTo("error.xhtml");
	}

	public String checkForPageAccess() {

		System.out.println("In checkForPageAccess() - ErrorController");

		Contact contact  = (Contact) Utils.getFromSession("contact");

		if(contact == null) {
			return "login?faces-redirect=true";
		}

		if(Utils.getCurrentPageName().equals("ViewAllAccounts") && contact != null && !contact.getAccount().getName().equals("QuickRescue") ) {
			error = "You are not permitted for requested resource.";
			return "error?faces-redirect=true";
		}

		return "";
	}

}
