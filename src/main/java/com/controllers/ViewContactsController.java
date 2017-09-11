package com.controllers;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import com.entities.Account;
import com.entities.AccountContract;
import com.entities.AlertProfile;
import com.entities.Contact;
import com.entities.ContactLoginDetails;
import com.entities.Location;
import com.services.AccountService;
import com.services.ContactService;
import com.utils.Utils;

@ManagedBean(name = "viewContactsController")
@ViewScoped
public class ViewContactsController implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	AccountService accountService;

	@EJB
	ContactService contactService;

	private List<Contact> contactsList = new ArrayList<>();
	private List<AlertProfile> alertProfiles = new ArrayList<>();
	private Integer accountId;
	private Contact newContact;
	private Account account;

	private Integer contactsLeft, loginsLeft;
	private String contactsAndLoginsLimit;
	private AlertProfile alertProfile;
	private Location location;
	private ArrayList<Location> locations;

	private Location editedLocation;

	private int editCounter;

	private ArrayList<Contact> editedAccountsList;

	private int editContactId = -1;

	private Map<Integer, String> accountsNamesList;
	private Integer accountIdForNewContract;
	private AccountContract accountContract;

	private List<AccountContract> activeContract;
	private List<AccountContract> inactiveContacts;

	private Contact loggedInContact;

	String minDate;

	@PostConstruct
	public void init() {
		System.out.println("in Init of ContactController");

		newContact = new Contact();
		account = new Account();
		alertProfile = new AlertProfile();
		location = new Location();
		locations = new ArrayList<>();
		editCounter = 0;
		accountContract = new AccountContract();
		loggedInContact = new Contact();

		activeContract = new ArrayList<>(1);
		inactiveContacts = new ArrayList<>();



		editedLocation = new Location();


		minDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();

		if (request.getRequestedSessionId() != null && request.isRequestedSessionIdValid()) {

		this.accountId = Integer.valueOf(Utils.getFromRequest("accountId"));

		if(this.accountId == -1 || this.accountId == null) {
			throw new NoSuchElementException("Couldn't get accountId from Session.");
		}
		else {
			System.out.println("Got from Request - accountId = " + accountId);
			this.account = accountService.findAccountById(accountId);


			this.countContactAndLoginLimit();

			alertProfiles = this.account.getAlertProfiles();
			getAccountContacts();

			ArrayList<Account> accNameList = new ArrayList<>();
			accNameList.add(account);
			accountsNamesList = accNameList.stream().collect(Collectors.toMap(Account::getAccountId, Account::getName));
			activeContract.add(this.account.getAccountContract());
			inactiveContacts.addAll(accountService.getInactiveContracts(account));


			loggedInContact = (Contact) Utils.getFromSession("contact");

		}
		}

	}

	public Integer getCurrentNumberOfContactsOfAccount(Account account) {
		return (account != null && account.getContacts() != null) ? account.getContacts().size() : 0;
	}

	public Integer getCurrentNumberOfLoginsOfAccount(Account account) {
		Integer loginsCount = 0;

		if(getCurrentNumberOfContactsOfAccount(account) != 0) {
			for(Contact contact : account.getContacts()) {
				if(contact.getHasLogin()) {
					loginsCount++;
				}
			}
		}

		return loginsCount;
	}


	public void addNewContract() {

		System.out.println("In addNewContract()");


		AccountContract currentContract = this.account.getAccountContract();

		// Assuming an account can not be created without a contract.
		if(new Date().compareTo(currentContract.getEndDate()) <= 0)
		{
			Utils.addFacesMessage("Active Contract Found", "An active contract is already associated with this account.", FacesMessage.SEVERITY_INFO);
		}
		else {

		accountContract.setAccount(this.account);
		this.account.setAccountContract(accountContract);
//		accountContract.setAccount(acc);

		accountService.updateAccount(this.account);
		}

//		Utils.navigateTo("ViewAllAccounts.xhtml");
	}


	public String contractEditAction(AccountContract contract) {

		if(!contract.getEditable()) {
			contract.setEditable(true);
		} else {
			contractSaveAction(contract);
		}


		return "";
	}


	public String contractSaveAction(AccountContract contract) {

		contract.setEditable(false);
		accountService.updateContract(contract);

		return "";
	}

	// Method to fetch contacts list using an account id received via POST parameters on ViewAllContacts.xhtml
	public void getAccountContacts() {

		if (contactsList.size() == 0) {

			System.out.println("In getAccountContacts()");



			contactsList.addAll(this.account.getContacts());

			if (contactsList != null && contactsList.size() > 0) {
				System.out.println("Contact List for id " + accountId + " retrieved");
			}
		}
	}


	public void checkForAlertProfileMatch(Contact contact, Account account) {

		for (AlertProfile alertProfile : account.getAlertProfiles()) {
			for(Location location : alertProfile.getLocations()) {
				if(null != contact.getCity() && contact.getCity().equals(location.getCity())) {
					System.out.println("Alert Profile sent for contact (" + contact.getFirstName()  + ", " + contact.getLastName()+ ") on matching City " + contact.getCity() +":"+location.getCity());
				}
				else if(null != contact.getCountry() && contact.getCountry().equals(location.getCountry())) {
					System.out.println("Alert Profile sent for contact (" + contact.getFirstName()  + ", " + contact.getLastName()+ ") on matching Country " + contact.getCountry() +":"+location.getCountry());
				}
			} // inner loop end
		} // outer loop end

	} // function end


	public boolean checkAccountContactsLimit(Account account) {


		if(null!= account.getAccountContract()) {
			if(account.getAccountContract().getContactsLimit() > account.getContacts().size()) {
				return true;
			}
		}
		else {
			System.out.println("No contract found for account " + account.getName() + " with id " + account.getAccountId());
		}

		return false;
	}

	public boolean checkAccountLoginsLimit(Account account) {

		if(null!= account.getAccountContract()) {
			// Count those contacts which has 'hasLogin' flag true.
			int loginsCount = 0;

			for (Contact c : account.getContacts()) {
				if (c.getHasLogin()) {
					loginsCount++;
				}
			}


			if(account.getAccountContract().getLoginsLimit() > loginsCount) {
				return true;
			}
		}
		else {
			System.out.println("No contract found for account " + account.getName() + " with id " + account.getAccountId());
		}

		return false;
	}


	// Method used to add Contact using Modal in ViewAllContacts.xhtml
	public String addContact() {
    	System.out.println("In Add New Contact");

		Account acc = accountService.findAccountById(account.getAccountId());


    	if(!this.checkAccountContactsLimit(this.account)) {
			System.out.println("Contact NOT added - Account's CONTACTS Limit Exceeded." );
		} else {
	    	ContactLoginDetails contactLoginDetails = new ContactLoginDetails();


	    	newContact.setEmailAddress(newContact.getEmailAddress().concat("@"+this.account.getEmailDomain()));

	    	// Adding contact to the associated account
	    	Integer contactId = contactService.addContact(this.account, this.newContact);
	    	System.out.println("Contact Added with Id=" + contactId);

	    	// Adding ContactLoginDetails to a contact
	    	if(this.newContact.getHasLogin()) {
	    		if(!this.checkAccountLoginsLimit(acc)) {
					System.out.println("Contact NOT added - Account's LOGINS Limit Exceeded." );
				}
	    		else {
		    		contactLoginDetails = Utils.createLogin(account, newContact);
		    		// Add Contact to ContactLoginDetails for bi-directional purpose.
			    	contactService.addLoginDetails(newContact, contactLoginDetails);
	    		}
	    	}

	    	if(contactsList.add(newContact)) {
				System.out.println("Contact successfully added to contacts list");
			}

	    	this.checkForAlertProfileMatch(this.newContact, this.account);
    	}

    	newContact = new Contact(); // Reset placeholder.
    	return "ViewContacts?accountId="+this.accountId+"&?faces-redirect=true";
    }



	 // Method to delete a contact from ViewAllContacts.xhtml page.
    public void deleteContact(Integer contactId) {

    	System.err.println("In deleteContact(int contactId) - contactId=" + contactId);
    	contactsList.remove(contactService.findContactById(contactId));
    	contactService.deleteContactById(contactId);

    	Utils.redirectTo("ViewContacts.xhtml?accountId="+this.accountId);
//    	return "ViewContacts?accountId="+this.accountId+"&?faces-redirect=true";
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
    public String saveContact(Contact contact) {

		System.out.println("In editAccount(Contact)");

		contactService.updateContact(contact);
		System.out.println("Updated Contact with id=" + contact.getContactId());

		editCounter--;

		// Utils.redirectTo("ViewAllContacts.xhtml");
		return "ViewContacts?accountId="+this.accountId+"&?faces-redirect=true";
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
		Account acc = accountService.findAccountById(account.getAccountId());

			// Set editable to false
			contact.setEditable(false);

			// As the contact now has "hasLogin" true, so we need to create its login details.
			if(contact.getHasLogin()) {

				if(!this.checkAccountLoginsLimit(acc)) {
					System.out.println("Login NOT created - Account's LOGINS Limit Exceeded." );
				} else {

					ContactLoginDetails contactLoginDetails;
					if((contactLoginDetails = contactService.checkExistingLoginDetails(contact)) == null)
					{
						contactLoginDetails = Utils.createLogin(account, contact);
						contact.setLoginDetails(contactLoginDetails);
						contactLoginDetails.setContact(contact);
						contactService.addLoginDetails(contact, contactLoginDetails);
					}
					else {
						contact.setLoginDetails(contactLoginDetails);
						contactLoginDetails.setContact(contact);
					}

						System.out.println("Login Updated/Created for contact " + contact.getFirstName() + " " + contact.getLastName() + " (" + contact.getContactId() + ") - " + contactLoginDetails.getUsername() + ":" + contactLoginDetails.getPassword() + " - id=" + contactLoginDetails.getContactLoginId());
				}
			}

			contactService.updateContact(contact);
			this.checkForAlertProfileMatch(contact, this.account);

		//return to current page
		return "";
	}


	 // Method to add city of a location from Add New Alert Profile form.
    public void addLocation(Location location) {

    	if (null==location.getCity() && null==location.getCountry()) {
    		System.out.println("In addLocation(Location location) - City and Country are Empty - Location not added");
    	}
    	else if (null==location.getCity()) {
    		System.out.println("In addLocation(Location location) - City is Empty - Location Added");
    		locations.add(location);
    	}
    	else if(null==location.getCountry()) {
    		System.out.println("In addLocation(Location location) - Country is Empty - Location Added");
    		locations.add(location);
    	}
    	else {
    		System.out.println("In addLocation(Location location) - " + location.getCity() + ", " + location.getCountry() + " - Location Added");
    		locations.add(location);
    	}

    	location = new Location();
    }


	 // Method to delete a location from Add New Alert Profile form.
    public void deleteLocation(Location location) {
    	System.out.println("In deleteLocation(Location location) - " + location.getCity() + ", " + location.getCountry());
    	locations.remove(location);
//    	location = new Location();
    }






    public void addLocationFromEditedProfile(AlertProfile profile, Location location) {

    	if (null==location.getCity() && null==location.getCountry()) {
    		System.out.println("In addLocation(Location location) - City and Country are Empty - Location not added");
    	}
    	else if (null==location.getCity()) {
    		System.out.println("In addLocation(Location location) - City is Empty - Location Added");
    		profile.getLocations().add(location);
    	}
    	else if(null==location.getCountry()) {
    		System.out.println("In addLocation(Location location) - Country is Empty - Location Added");
    		profile.getLocations().add(location);
    	}
    	else {
    		System.out.println("In addLocation(Location location) - " + location.getCity() + ", " + location.getCountry() + " - Location Added");
    		profile.getLocations().add(location);
    	}

    	location = new Location();
    }

    public void deleteLocationFromEditedProfile(AlertProfile profile, Location location) {
    	System.out.println("In deleteLocationFromEditedProfile(AlertProfile profile, Location location) - " + location.getCity() + ", " + location.getCountry());
    	profile.getLocations().remove(location);
//    	location = new Location();
    }


    public void addAlertProfile() {
    	System.out.println("In addNewAlertProfile()");

    	for(Location loc : locations) {
    		alertProfile.addLocation(loc);
    	}

//    	this.account.getAlertProfiles().add(alertProfile);

    	accountService.addAlertProfile(alertProfile, this.account);
    	alertProfile = new AlertProfile();

    	Utils.redirectTo("ViewContacts.xhtml?accountId="+this.accountId+"");
    }

    public void deleteAlertProfile(Integer profileId) {

    	System.err.println("In deleteContact(int contactId) - contactId=" + profileId);
    	alertProfiles.remove(contactService.findAlertProfileById(profileId));
    	contactService.deleteAlertProfileById(profileId);

    	Utils.redirectTo("ViewContacts.xhtml?accountId="+this.accountId+"");
    }






	public String alertProfileEditAction(AlertProfile profile) {

		if(!profile.getEditable()) {
			profile.setEditable(true);
		} else {
			alertProfileSaveAction(profile);
		}

		return null;
	}

	public String alertProfileSaveAction(AlertProfile profile) {

		// Set editable to false
		profile.setEditable(false);

		// As the contact now has "hasLogin" true, so we need to create its login
		// details.
		contactService.updateAlertProfile(profile);

		// return to current page
		return null;
	}








	public void countContactAndLoginLimit() {
		contactsLeft = account.getAccountContract().getContactsLimit() - account.getContacts().size();
		loginsLeft = (account.getAccountContract().getLoginsLimit() - (int) account.getContacts().stream().filter(x->x.getHasLogin()).count());
		this.contactsAndLoginsLimit = "You can create "+contactsLeft+" more contacts and "+loginsLeft+" more logins";
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

	public AlertProfile getAlertProfile() {
		return alertProfile;
	}

	public void setAlertProfile(AlertProfile alertProfile) {
		this.alertProfile = alertProfile;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public ArrayList<Location> getLocations() {
		return locations;
	}

	public void setLocations(ArrayList<Location> locations) {
		this.locations = locations;
	}

	public List<AlertProfile> getAlertProfiles() {
		return alertProfiles;
	}

	public void setAlertProfiles(List<AlertProfile> alertProfiles) {
		this.alertProfiles = alertProfiles;
	}

	public Location getEditedLocation() {
		return editedLocation;
	}

	public void setEditedLocation(Location editedLocation) {
		this.editedLocation = editedLocation;
	}

	public Map<Integer, String> getAccountsNamesList() {
		return accountsNamesList;
	}

	public void setAccountsNamesList(Map<Integer, String> accountsNamesList) {
		this.accountsNamesList = accountsNamesList;
	}

	public Integer getAccountIdForNewContract() {
		return accountIdForNewContract;
	}

	public void setAccountIdForNewContract(Integer accountIdForNewContract) {
		this.accountIdForNewContract = accountIdForNewContract;
	}

	public AccountContract getAccountContract() {
		return accountContract;
	}

	public void setAccountContract(AccountContract accountContract) {
		this.accountContract = accountContract;
	}

	public List<AccountContract> getActiveContract() {
		return activeContract;
	}

	public void setActiveContract(List<AccountContract> activeContract) {
		this.activeContract = activeContract;
	}

	public List<AccountContract> getInactiveContacts() {
		return inactiveContacts;
	}

	public void setInactiveContacts(List<AccountContract> inactiveContacts) {
		this.inactiveContacts = inactiveContacts;
	}

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public Integer getContactsLeft() {
		return contactsLeft;
	}

	public void setContactsLeft(Integer contactsLeft) {
		this.contactsLeft = contactsLeft;
	}

	public Integer getLoginsLeft() {
		return loginsLeft;
	}

	public void setLoginsLeft(Integer loginsLeft) {
		this.loginsLeft = loginsLeft;
	}

	public String getContactsAndLoginsLimit() {
		return contactsAndLoginsLimit;
	}

	public void setContactsAndLoginsLimit(String contactsAndLoginsLimit) {
		this.contactsAndLoginsLimit = contactsAndLoginsLimit;
	}

	public Contact getLoggedInContact() {
		return loggedInContact;
	}

	public void setLoggedInContact(Contact loggedInContact) {
		this.loggedInContact = loggedInContact;
	}


}
