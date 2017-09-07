package com.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.dao.AccountDao;
import com.entities.Account;
import com.entities.AccountContract;
import com.entities.AlertProfile;
import com.utils.HibernateUtils;

@Stateless
public class AccountDaoImpl implements AccountDao {


	private SessionFactory factory;
//	private EntityManagerFactory entityManagerFactory;

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

    		accountList = (List<Account>) session.createCriteria(Account.class)
    			    .list();

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
	public List<AccountContract> getAllContracts() {

		Session session = factory.openSession();
    	Transaction tx = null;
    	List<AccountContract> contractList = null;

    	try {
    		tx = session.beginTransaction();

    		contractList = (List<AccountContract>) session.createCriteria(AccountContract.class)
    			    .list();

    		tx.commit();

    	}
    	catch(HibernateException e) {
    		e.printStackTrace();
    	}
    	finally {
    		session.close();
    	}

    	return contractList;
	}

	@Override
	public Boolean checkIfAccountExists(Account account) {

		Session session = factory.openSession();
    	Transaction tx = null;
    	Boolean check = null;

    	try {
    		tx = session.beginTransaction();

    		Account checkAccount = (Account) session.load(Account.class, account.getName());

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

				accountId = (Integer) session.save(account);
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
	public Integer add(Account account) {
		return this.addAccount(account);
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
    	}

    	return accountToReturn;
	}

	@Override
	public Account findAccountById(Integer accountId) {

		Session session = factory.openSession();
    	Transaction tx = null;
    	SQLQuery query = null;
    	Account accountToReturn = null;

    	try {

    		tx = session.beginTransaction();

    		query = session.createSQLQuery("Select * from account where accountId=:accountId ");
    		query.setParameter("accountId", accountId);

//			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			query.addEntity(Account.class);

			List<Account> l = (List<Account>)query.list();


			if (l.size()>0) {
			  accountToReturn=l.get(0);
			}

    		tx.commit();
    	}
    	catch(HibernateException e) {

    		System.err.println(e.getMessage());


    		if (tx != null) {
				tx.rollback();
			}
    	}
    	finally {
    		session.close();
    	}

    	return accountToReturn;
	}



	@Override
	public AccountContract findContractById(Integer contractId) {

		Session session = factory.openSession();
    	Transaction tx = null;
    	SQLQuery query = null;
    	AccountContract contractToReturn = null;

    	try {

    		tx = session.beginTransaction();

    		query = session.createSQLQuery("Select * from account_contracts where contractId=:contractId");
    		query.setParameter("contractId", contractId);

//			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			query.addEntity(AccountContract.class);

			List<AccountContract> l = (List<AccountContract>)query.list();


			if (l.size()>0) {
				contractToReturn=l.get(0);
			}

    		tx.commit();
    	}
    	catch(HibernateException e) {

    		System.err.println(e.getMessage());


    		if (tx != null) {
				tx.rollback();
			}
    	}
    	finally {
    		session.close();
    	}

    	return contractToReturn;
	}


	@Override
	public void deleteAccount(Account account) {

		Session session = factory.openSession();
    	Transaction tx = null;

    	try {

    		tx = session.beginTransaction();

    		session.delete(account);

    		tx.commit();
    	}
    	catch(HibernateException e) {

    		System.err.println(e.getMessage());


    		if (tx != null) {
				tx.rollback();
			}
    	}
    	finally {
    		session.close();
    	}

	}

	@Override
	public void delete(Account account) {
		this.deleteAccount(account);
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

		Session session = factory.openSession();
    	Transaction tx = null;

    	try {

    		tx = session.beginTransaction();

    		session.update(account);
    		tx.commit();
    	}
    	catch(HibernateException e) {

    		System.err.println(e.getMessage());


    		if (tx != null) {
				tx.rollback();
			}
    	}
    	finally {
    		session.close();
    	}

	}

	@Override
	public void update(Account account) {
		this.update(account);
	}


	@Override
	public void updateContract(AccountContract contract) {

		Session session = factory.openSession();
    	Transaction tx = null;

    	try {

    		tx = session.beginTransaction();

    		session.update(contract);
    		tx.commit();
    	}
    	catch(HibernateException e) {

    		System.err.println(e.getMessage());


    		if (tx != null) {
				tx.rollback();
			}
    	}
    	finally {
    		session.close();
    	}

	}


	@Override
	public void addAlertProfile(AlertProfile alertProfile, Account account) {
		Session session = factory.openSession();
		Transaction tx = null;

		try {
			tx = session.beginTransaction();

			alertProfile.setAccount(account);

			session.save(alertProfile);

			account.getAlertProfiles().add(alertProfile);

			session.update(account);

			tx.commit();
		} catch (HibernateException e) {

			System.out.println(e.getMessage());

			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}


	@Override
	public List<AlertProfile> getAlertProfiles(Account account) {
		Session session = factory.openSession();
		Transaction tx = null;
		List<AlertProfile> alertProfiles = null;

		try {
			tx = session.beginTransaction();

			alertProfiles = account.getAlertProfiles();

			tx.commit();
		} catch (HibernateException e) {

			System.out.println(e.getMessage());

			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return alertProfiles;
	}

}
