package com.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import com.dao.AccountDao;
import com.jpa.entities.Account;
import com.utils.HibernateUtils;

@Stateless
public class AccountDaoImpl implements AccountDao {
	
	
	private SessionFactory factory;

    public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}
	
	public AccountDaoImpl() {
		try {
        	System.out.println(System.getProperty("user.dir"));
//        	factory = new Configuration().configure().buildSessionFactory();
        	
        	factory = HibernateUtils.getSessionFactory();
        	
        }
        catch(Throwable e) {
        	System.err.println("Failed to create criteriaFactory object." + e);
        	throw new ExceptionInInitializerError(e);
        }
	}

	public List<Account> getAllAccounts() {
		
		Session session = factory.openSession();
    	Transaction tx = null;
    	List<Account> accountList = null;
    	
    	try {
    		/*
    		tx = session.beginTransaction();
    		CriteriaBuilder crBuilder = session.getCriteriaBuilder();
    		CriteriaQuery<Account> criteria = crBuilder.createQuery(Account.class);
    		
    		Root<Account> accountRoot = criteria.from(Account.class);
    		criteria.select(accountRoot);
    		
    		accountList = session.createQuery(criteria).getResultList();
    		
    		tx.commit();
    		*/
    	}
    	catch(HibernateException e) {
    		if (tx != null)
    			tx.rollback();
    	}
    	finally {
    		session.close();
    	}
    	
    	return accountList;
	}
	
	public Boolean checkIfAccountExists(Account account) {
		
		Session session = factory.openSession();
    	Transaction tx = null;
    	Boolean check = null;
    	
    	try {
    		tx = session.beginTransaction();
    		
    		Account checkAccount = (Account) session.load(Account.class, account.getAccountId());    		
    		
    		if(checkAccount == null)
    			check = false;
    		else
    			check = true;
    		
    		tx.commit();
    	}
    	catch(HibernateException e) {
    		
    		if(tx != null)
    			tx.rollback();
    	}
    	finally {
    		if(session!=null)
    			session.close();
    	}
		return check;
	}

	public Integer addAccount(Account account) {
		
		Session session = factory.openSession();
    	Transaction tx = null;
    	Integer accountId = null;

    	try {
    		tx = session.beginTransaction();
    		
    		if(!checkIfAccountExists(account))
    			accountId = (Integer) session.save(account);
    		tx.commit();
    	}
    	catch(HibernateException e) {
    		if(tx != null)
    			tx.rollback();
    	}
    	finally {
    		if(session!=null)
    			session.close();
    	}
    	
    	return accountId;
	}

	public Account findAccountByName(String accountName) {
		Session session = factory.openSession();
    	Transaction tx = null;
    
    	Account accountToReturn = null;
    	
    	try {
    		/*
    		
    		tx = session.beginTransaction();
    		CriteriaBuilder crBuilder = session.getCriteriaBuilder();
    		CriteriaQuery<Account> criteria = crBuilder.createQuery(Account.class);
    		
    		Root<Account> accountRoot = criteria.from(Account.class);
    		criteria.select(accountRoot);
    		
    		criteria.where(crBuilder.equal(accountRoot.get("name"), accountName));
    		
    		accountToReturn = session.createQuery(criteria).getSingleResult();
    		
    		tx.commit();
    		*/
    	}
    	catch(HibernateException e) {
    		
    		System.err.println(e.getMessage());
    		
    		
    		if (tx != null)
    			tx.rollback();
    	}
    	finally {
    		session.close();
    		return accountToReturn;
    	}
		
	}

	public Account findAccountById(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteAccountByName(String accountName) {
		// TODO Auto-generated method stub
		return null;
	}

	public Boolean deleteAccountById(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	public void updateAccount(Account account) {
		// TODO Auto-generated method stub
		
	}


}
