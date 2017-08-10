package com.services.impl;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.dao.AccountDao;
import com.entities.Account;
import com.entities.AccountContract;
import com.services.AccountService;

@Stateless
public class AccountServiceImpl implements AccountService, Serializable   {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	@EJB
	private AccountDao accountDao;



	@Override
	public List<Account> getAllAccounts() {
		return accountDao.getAllAccounts();
	}


	@Override
	public Integer addAccount(Account account) {
		return accountDao.addAccount(account);
	}


	@Override
	public Account findAccountByName(String accountName) {
		return accountDao.findAccountByName(accountName);
	}


	@Override
	public Account findAccountById(Integer accountId) {
		return accountDao.findAccountById(accountId);
	}

	@Override
	public AccountContract findContractById(Integer contractId) {
		return accountDao.findContractById(contractId);
	}

	@Override
	public void deleteAccount(Account account) {
		accountDao.deleteAccount(account);
	}


	@Override
	public Boolean deleteAccountByName(String accountName) {
		return accountDao.deleteAccountByName(accountName);
	}


	@Override
	public Boolean deleteAccountById(Integer accountId) {
		return accountDao.deleteAccountById(accountId);
	}


	@Override
	public Boolean checkIfAccountExists(Account account) {
		return accountDao.checkIfAccountExists(account);
	}


	@Override
	public void updateAccount(Account account) {
		accountDao.updateAccount(account);
	}

	@Override
	public void updateContract(AccountContract contract) {
		accountDao.updateContract(contract);
	}


	@Override
	public void redirectToExternal(String pageName) {
		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect("http://google.com");
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	}


	@Override
	public void redirectToInternal(String pageName) {
		ExternalContext ec = FacesContext.getCurrentInstance()
		        .getExternalContext();
		try {
		    ec.redirect(ec.getRequestContextPath()
		            + "/" + pageName);
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}

	}


	@Override
	public List<AccountContract> getAllContracts() {
		return accountDao.getAllContracts();
	}
}
