package com.exceptions;

import java.io.Serializable;

import javax.ejb.EJBTransactionRolledbackException;

public class ComponentInstanceException extends EJBTransactionRolledbackException implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;


	public ComponentInstanceException() {
		// TODO Auto-generated constructor stub
	}

	public ComponentInstanceException(String message) {
		super(message);
	}


}
