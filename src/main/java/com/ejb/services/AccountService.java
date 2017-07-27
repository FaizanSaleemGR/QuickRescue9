package com.ejb.services;

import java.util.List;

import javax.ejb.Local;

import com.jpa.entities.Account;

@Local
public interface AccountService {

	List<Account> getAllAccounts();
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



}
