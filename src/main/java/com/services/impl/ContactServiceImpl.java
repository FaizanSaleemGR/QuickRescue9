package com.services.impl;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.ContactDao;
import com.entities.Account;
import com.entities.Contact;
import com.entities.ContactLoginDetails;
import com.services.ContactService;
import com.utils.Utils;

@Stateless
public class ContactServiceImpl implements ContactService, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private ContactDao contactDao;

	public ContactServiceImpl() {

	}

	@Override
	public Integer addContact(Account account, Contact contact) {
		return contactDao.addContact(account, contact);
	}

	@Override
	public Integer addLoginDetails(Contact contact, ContactLoginDetails contactLoginDetails) {
		return contactDao.addLoginDetails(contact, contactLoginDetails);
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
		Boolean check = null;


		ContactLoginDetails contactLoginDetails = contactDao.getContactLogin(contactUsername, contactPassword);
			if(contactLoginDetails == null) {
				return check;
			}
		Contact contact = contactLoginDetails.getContact();

		if(contact != null) {
			check = false;
			if(contact.getHasLogin()) {
				check = false;
				if(contactUsername.equals(contactLoginDetails.getUsername()) && contactPassword.equals(contactLoginDetails.getPassword())) {
					check = true;


					// Redirect contact to ViewAllAccounts if it's a contact of QuickRescue account.
					if(contact.getAccount().getName().equals("QuickRescue")) {

						Utils.addToSession(new SimpleEntry<>("contact", contact));

						Utils.redirectTo("ViewAllAccounts.xhtml");
//						return "ViewAllAccounts.xhtml?faces-redirect=true";
					}
					// Redirect contact to ViewAllContacts if it's NOT a contact of QuickRescue account.
					else {
						System.out.println("Contact is not member of QuickRescue Account");

						Utils.addToSession(
								new SimpleEntry<>("accountId", contact.getAccount().getAccountId())
								);
						Utils.addToSession(new SimpleEntry<>("contact", contact));

						Utils.redirectTo("ViewAllContacts.xhtml");
					}
				}
			}
		}
		return check;
	}






}
