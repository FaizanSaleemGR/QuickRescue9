package com.utils;

import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map;
import java.util.Random;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.entities.Account;
import com.entities.Contact;
import com.entities.ContactLoginDetails;

@ManagedBean(name="utilsController")
@ViewScoped
public class Utils {
	static ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
	static int alreadyRedirected = 0;

	public static void redirectTo(String url) {
//		FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, url);
		HttpServletResponse response;
		response = (HttpServletResponse)externalContext.getResponse();
		try {
//				response.reset();
//				response.resetBuffer();
				externalContext.redirect(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void navigateTo(String page) {
		FacesContext fc = FacesContext.getCurrentInstance();

		if(page.contains(".xhtml")) {
			page = page.replace(".xhtml", "");
		}

		ConfigurableNavigationHandler nav
		   = (ConfigurableNavigationHandler)
			fc.getApplication().getNavigationHandler();

		nav.performNavigation(page);
	}


	public static void addToSession(SimpleEntry<String, Object>... params) {

		Map<String, Object> sessionMap = externalContext.getSessionMap();


		for (SimpleEntry<String, Object> param : params) {
			sessionMap.put(param.getKey(), param.getValue());
		}
	}


	public static Object getFromSession(String key) {

		Map<String, Object> sessionMap = externalContext.getSessionMap();

		return sessionMap.get(key);
	}



	public static void addToRequest(SimpleEntry<String, String>... params) {

		Map<String, String> requestMap = externalContext.getRequestParameterMap();


		for (SimpleEntry<String, String> param : params) {
			requestMap.put(param.getKey(), param.getValue());
		}
	}


	public static String getFromRequest(String key) {

		Map<String, String> requestMap = externalContext.getRequestParameterMap();

		return requestMap.get(key);
	}

	public static String generateRandomPassword(Integer limit) {
	    final String alphabet = "0123456789ABCDE";
	    final int N = alphabet.length();

	    Random r = new Random();
	    String password = "";

	    for (int i = 0; i < limit; i++) {
	    	password += alphabet.charAt(r.nextInt(N));
	    }
	    return password;
	}

	public static ContactLoginDetails createLogin(Account account, Contact contact) {
		ContactLoginDetails contactLoginDetails = new ContactLoginDetails();
		contactLoginDetails.setUsername(contact.getEmailAddress() + "@" + account.getEmailDomain());
		contactLoginDetails.setPassword(Utils.generateRandomPassword(8));

		return contactLoginDetails;
	}

	public static void setSessionTimeOutInMinutes(Integer timeOut) {
		externalContext.setSessionMaxInactiveInterval(timeOut*60);;
	}

	public static String logout() {

		System.out.println("In logout()");
		externalContext.getSessionMap().clear();
		navigateTo("login");
		externalContext.invalidateSession();

	    return "";



    }

	public static String getCurrentPageName() {
		return ((HttpServletRequest)externalContext.getRequest()).getServletPath().replaceAll("/", "").replaceAll(".xhtml", "");
	}

	public static void flushResponseBuffer() {

		if ( externalContext.isResponseCommitted() ) {
			//
		}


	}


}
