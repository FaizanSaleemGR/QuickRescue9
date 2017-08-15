package com.dao;

import java.util.List;

import javax.ejb.Local;

import com.entities.Account;
import com.entities.AccountContract;
import com.entities.AlertProfile;

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
	List<AccountContract> getAllContracts();
	void updateContract(AccountContract contract);
	AccountContract findContractById(Integer contractId);
	void addAlertProfile(AlertProfile alertProfile, Account account);
	List<AlertProfile> getAlertProfiles(Account account);
}
