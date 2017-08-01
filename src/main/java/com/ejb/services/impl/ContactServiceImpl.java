package com.ejb.services.impl;

import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.dao.ContactDao;
import com.ejb.services.ContactService;
import com.gui.controllers.UtilsBean;
import com.jpa.entities.Account;
import com.jpa.entities.Contact;
import com.jpa.entities.ContactLoginDetails;

@Stateless
public class ContactServiceImpl implements ContactService {

	@EJB
	private ContactDao contactDao;

	public ContactServiceImpl() {

	}

	@Override
	public Integer addContact(Account account, Contact contact) {
		return contactDao.addContact(account, contact);
	}


	@Override
	public Contact findContactByName(String contactFirstName, String contactLastName) {
		return contactDao.findContactByName(contactFirstName, contactLastName);
	}


	@Override
	public Contact findContactById(Integer contactId) {
		return contactDao.findContactById(contactId);
	}


	@Override
	public Boolean deleteContactByName(String contactFirstName, String contactLastName) {
		return contactDao.deleteContactByName(contactFirstName, contactLastName);
	}


	@Override
	public Boolean deleteContactById(Integer contactId) {
		return contactDao.deleteContactById(contactId);
	}


	@Override
	public List<Contact> getContactsOfAccount(Account account) {
		return contactDao.getContactsOfAccount(account);
	}


	@Override
	public void updateContact(Contact contact) {
		contactDao.updateContact(contact);
	}


	@Override
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

					if(contact.getAccount().getName().equals("QuickRescue")) {
						UtilsBean.redirectTo("ViewAllAccounts.xhtml");
					} else {
						System.out.println("Contact is not member of QuickRescue Account");
						ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
						Map<String, Object> sessionMap = externalContext.getSessionMap();
						sessionMap.put("accountId", contact.getAccount().getAccountId());
						UtilsBean.redirectTo("ViewAllContacts.xhtml");
					}
				}
			}
		}
		return check;
	}






}
