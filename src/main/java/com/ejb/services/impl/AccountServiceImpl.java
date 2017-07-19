package com.ejb.services.impl;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.dao.AccountDao;
import com.dao.impl.AccountDaoImpl;
import com.ejb.services.AccountService;
import com.jpa.entities.Account;

@Stateless
public class AccountServiceImpl implements AccountService {

	@EJB
	private AccountDao accountDao;
	
	
	
	public List<Account> getAllAccounts() {
		return accountDao.getAllAccounts();
	}

	
	public Integer addAccount(Account account) {
		return accountDao.addAccount(account);
	}

	
	public Account findAccountByName(String accountName) {
		return accountDao.findAccountByName(accountName);
	}

	
	public Account findAccountById(Integer accountId) {
		return accountDao.findAccountById(accountId);
	}

	
	public Boolean deleteAccountByName(String accountName) {
		return accountDao.deleteAccountByName(accountName);
	}

	
	public Boolean deleteAccountById(Integer accountId) {
		return accountDao.deleteAccountById(accountId);
	}

	
	public Boolean checkIfAccountExists(Account account) {
		return accountDao.checkIfAccountExists(account);
	}

	
	public void updateAccount(Account account) {
		accountDao.updateAccount(account);
	}

	
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
}
