package com.ejb.services.impl;

import java.io.IOException;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.dao.ContactDao;
import com.ejb.services.ContactService;
import com.jpa.entities.Account;
import com.jpa.entities.Contact;
import com.jpa.entities.ContactLoginDetails;

@Stateless
public class ContactServiceImpl implements ContactService {

	@EJB
	private ContactDao contactDao;
	
	public ContactServiceImpl() {
		
	}
	
	public Integer addContact(Account account, Contact contact) {
		return contactDao.addContact(account, contact);
	}

	
	public Contact findContactByName(String contactFirstName, String contactLastName) {
		return contactDao.findContactByName(contactFirstName, contactLastName);
	}

	
	public Contact findContactById(Integer contactId) {
		return contactDao.findContactById(contactId);
	}

	
	public Boolean deleteContactByName(String contactFirstName, String contactLastName) {
		return contactDao.deleteContactByName(contactFirstName, contactLastName);
	}

	
	public Boolean deleteContactById(Integer contactId) {
		return contactDao.deleteContactById(contactId);
	}

	
	public Set<Contact> getContactsOfAccount(Account account) {
		return contactDao.getContactsOfAccount(account);
	}

	
	public void updateContact(Contact contact) {
		contactDao.updateContact(contact);
	}


	public void redirectTo(String url) {
//		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, url);
		
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext(); 
		try {
			externalContext.redirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}

	
	}

	

	public Boolean loginContact(String contactUsername, String contactPassword) {
		
		ContactLoginDetails contactLoginDetails = contactDao.getContactLogin(contactUsername, contactPassword);
		Contact contact = contactLoginDetails.getContact();
		
		Boolean check = null;
		
		if(contact != null) {
			check = false;
			if(contact.getHasLogin()) {
				check = false;
				if(contactUsername.equals(contactLoginDetails.getUsername()) && contactPassword.equals(contactLoginDetails.getPassword())) {
					check = true;
					
					if(contact.getAccount().getName().equals("QuickRescue"))
						redirectTo("AllAccountsView.xhtml");
					else
						redirectTo("AllContactsView.xhtml");
				}
			}
		}
		
		return check;
	}

}
