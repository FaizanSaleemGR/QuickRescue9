package com.controllers;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.entities.Contact;
import com.services.ContactService;
import com.utils.Utils;

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


	@PostConstruct
	public void init() {
	}

	public String loginContact(String contactUsername, String contactPassword) {
		String redirectTo = "";

		try {
			FacesContext context = FacesContext.getCurrentInstance();
			System.out.println("in loginContact(String, String)");
			Boolean loginCheck = null;

			// Check if login is not found i.e. it's null as per the logic in ContactServiceImpl.loginContact(...)
			if((redirectTo=contactService.loginContact(contactUsername, contactPassword))==null) {
				redirectTo = "";

				FacesMessage msg = new FacesMessage(Utils.getFromResourceBundle("messages", "invalid_login"), Utils.getFromResourceBundle("messages", "invalid_login_error"));
				msg.setSeverity(FacesMessage.SEVERITY_ERROR);
				context.addMessage(null, msg);
				loginCheck = false;


			} else { // If login is found.
				Utils.setSessionTimeOutInMinutes(60);	//Set session timeout to 60 mins.
				loginCheck = true;
			}

		}
		catch(Exception e) {
			System.err.println("redirectTo is empty in ContactLoginController.loginContact(String contactUsername, String contactPassword) -> " + e.getLocalizedMessage());
		}


		return redirectTo.replaceAll(".xhtml", "")+"?faces-redirect=true";
	}


	public void checkForLogin() {

		System.out.println("In checkForLogin()");
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

			Contact contact = (Contact) Utils.getFromSession("contact");

			if (contact != null) {
				if (contact.getAccount().getName().equals("QuickRescue")) {
					Utils.navigateTo("ViewAllAccounts.xhtml");
//					return "ViewAllAccounts.xhtml?faces-redirect=true";
				} else {
					Utils.navigateTo("ViewAllContacts.xhtml");
//					return "ViewAllContacts.xhtml?faces-redirect=true";
				}
			}
		else {
			Utils.navigateTo("login.xhtml");
//			return "login.xhtml?faces-redirect=true";
			// throw new NullPointerException("contact from Session is null");
		}
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
