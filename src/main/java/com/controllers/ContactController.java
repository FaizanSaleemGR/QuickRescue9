package com.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.entities.Account;
import com.entities.Contact;
import com.entities.ContactLoginDetails;
import com.services.AccountService;
import com.services.ContactService;
import com.utils.Utils;

@ManagedBean(name = "contactController")
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

	private List<Contact> contactsList = new ArrayList<>();
	private Integer accountId;
	private Contact newContact;
	private Account account;
	private int editCounter;

	private ArrayList<Contact> editedAccountsList;

	private int editContactId = -1;

	@PostConstruct
	public void init() {
		// list = accountService.getAllAccounts();
		// list.add(new Account("FaizanSaleem", "faizan.com", "Taxila"));
		System.out.println("in Init of ContactController");

		newContact = new Contact();
		account = new Account();
		editCounter = 0;

		getAccountContacts();

	}

	// Method to fetch contacts list using an account id received via POST parameters on ViewAllContacts.xhtml
	public void getAccountContacts() {

		if (contactsList.size() == 0) {

			System.out.println("In getAccountContacts()");

			this.accountId = (Integer) Utils.getFromSession("accountId");

			System.out.println("getAccountContacts() - accountId = " + accountId);

			this.account = accountService.findAccountById(accountId);

			contactsList.addAll(this.account.getContacts());

			if (contactsList != null && contactsList.size() > 0) {
				System.out.println("Contact List for id " + accountId + " retrieved");
			}
		}
	}


	// Method used to add Contact using Modal in ViewAllContacts.xhtml
	public void addContact() {
	    	System.out.println("In Add New Contact");
	    	ContactLoginDetails contactLoginDetails = new ContactLoginDetails();

	    	// Adding ContactLoginDetails to a contact
	    	if(this.newContact.getHasLogin()) {
	    		contactLoginDetails = Utils.createLogin(account, newContact);
	    		newContact.setLoginDetails(contactLoginDetails);

	    	}

	    	// Adding contact to the associated account
	    	Integer contactId = contactService.addContact(this.account, this.newContact);
	    	System.out.println("Contact Added with Id=" + contactId);

	    	// Add Contact to ContactLoginDetails for bi-directional purpose.
	    	contactService.addLoginDetails(newContact, contactLoginDetails);




	    	if(contactsList.add(newContact)) {
				System.out.println("Contact successfully added to contacts list");
			}

	    	newContact = new Contact(); // Reset placeholder.
			Utils.redirectTo("ViewAllContacts.xhtml");
	    }



	 // Method to delete a contact from ViewAllContacts.xhtml page.
    public void deleteContact(Integer contactId) {

    	System.err.println("In deleteContact(int contactId) - contactId=" + contactId);
    	contactsList.remove(contactService.findContactById(contactId));
    	contactService.deleteContactById(contactId);

    	Utils.redirectTo("ViewAllContacts.xhtml");
    }

    // Method to get the details of an editable account and display in EditAccount.xhtml page.
    public void editContact() {
	  	  if(editCounter < 1) {

				System.out.println("In editContact()");

				editContactId = Integer.valueOf(Utils.getFromRequest("editContactId"));

				System.out.println("editContact() - contactId = " + editContactId);

//				Contact contact = contactService.findContactById(editContactId);
				Contact contact = contactsList.stream().filter(x-> x.getContactId()==editContactId).findFirst().get();


				if (contact != null) {
					editedAccountsList = new ArrayList<>();
					editedAccountsList.add(contact);
					System.out.println("Found Contact with id=" + contact.getContactId());
				} else {
					System.out.println("Contact not found with id=" + editContactId);
				}


	    	editCounter++;

	  	  }

    }

    // Method to actually save the edited account in EditAccount.xhtml page.
    public void saveContact(Contact contact) {

  	  System.out.println("In editAccount(Contact)");

			contactService.updateContact(contact);
			System.out.println("Updated Contact with id=" + contact.getContactId());

			editCounter--;

			Utils.redirectTo("ViewAllContacts.xhtml");
    }


	public String contactEditAction(Contact contact) {

		if(!contact.getEditable()) {
			contact.setEditable(true);
		} else {
			contactSaveAction(contact);
		}

		return null;
	}

	public String contactSaveAction(Contact contact) {

		//get all existing value but set "editable" to false

			// Set editable to false
			contact.setEditable(false);

			// As the contact now has "hasLogin" true, so we need to create its login details.
			if(contact.getHasLogin()) {
				ContactLoginDetails contactLoginDetails = Utils.createLogin(account, contact);
				contact.setLoginDetails(contactLoginDetails);
				contactService.addLoginDetails(contact, contactLoginDetails);
			}

			contactService.updateContact(contact);

		//return to current page
		return null;

	}










	public List<Contact> getContactsList() {
		return contactsList;
	}

	public void setContactsList(List<Contact> contactsList) {
		this.contactsList = contactsList;
	}

	public Contact getNewContact() {
		return newContact;
	}

	public void setNewContact(Contact newContact) {
		this.newContact = newContact;
	}

	public ArrayList<Contact> getEditedAccountsList() {
		return editedAccountsList;
	}

	public void setEditedAccountsList(ArrayList<Contact> editedAccountsList) {
		this.editedAccountsList = editedAccountsList;
	}

	public int getEditContactId() {
		return editContactId;
	}

	public void setEditContactId(int editContactId) {
		this.editContactId = editContactId;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}


}
