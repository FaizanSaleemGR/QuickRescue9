package com.globalrescue.QuickRescue9;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.dao.ContactDao;
import com.dao.impl.ContactDaoImpl;
import com.jpa.entities.Contact;
import com.jpa.entities.ContactLoginDetails;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HibernateUtilsTest {

	ContactDao contactDao;

	@Before
	public void setup() {
		contactDao = new ContactDaoImpl();
	}

/*	@Test
	public void test0_LoginContact() {
		Contact contact = contactDao.getContact("Ali Raza", "Contact1");
		assertNotNull(contact);
	}*/

	@Test
	public void test1_LoginContact() {
		ContactLoginDetails contactLoginDetails = contactDao.getContactLogin("ali", "123");
		Contact contact = contactLoginDetails.getContact();
		
		assertNotNull(contact);
		assertEquals("Ali Raza", contact.getFirstName());
		assertNotNull(contact.getAccount());
		assertEquals("QuickRescue", contact.getAccount().getName());
		
		
	}

}
