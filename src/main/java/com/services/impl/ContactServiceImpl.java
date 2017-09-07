package com.services.impl;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.dao.ContactDao;
import com.entities.Account;
import com.entities.AlertProfile;
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

	private Boolean loggedIn = false;

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
	public AlertProfile findAlertProfileById(Integer profileId) {
		return contactDao.findAlertProfileById(profileId);
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
	public Boolean deleteAlertProfileById(Integer profileId) {
		return contactDao.deleteAlertProfileById(profileId);
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
	public void updateAlertProfile(AlertProfile profile) {
		contactDao.updateAlertProfile(profile);
	}

	@Override
	public String loginContact(String contactUsername, String contactPassword) {
		Boolean check = null;
		String redirectTo = null;


		ContactLoginDetails contactLoginDetails = contactDao.getContactLogin(contactUsername, contactPassword);
			if(contactLoginDetails == null) {
				return null;
			}
		Contact contact = contactLoginDetails.getContact();

		if(contact != null) {
			if(contact.getHasLogin()) {
				if(contactUsername.equals(contactLoginDetails.getUsername()) && contactPassword.equals(contactLoginDetails.getPassword())) {

					// Redirect contact to ViewAllAccounts if it's a contact of QuickRescue account.
					if(contact.getAccount().getName().equals("QuickRescue")) {
						loggedIn = true;
						Utils.addToSession(
								new SimpleEntry<>("accountId", contact.getAccount().getAccountId()),
								new SimpleEntry<>("contact", contact),
								new SimpleEntry<>("loggedIn", loggedIn)
								);


						redirectTo = "ViewAllAccounts.xhtml";
					}
					// Redirect contact to ViewAllContacts if it's NOT a contact of QuickRescue account.
					else {
						System.out.println("Contact is not member of QuickRescue Account");

						loggedIn = true;
						Utils.addToSession(
								new SimpleEntry<>("accountId", contact.getAccount().getAccountId()),
								new SimpleEntry<>("contact", contact),
								new SimpleEntry<>("loggedIn", loggedIn)
								);

						redirectTo = "ViewAllContacts.xhtml";
					}



				}
			}
		}

		loggedIn = false;
		return redirectTo;
	}

	public Boolean isLoggedIn() {
		return loggedIn;
	}

	public void setLoggedIn(Boolean loggedIn) {
		this.loggedIn = loggedIn;
	}






}
