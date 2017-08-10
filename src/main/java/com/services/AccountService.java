package com.services;

import java.util.List;

import javax.ejb.Local;

import com.entities.Account;
import com.entities.AccountContract;

@Local
public interface AccountService {

	List<Account> getAllAccounts();
	List<AccountContract> getAllContracts();
	Integer addAccount(Account account);
	Account findAccountByName(String accountName);
	Account findAccountById(Integer accountId);
	Boolean deleteAccountByName(String accountName);
	Boolean deleteAccountById(Integer accountId);
	Boolean checkIfAccountExists(Account account);
	void updateAccount(Account account);


	void redirectToExternal(String pageName);
	void redirectToInternal(String pageName);
	void deleteAccount(Account account);
	void updateContract(AccountContract contract);
	AccountContract findContractById(Integer contractId);



}
