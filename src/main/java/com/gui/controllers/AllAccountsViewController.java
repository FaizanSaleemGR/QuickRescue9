package com.gui.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.ejb.services.AccountService;
import com.jpa.entities.Account;

@ManagedBean(name="allAccountsViewController")
@ViewScoped
public class AllAccountsViewController implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@EJB
	private AccountService accountService;

	private List<Account> list;
//    private transient DataModel<Account> model;
    private Account account;
    private int page = 1;

    public List<Account> getList() {
		return list;
	}

	public void setList(List<Account> list) {
		this.list = list;
	}

	private boolean edit;

    @PostConstruct
    public void init() {
//        list = accountService.getAllAccounts();
    	list = new ArrayList<>();
//		list.add(new Account("FaizanSaleem", "faizan.com", "Taxila"));

		list.addAll(accountService.getAllAccounts());

		System.out.println("in Init");

    }

	public void setAccount(Account account) {
		this.account = account;
	}

	public void setEdit(boolean edit) {
		this.edit = edit;
	}

	public void add() {
		accountService.addAccount(account);
		list.add(account);
		account = new Account(); // Reset placeholder.
	}

	public void editAccount(Account account) {
		this.account = account;
		edit = true;
	}

    public void save() {
    	accountService.updateAccount(account);
    	account = new Account(); // Reset placeholder.
        edit = false;
    }

    public void delete(Account account) {
    	accountService.deleteAccountByName(account.getName());
    	list.remove(account);
    }


    public Account getAccount() {
        return account;
    }

    public boolean isEdit() {
        return edit;
    }

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
