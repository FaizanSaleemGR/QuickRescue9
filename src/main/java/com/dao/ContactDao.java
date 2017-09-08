package com.dao;

import java.util.List;

import javax.ejb.Local;

import com.entities.Account;
import com.entities.AlertProfile;
import com.entities.Contact;
import com.entities.ContactLoginDetails;

@Local
public interface ContactDao extends BaseDao<Contact> {

	Integer addContact(Account account, Contact contact);
	@Override
	Integer add(Contact contact);
	@Override
	void update(Contact contact);
	@Override
	void delete(Contact contact);
	Contact findContactByName(String contactFirstName, String contactLastName);
	Contact findContactById(Integer contactId);
	Boolean deleteContactByName(String contactFirstName, String contactLastName);
	Boolean deleteContactById(Integer contactId);
	List<Contact> getContactsOfAccount(Account account);
	void updateContact(Contact contact);
	ContactLoginDetails getContactLogin(String contactUsername, String contactPassword);
	Contact getContact(String firstName, String lastName);
	Integer addLoginDetails(Contact contact, ContactLoginDetails contactLoginDetails);
	void updateAlertProfile(AlertProfile profile);
	AlertProfile findAlertProfileById(Integer profileId);
	Boolean deleteAlertProfileById(Integer profileId);

	ContactLoginDetails checkExistingLoginDetails(Contact contact);
}
