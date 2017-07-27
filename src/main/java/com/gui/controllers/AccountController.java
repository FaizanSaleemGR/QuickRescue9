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

@ManagedBean(name="accountController")
@ViewScoped
public class AccountController implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private AccountService accountService;
	@EJB
	private ContactService contactService;

	private List<Account> accountsList;
	private List<Contact> contactsList;
//    private transient DataModel<Account> model;
    private Account account;
    private int page = 1;

    private int currentAccountIndex;
    private Account newAccount;
    private Account accountToDelete;


    private List<Account> editedAccountsList;
    private Account editedAccount;

    private int editCounter;
    private int editedAccountId;


    /*Accessors and Mutators*/

    public List<Account> getEditedAccountsList() {
		return editedAccountsList;
	}




	public void setEditedAccountsList(List<Account> editedAccountsList) {
		this.editedAccountsList = editedAccountsList;
	}



	public List<Contact> getContactsList() {
		return contactsList;
	}




	public void setContactsList(List<Contact> contactsList) {
		this.contactsList = contactsList;
	}




	@PostConstruct
    public void init() {
//        list = accountService.getAllAccounts();
    	accountsList = new ArrayList<>();
//		list.add(new Account("FaizanSaleem", "faizan.com", "Taxila"));

    	accountsList.addAll(accountService.getAllAccounts());

		System.out.println("in Init");
		account = new Account();
		newAccount = new Account();
		editedAccount = new Account();


		editCounter = 0;


    }




    public int getCurrentAccountIndex() {
		return currentAccountIndex;
	}

	public void setCurrentAccountIndex(int currentAccountIndex) {
		this.currentAccountIndex = currentAccountIndex;
	}

	public Account getNewAccount() {
		return newAccount;
	}

	public void setNewAccount(Account newAccount) {
		this.newAccount = newAccount;
	}

	public Account getEditedAccount() {
		return editedAccount;
	}

	public void setEditedAccount(Account editedAccount) {
		this.editedAccount = editedAccount;
	}


	public List<Account> getAccountsList() {
		return accountsList;
	}

	public void setAccountsList(List<Account> accountsList) {
		this.accountsList = accountsList;
	}


    public String addAccount() {
    	System.out.println("In Add New Account");
    	accountService.addAccount(newAccount);
		accountsList.add(newAccount);
		newAccount = new Account(); // Reset placeholder.
//		UtilsBean.redirectTo("AllAccountsView.xhtml");
		return "/AllAccountsView.xhtml?faces-redirect=true";
    }

	public void setAccount(Account account) {
		this.account = account;
	}

	public void add() {
		accountService.addAccount(account);
		accountsList.add(account);
		account = new Account(); // Reset placeholder.
	}

    public void save() {
    	accountService.updateAccount(account);
    	account = new Account(); // Reset placeholder.
    }

    public String deleteAccount(int accountId) {

    	System.err.println("In Delete(int accountId)");

//    	accountService.deleteAccountByName(this.accountToDelete.getName());

    	accountToDelete = accountService.findAccountById(accountId);
    	accountService.deleteAccount(accountToDelete);

    	accountsList.remove(this.accountToDelete);

    	accountToDelete = new Account();

    	return "/AllAccountsView.xhtml?faces-redirect=true";

    }

      public void getContacts() {

    	System.out.println("In getContacts");

    	Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
    	Integer accountId = Integer.valueOf(parameterMap.get("accountId"));

    	System.out.println("accountId = " + accountId);

    	Account acc = accountService.findAccountById(accountId);

    	contactsList = contactService.getContactsOfAccount(acc);

    	if(contactsList!=null && contactsList.size()>0) {
			System.out.println("Contact List for id "+accountId + " retrieved");
		}
    }


      public void editAccount() {

    	  if(editCounter < 1) {

			System.out.println("In editAccount()");


			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			Map<String, String> parameterMap = externalContext.getRequestParameterMap();

			if(parameterMap!=null && parameterMap.size()>0) {
				editedAccountId = Integer.valueOf(parameterMap.get("accountId"));
			}

			System.out.println("accountId = " + editedAccountId);

			Account acc = accountService.findAccountById(editedAccountId);

			if (acc != null) {
				editedAccountsList = new ArrayList<>();
				editedAccountsList.add(acc);
				System.out.println("Found Account with id=" + acc.getAccountId());
			} else {
				System.out.println("Account not found with id=" + editedAccountId);
			}


      	editCounter++;

    	  }

      }

      public int getEditCounter() {
		return editCounter;
	}




	public void setEditCounter(int editCounter) {
		this.editCounter = editCounter;
	}




	public void saveAccount(Account acc) {

    	  System.out.println("In editAccount(Account)");

			accountService.updateAccount(acc);
			System.out.println("Updated Account with id=" + acc.getAccountId());


			editCounter--;

			UtilsBean.redirectTo("ViewAllAccounts.xhtml");
//			return "AllAccountsView.xhtml?faces-redirect=true";
      }



    public void deleteAccount(Account accountToDelete) {

    	System.err.println("In Delete()");

    	accountService.deleteAccountByName(this.accountToDelete.getName());
    	accountsList.remove(this.accountToDelete);
    }

    public Account getAccount() {
        return account;
    }

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public Account getAccountToDelete() {
		return accountToDelete;
	}

	public void setAccountToDelete(Account accountToDelete) {
		this.accountToDelete = accountToDelete;
	}




	public int getEditedAccountId() {
		return editedAccountId;
	}




	public void setEditedAccountId(int editedAccountId) {
		this.editedAccountId = editedAccountId;
	}

}
