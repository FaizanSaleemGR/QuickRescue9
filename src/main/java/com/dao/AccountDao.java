package com.dao;

import java.util.List;

import javax.ejb.Local;

import com.jpa.entities.Account;

@Local
public interface AccountDao {

	List<Account> getAllAccounts();
	Integer addAccount(Account account);
	Account findAccountByName(String accountName);
	Account findAccountById(Integer accountId);
	Boolean deleteAccountByName(String accountName);
	Boolean deleteAccountById(Integer accountId);
	Boolean checkIfAccountExists(Account account);
	void updateAccount(Account account);
	void deleteAccount(Account account);
}
