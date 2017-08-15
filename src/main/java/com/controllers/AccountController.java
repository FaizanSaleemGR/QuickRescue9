package com.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.entities.Account;
import com.entities.AccountContract;
import com.entities.Contact;
import com.services.AccountService;
import com.services.ContactService;
import com.utils.Utils;

@ManagedBean(name = "accountController")
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

	// private transient DataModel<Account> model;
	private Account account;
	private int page = 1;

	private int currentAccountIndex;
	private Account newAccount;
	private Account accountToDelete;

	private List<Account> editedAccountsList;
	private List<AccountContract> editedContractsList;
	private Account editedAccount;
	private AccountContract editedContract;

	private String accountNameForNewContract;
	private Integer accountIdForNewContract;

	private int editCounter;
	private int editContractCounter;
	private int editedAccountId;
	private int editedContractId;

	private Map<Integer, String> accountsNamesList;

	private AccountContract accountContract;
	private List<AccountContract> contractsList;
//	private HttpServletRequest requestUrl = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//	private String currentPageName = requestUrl.getServletPath().replaceAll("/", "").replaceAll(".xhtml", "");

	@PostConstruct
	public void init() {
		accountsList = new ArrayList<>();
		contractsList = new ArrayList<>();

		accountsList.addAll(accountService.getAllAccounts());

//		accountsNamesList = accountsList.stream().map(x-> new SimpleEntry<Integer, String>(x.getAccountId(), x.getName()) ).collect(Collectors.toList());
		accountsNamesList = accountsList.stream().filter(x-> x.getAccountContract()==null). collect(Collectors.toMap(Account::getAccountId, Account::getName));

		contractsList.addAll(accountService.getAllContracts());

		System.out.println("in Init of AccountController");
		account = new Account();
		newAccount = new Account();
		editedAccount = new Account();
		accountContract = new AccountContract();

		editCounter = 0;
		editContractCounter = 0;

	}

	// Method used to add Account using Modal in ViewAllAccounts.xhtml
	public void addAccount() {
		System.out.println("In Add New Account");

//		accountContract.setAccount(newAccount);
		newAccount.setAccountContract(accountContract);
		accountService.addAccount(newAccount);
		accountsList.add(newAccount);
		newAccount = new Account(); // Reset placeholder.
		// return "/AllAccountsView.xhtml?faces-redirect=true";
		Utils.redirectTo("ViewAllAccounts.xhtml");
	}

	// Method to delete an account from ViewAllAccounts.xhtml page.
	public void deleteAccount(int accountId) {

		System.err.println("In Delete(int accountId)");

		// accountService.deleteAccountByName(this.accountToDelete.getName());

		accountToDelete = accountService.findAccountById(accountId);
		accountService.deleteAccount(accountToDelete);

		accountsList.remove(this.accountToDelete);

		accountToDelete = new Account();

		// return "/AllAccountsView.xhtml?faces-redirect=true";
		Utils.redirectTo("ViewAllAccounts.xhtml");

	}

	// Method to get contact list of an account whose id is sent to
	// ViewContacts.xhtml using a request parameter.
	public void getContacts() {

		System.out.println("In getContacts");

		Map<String, String> parameterMap = FacesContext.getCurrentInstance().getExternalContext()
				.getRequestParameterMap();
		Integer accountId = Integer.valueOf(parameterMap.get("accountId"));

		System.out.println("accountId = " + accountId);

		Account acc = accountService.findAccountById(accountId);

		contactsList = contactService.getContactsOfAccount(acc);

		if (contactsList != null && contactsList.size() > 0) {
			System.out.println("Contact List for id " + accountId + " retrieved");
		}
	}

	// Method to get the details of an editable account and display in
	// EditAccount.xhtml page.
	public void editAccount() {

		if (editCounter < 1) {

			System.out.println("In editAccount()");

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			Map<String, String> parameterMap = externalContext.getRequestParameterMap();

			if (parameterMap != null && parameterMap.size() > 0) {
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

	// Method to actually save the edited account in EditAccount.xhtml page.
	public void saveAccount(Account acc) {

		System.out.println("In editAccount(Account)");

		accountService.updateAccount(acc);
		System.out.println("Updated Account with id=" + acc.getAccountId());

		editCounter--;

		Utils.redirectTo("ViewAllAccounts.xhtml");
		// return "AllAccountsView.xhtml?faces-redirect=true";
	}

	public void addNewContract() {

		System.out.println("In addNewContract()");

		Account acc = accountsList.stream().filter(x -> x.getAccountId().equals(accountIdForNewContract)).findFirst()
				.get();

		acc.setAccountContract(accountContract);
//		accountContract.setAccount(acc);

		accountService.updateAccount(acc);

		Utils.redirectTo("ViewAllContracts.xhtml");
	}

	public void editContract() {

		if (editContractCounter < 1) {

			System.out.println("In editContract()");

			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

			Map<String, String> parameterMap = externalContext.getRequestParameterMap();

			if (parameterMap != null && parameterMap.size() > 0) {
				editedContractId = Integer.valueOf(parameterMap.get("contractId"));
			}

			System.out.println("contractId = " + editedContractId);

			AccountContract contract = accountService.findContractById(editedContractId);

			if (contract != null) {
				editedContractsList = new ArrayList<>();
				editedContractsList.add(contract);
				System.out.println("Found AccountContract with id=" + contract.getContractId());
			} else {
				System.out.println("AccountContract not found with id=" + editedContractId);
			}

			editContractCounter++;

		}

	}

	// Method to actually save the edited account in EditAccount.xhtml page.
	public void saveContract(AccountContract contract) {

		System.out.println("In saveContract(AccountContract)");

		accountService.updateContract(contract);
		System.out.println("Updated AccountContract with id=" + contract.getContractId());

		editContractCounter--;

		Utils.redirectTo("ViewAllContracts.xhtml");
	}



	public String contractEditAction(AccountContract contract) {

		if(!contract.getEditable()) {
			contract.setEditable(true);
		} else {
			contractSaveAction();
		}


		return null;
	}


	public String contractSaveAction() {

		//get all existing value but set "editable" to false
		for (AccountContract contract : contractsList){
			contract.setEditable(false);
			accountService.updateContract(contract);
		}
		//return to current page
		return null;

	}
	
	public Integer getCurrentNumberOfContactsOfAccount(Account account) {
		return account.getContacts().size();
	}








	public String accountEditAction(Account account) {

		if(!account.getEditable()) {
			account.setEditable(true);
		} else {
			accountSaveAction(account);
		}

		return null;
	}


	public String accountSaveAction(Account account) {

			account.setEditable(false);
			accountService.updateAccount(account);

			return null;
	}



	/*
	 *
	 * Accessors and Mutators
	 *
	 *
	 */

	public List<Account> getAccountsList() {
		return accountsList;
	}

	public List<Account> getEditedAccountsList() {
		return editedAccountsList;
	}

	public void setEditCounter(int editCounter) {
		this.editCounter = editCounter;
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

	public void setAccountsList(List<Account> accountsList) {
		this.accountsList = accountsList;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public int getEditCounter() {
		return editCounter;
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

	public AccountContract getAccountContract() {
		return accountContract;
	}

	public void setAccountContract(AccountContract accountContract) {
		this.accountContract = accountContract;
	}

	public List<AccountContract> getContractsList() {
		return contractsList;
	}

	public void setContractsList(List<AccountContract> contractsList) {
		this.contractsList = contractsList;
	}

	public String getAccountNameForNewContract() {
		return accountNameForNewContract;
	}

	public void setAccountNameForNewContract(String accountNameForNewContract) {
		this.accountNameForNewContract = accountNameForNewContract;
	}

	public List<AccountContract> getEditedContractsList() {
		return editedContractsList;
	}

	public void setEditedContactsList(List<AccountContract> editedContractsList) {
		this.editedContractsList = editedContractsList;
	}

	public AccountContract getEditedContract() {
		return editedContract;
	}

	public void setEditedContract(AccountContract editedContract) {
		this.editedContract = editedContract;
	}

	public int getEditedContractId() {
		return editedContractId;
	}

	public void setEditedContractId(int editedContractId) {
		this.editedContractId = editedContractId;
	}

	public int getEditContractCounter() {
		return editContractCounter;
	}

	public void setEditContractCounter(int editContractCounter) {
		this.editContractCounter = editContractCounter;
	}

	public Integer getAccountIdForNewContract() {
		return accountIdForNewContract;
	}

	public void setAccountIdForNewContract(Integer accountIdForNewContract) {
		this.accountIdForNewContract = accountIdForNewContract;
	}

	public Map<Integer, String> getAccountsNamesList() {
		return accountsNamesList;
	}

	public void setAccountsNamesList(Map<Integer, String> accountsNamesList) {
		this.accountsNamesList = accountsNamesList;
	}

}
