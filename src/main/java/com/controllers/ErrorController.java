package com.controllers;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

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

		HttpServletRequest requestUrl = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
		String currentPageName = requestUrl.getServletPath().replaceAll("/", "").replaceAll(".xhtml", "");

		if(currentPageName.equals("ViewAllAccounts") && contact != null && !contact.getAccount().getName().equals("QuickRescue") ) {
			error = "You are not permitted for requested resource.";
			return "access_denied?faces-redirect=true";
		}

		return "";
	}

}
