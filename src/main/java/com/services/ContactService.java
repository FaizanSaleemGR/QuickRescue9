package com.services;

import java.util.List;

import javax.ejb.Local;

import com.entities.Account;
import com.entities.Contact;
import com.entities.ContactLoginDetails;

@Local
public interface ContactService {

	Integer addContact(Account account, Contact contact);
	Contact findContactByName(String contactFirstName, String contactLastName);
	Contact findContactById(Integer contactId);
	Boolean deleteContactByName(String contactFirstName, String contactLastName);
	Boolean deleteContactById(Integer contactId);
	List<Contact> getContactsOfAccount(Account account);
	void updateContact(Contact contact);
	Boolean loginContact(String contactUsername, String contactPassword);
	Integer addLoginDetails(Contact contact, ContactLoginDetails contactLoginDetails);
}
