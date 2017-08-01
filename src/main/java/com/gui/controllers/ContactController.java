package com.gui.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.ejb.services.AccountService;
import com.ejb.services.ContactService;
import com.jpa.entities.Account;
import com.jpa.entities.Contact;

@ManagedBean(name="contactController")
@ViewScoped
public class ContactController implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	AccountService accountService;

	@EJB
	ContactService contactService;

	private List<Contact> contactsList = new ArrayList<Contact>();


	@PostConstruct
    public void init()
	{
//        list = accountService.getAllAccounts();
//		list.add(new Account("FaizanSaleem", "faizan.com", "Taxila"));
		System.out.println("in Init of ContactController");
    }

	public void getAccountContacts() {

  	System.out.println("In getAccountContacts()");

  	ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	Map<String, Object> sessionMap = externalContext.getSessionMap();

  	Integer accountId = (Integer)sessionMap.get("accountId");

  	System.out.println("accountId = " + accountId);

  	Account acc = accountService.findAccountById(accountId);

  	contactsList.addAll(acc.getContacts());

  	if(contactsList!=null && contactsList.size()>0) {
			System.out.println("Contact List for id "+accountId + " retrieved");
		}
  }

    public List<Contact> getContactsList() {
		return contactsList;
	}



	public void setContactsList(List<Contact> contactsList) {
		this.contactsList = contactsList;
	}

}
