package com.dao.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.type.IntegerType;

import com.dao.ContactDao;
import com.entities.Account;
import com.entities.Contact;
import com.entities.ContactLoginDetails;
import com.utils.HibernateUtils;

@Stateless
public class ContactDaoImpl implements ContactDao {

	private SessionFactory factory;

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
			factory = HibernateUtils.getSessionFactory();

		} catch (Throwable e) {
			System.err.println("Failed to create criteriaFactory object." + e);
			throw new ExceptionInInitializerError(e);
		}
	}


	@Override
	public Integer addLoginDetails(Contact contact, ContactLoginDetails contactLoginDetails) {

		Session session = factory.openSession();
		Transaction tx = null;
		Integer contactId = null;

		try {
			tx = session.beginTransaction();

			Criteria criteria = session.createCriteria(ContactLoginDetails.class);
			ContactLoginDetails contactLoginDetails2 = (ContactLoginDetails) criteria.add(Restrictions.sqlRestriction("contactId=?", contact.getContactId(), IntegerType.INSTANCE)).uniqueResult();


			if(null!=contactLoginDetails2) {
				contact.setLoginDetails(contactLoginDetails2);
				contactLoginDetails2.setContact(contact);
				session.saveOrUpdate(contactLoginDetails2);
			}
			else {
				contact.setLoginDetails(contactLoginDetails);
				contactLoginDetails.setContact(contact);
				session.saveOrUpdate(contactLoginDetails);
			}


			session.update(contact);

			tx.commit();
		} catch (HibernateException e) {

			System.err.println(e.getLocalizedMessage());

			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return contactId;

	}

	@Override
	public Integer addContact(Account account, Contact contact) {

		Session session = factory.openSession();
		Transaction tx = null;
		Integer contactId = null;

		try {
			tx = session.beginTransaction();

			contact.setAccount(account);
			//contact.setAccountId(account.getAccountId());
			account.getContacts().add(contact);

			contactId = (Integer)session.save(contact);
			session.update(account);


			tx.commit();
		} catch (HibernateException e) {

			System.err.println(e.getLocalizedMessage());

			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return contactId;

	}

	@Override
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

			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return contactToReturn;
	}

	@Override
	public Contact findContactById(Integer contactId) {

		Session session = factory.openSession();
		Transaction tx = null;
		Contact contactToFind = null;
		List<Contact> contactList = null;

		try {


			tx = session.beginTransaction();


			Criteria criteria = session.createCriteria(Contact.class);
			criteria.add(Restrictions.eq("contactId", contactId));

			contactList = (List<Contact>) criteria.list();


			if (null != contactList && contactList.size() > 0) {
				contactToFind = contactList.get(0);
			}

			tx.commit();

		} catch (HibernateException e) {
			System.err.println(e.getLocalizedMessage());

			if (tx != null) {
				tx.rollback();
			}
		} finally {
			session.close();
		}
		return contactToFind;

	}

	@Override
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

			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return check;

	}

	@Override
	public Boolean deleteContactById(Integer contactId) {
		Session session = factory.openSession();
		Transaction tx = null;
		Boolean check = null;

		try {

			tx = session.beginTransaction();
			Contact contactToRemove = findContactById(contactId);

			if (contactToRemove != null) {
				check = true;
				session.delete(contactToRemove);
			} else {
				check = false;
			}

			tx.commit();
		} catch (HibernateException e) {

			System.err.println(e.getLocalizedMessage());

			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return check;
	}

	@Override
	public List<Contact> getContactsOfAccount(Account account) {

		Session session = factory.openSession();
		Transaction tx = null;
		List<Contact> contactList = null;

		try {
			tx = session.beginTransaction();



//			Account checkAccount = (Account) session.load(Account.class, account.getAccountId());



    		SQLQuery query = session.createSQLQuery("Select * from contact where accountId=:accountId ");
    		query.setParameter("accountId", account.getAccountId());

//			query.setResultTransformer(CriteriaSpecification.ALIAS_TO_ENTITY_MAP);
			query.addEntity(Contact.class);


			contactList = (List<Contact>)query.list();

    		tx.commit();





		} catch (HibernateException e) {
			if (tx != null) {
				tx.rollback();
			}
		} catch (SecurityException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return contactList;

	}

	@Override
	public void updateContact(Contact contactToUpdate) {

		Session session = factory.openSession();
		Transaction tx = null;

		try {
			  tx = session.beginTransaction();

			  session.update(contactToUpdate);

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

	 			   contactDetailsToReturn = (List<ContactLoginDetails>) criteria.getExecutableCriteria(session).list();


	 			   if(null!=contactDetailsToReturn && contactDetailsToReturn.size() > 0) {
					singleContactDetails = contactDetailsToReturn.get(0);
				}

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

		return singleContactDetails;
	}

	@Override
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

			if (tx != null) {
				tx.rollback();
			}
		} finally {
			if (session != null) {
				session.close();
			}
		}

		return contactToReturn;
	}

}
