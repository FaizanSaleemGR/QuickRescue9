package com.dao;

import java.util.List;

import javax.ejb.Local;

import com.jpa.entities.Account;
import com.jpa.entities.Contact;
import com.jpa.entities.ContactLoginDetails;

@Local
public interface ContactDao {

	Integer addContact(Account account, Contact contact);
	Contact findContactByName(String contactFirstName, String contactLastName);
	Contact findContactById(Integer contactId);
	Boolean deleteContactByName(String contactFirstName, String contactLastName);
	Boolean deleteContactById(Integer contactId);
	List<Contact> getContactsOfAccount(Account account);
	void updateContact(Contact contact);
	ContactLoginDetails getContactLogin(String contactUsername, String contactPassword);
	Contact getContact(String firstName, String lastName);
}
