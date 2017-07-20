package com.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dao.AccountDao;
import com.jpa.entities.Account;
import com.utils.HibernateUtils;

@Stateless
public class AccountDaoImpl implements AccountDao {


	private SessionFactory factory;
//	private EntityManagerFactory entityManagerFactory;

    @PersistenceContext(name="QuickRescue")
	private EntityManager entityManager;

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

//            entityManagerFactory = Persistence.createEntityManagerFactory("QuickRescue");





        }
        catch(Throwable e) {
        	System.err.println("Failed to create criteriaFactory object." + e);
        	throw new ExceptionInInitializerError(e);
        }
	}

	@Override
	public List<Account> getAllAccounts() {

		Session session = factory.openSession();
    	Transaction tx = null;
    	List<Account> accountList = null;

    	try {
    		tx = session.beginTransaction();

/*    		entityManager = entityManagerFactory.createEntityManager();
    		entityManager.getTransaction().begin();
    		accountList = entityManager.createQuery( "from Account", Account.class ).getResultList();
    		entityManager.getTransaction().commit();
    		entityManager.close();*/


    		Query query = session.createSQLQuery("Select * from account");
    		accountList = query.list();

    		tx.commit();

    	}
    	catch(HibernateException e) {
    		e.printStackTrace();
    	}
    	finally {
    		session.close();
    	}

    	return accountList;
	}

	@Override
	public Boolean checkIfAccountExists(Account account) {

		Session session = factory.openSession();
    	Transaction tx = null;
    	Boolean check = null;

    	try {
    		tx = session.beginTransaction();

    		Account checkAccount = (Account) session.load(Account.class, account.getAccountId());

    		if(checkAccount == null) {
				check = false;
			} else {
				check = true;
			}

    		tx.commit();
    	}
    	catch(HibernateException e) {

    		if(tx != null) {
				tx.rollback();
			}
    	}
    	finally {
    		if(session!=null) {
				session.close();
			}
    	}
		return check;
	}

	@Override
	public Integer addAccount(Account account) {

		Session session = factory.openSession();
    	Transaction tx = null;
    	Integer accountId = null;

    	try {
    		tx = session.beginTransaction();

    		if(!checkIfAccountExists(account)) {
				accountId = (Integer) session.save(account);
			}
    		tx.commit();
    	}
    	catch(HibernateException e) {
    		if(tx != null) {
				tx.rollback();
			}
    	}
    	finally {
    		if(session!=null) {
				session.close();
			}
    	}

    	return accountId;
	}

	@Override
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


    		if (tx != null) {
				tx.rollback();
			}
    	}
    	finally {
    		session.close();
    		return accountToReturn;
    	}

	}

	@Override
	public Account findAccountById(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteAccountByName(String accountName) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean deleteAccountById(Integer accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updateAccount(Account account) {
		// TODO Auto-generated method stub

	}


}
