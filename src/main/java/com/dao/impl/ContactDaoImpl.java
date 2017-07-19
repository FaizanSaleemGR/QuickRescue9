package com.dao.impl;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import com.dao.ContactDao;
import com.jpa.entities.Account;
import com.jpa.entities.Contact;
import com.jpa.entities.ContactLoginDetails;
import com.utils.HibernateUtils;

@Stateless
public class ContactDaoImpl implements ContactDao {

	private SessionFactory factory;
	private HibernateUtils utils = new HibernateUtils();

	@PersistenceContext(name = "QuickRescue")
	private EntityManager entityManager;

	public SessionFactory getFactory() {
		return factory;
	}

	public void setFactory(SessionFactory factory) {
		this.factory = factory;
	}

	public ContactDaoImpl() {
		try {
			System.out.println(System.getProperty("user.dir"));
			factory = utils.getSessionFactory();

		} catch (Throwable e) {
			System.err.println("Failed to create criteriaFactory object." + e);
			throw new ExceptionInInitializerError(e);
		}
	}

	public Integer addContact(Account account, Contact contact) {

		Session session = factory.openSession();
		Transaction tx = null;
		Integer contactId = null;

		try {
			tx = session.beginTransaction();

			contact.setAccount(account);
			account.getContacts().add(contact);
			session.saveOrUpdate(account);

			// contactId = (Integer) session.save(account);
			tx.commit();
		} catch (HibernateException e) {

			System.err.println(e.getLocalizedMessage());

			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

		return contactId;

	}

	public Contact findContactByName(String contactFirstName, String contactLastName) {

		Session session = factory.openSession();
		Transaction tx = null;

		Contact contactToReturn = null;

		try {

			tx = session.beginTransaction();

			/*
			 * CriteriaBuilder crBuilder = session.getCriteriaBuilder();
			 * CriteriaQuery<Contact> criteria = crBuilder.createQuery(Contact.class);
			 * 
			 * Root<Contact> contactRoot = criteria.from(Contact.class);
			 * criteria.select(contactRoot);
			 * 
			 * 
			 * Predicate nameRestriction = crBuilder.and( crBuilder.equal(
			 * contactRoot.get("firstName"), contactFirstName ), crBuilder.equal(
			 * contactRoot.get("lastName"), contactLastName ) );
			 * 
			 * criteria.where(nameRestriction);
			 * 
			 * contactToReturn = session.createQuery(criteria).getSingleResult();
			 */

			contactToReturn = (Contact) entityManager
					.createNativeQuery("SELECT * " + "FROM contact WHERE first_name = '" + contactFirstName
							+ "' and last_name = '" + contactLastName + "'", Contact.class)
					.getSingleResult();

			tx.commit();

		} catch (Exception e) {

			System.err.println(e.getMessage());

			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

		return contactToReturn;
	}

	public Contact findContactByName1(String contactFirstName, String contactLastName) {

		Session session = factory.openSession();
		Transaction tx = null;

		Contact contactToReturn = null;

		try {

			/*
			 * tx = session.beginTransaction(); CriteriaBuilder crBuilder =
			 * session.getCriteriaBuilder(); CriteriaQuery<Contact> criteria =
			 * crBuilder.createQuery(Contact.class);
			 * 
			 * Root<Contact> contactRoot = criteria.from(Contact.class);
			 * criteria.select(contactRoot);
			 * 
			 * 
			 * Predicate nameRestriction = crBuilder.and( crBuilder.equal(
			 * contactRoot.get("firstName"), contactFirstName ), crBuilder.equal(
			 * contactRoot.get("lastName"), contactLastName ) );
			 * 
			 * criteria.where(nameRestriction);
			 * 
			 * contactToReturn = session.createQuery(criteria).getSingleResult();
			 * 
			 * tx.commit();
			 */
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());

			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null && session.isOpen())
				session.close();
		}

		return contactToReturn;
	}

	public Contact findContactById(Integer contactId) {

		Session session = factory.openSession();
		Transaction tx = null;
		Contact contactToFind = null;
		try {
			/*
			 * tx = session.beginTransaction(); CriteriaBuilder crBuilder =
			 * session.getCriteriaBuilder(); CriteriaQuery<Contact> criteria =
			 * crBuilder.createQuery(Contact.class);
			 * 
			 * Root<Contact> accountRoot = criteria.from(Contact.class);
			 * criteria.select(accountRoot);
			 * 
			 * criteria.where(crBuilder.equal(accountRoot.get("accountId"), contactId));
			 * 
			 * List<Contact> accountList = session.createQuery(criteria).getResultList();
			 * 
			 * if (null != accountList && accountList.size() > 0) { contactToFind =
			 * accountList.get(0); } else { return null; }
			 * 
			 * tx.commit();
			 */
		} catch (HibernateException e) {
			System.err.println(e.getLocalizedMessage());

			if (tx != null)
				tx.rollback();
		} finally {
			session.close();
		}
		return contactToFind;

	}

	public Boolean deleteContactByName(String contactFirstName, String contactLastName) {

		Session session = factory.openSession();
		Transaction tx = null;
		Boolean check = null;

		try {
			/*
			 * tx = session.beginTransaction();
			 * 
			 * Contact contactToRemove = findContactByName(contactFirstName,
			 * contactLastName);
			 * 
			 * if(contactToRemove != null) { check = true; session.remove(contactToRemove);
			 * } else { check = false; }
			 * 
			 * tx.commit();
			 */
		} catch (HibernateException e) {

			System.err.println(e.getLocalizedMessage());

			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

		return check;

	}

	public Boolean deleteContactById(Integer contactId) {
		Session session = factory.openSession();
		Transaction tx = null;
		Boolean check = null;

		try {
			/*
			 * tx = session.beginTransaction();
			 * 
			 * Contact contactToRemove = findContactById(contactId);
			 * 
			 * if(contactToRemove != null) { check = true; session.remove(contactToRemove);
			 * } else { check = false; }
			 * 
			 * tx.commit();
			 */
		} catch (HibernateException e) {

			System.err.println(e.getLocalizedMessage());

			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

		return check;
	}

	public Set<Contact> getContactsOfAccount(Account account) {

		Session session = factory.openSession();
		Transaction tx = null;
		Set<Contact> contactList = null;

		try {
			tx = session.beginTransaction();
			Account checkAccount = (Account) session.load(Account.class, account.getAccountId());
			contactList = checkAccount.getContacts();
			tx.commit();

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			if (session != null)
				session.close();
		}

		return contactList;

	}

	public void updateContact(Contact contactToUpdate) {

		Session session = factory.openSession();
		Transaction tx = null;

		try {
			/*
			 * tx = session.beginTransaction(); CriteriaBuilder crBuilder =
			 * session.getCriteriaBuilder(); CriteriaQuery<Contact> criteria =
			 * crBuilder.createQuery(Contact.class);
			 * 
			 * Root<Contact> accountRoot = criteria.from(Contact.class);
			 * criteria.select(accountRoot);
			 * 
			 * session.update(contactToUpdate);
			 * 
			 * tx.commit();
			 */
		} catch (HibernateException e) {

			System.out.println(e.getMessage());

			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

	}

	public ContactLoginDetails getContactLogin(String username, String password) {

		Session session = factory.openSession();
		Transaction tx = null;
		List<ContactLoginDetails> contactDetailsToReturn = null;
		ContactLoginDetails singleContactDetails = null;
		try {
	 		tx = session.beginTransaction();
	 	
	 		/*
	 		Query query = session.createQuery("from contact_login_details where username = :username AND password = :password ");
	 		query.setParameter("username", username);
	 		query.setParameter("password", password);
	 		contactDetailsToReturn = (ContactLoginDetails) query.uniqueResult();
	 		
	 		
	 		
	 		
	 		Criteria query = session.
	 				createCriteria(ContactLoginDetails.class, "cld");
	 				query.setProjection(Projections.projectionList().
	 				add(Projections.property("cld.account")));
	 				
	 				query.add(Restrictions.eq("username", username));
	 				query.add(Restrictions.eq("password", password));
	 			
	 				
	 				
	 				
	 				String sql = "SELECT * from contact_login_details cld, contact cont where cld.username = :username AND cld.password = :password AND cld.contactId = cont.contactId";
	 				
	 				SQLQuery query = session.createSQLQuery(sql);
	 				query.addEntity(ContactLoginDetails.class);
	 				query.addEntity(Contact.class);
	 				query.setParameter("username", username);
	 		 		query.setParameter("password", password);
	 				
	 		 		contactDetailsToReturn =  query.list();
	 		 		*/	
	 		 		
	 		 		
	 		 		
	 			    DetachedCriteria criteria = DetachedCriteria.forClass(ContactLoginDetails.class, "a");
	 			    criteria.add(Restrictions.eq("a.username", username));
	 			    criteria.add(Restrictions.eq("a.password", password));
	 			    
	 			   contactDetailsToReturn = criteria.getExecutableCriteria(session).list();
	 			   singleContactDetails = contactDetailsToReturn.get(0);
	 		
	 		tx.commit();
		} catch (HibernateException e) {

			System.out.println(e.getMessage());

			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

		return singleContactDetails;
	}

	public Contact getContact(String firstName, String lastName) {

		Session session = factory.openSession();
		Transaction tx = null;
		Contact contactToReturn = null;

		try {
			tx = session.beginTransaction();

			Query query = session.createQuery("from Contact where firstName = :firstName AND lastName = :lastName ");
			query.setParameter("firstName", firstName);
			query.setParameter("lastName", lastName);
			contactToReturn = (Contact) query.uniqueResult();

			tx.commit();
		} catch (HibernateException e) {

			System.out.println(e.getMessage());

			if (tx != null)
				tx.rollback();
		} finally {
			if (session != null)
				session.close();
		}

		return contactToReturn;
	}

}
