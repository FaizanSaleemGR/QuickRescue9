package com.validators;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import com.entities.Account;
import com.entities.Contact;
import com.services.AccountService;
import com.services.ContactService;
import com.services.impl.AccountServiceImpl;
import com.services.impl.ContactServiceImpl;

@FacesValidator("AccountNameValidator")
public class AccountNameValidator implements Validator, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	AccountService accountService = new AccountServiceImpl();
	ContactService contactService = new ContactServiceImpl();
	List<Contact> contactsList = new ArrayList<>();

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 	{

		if(value != null) {
		
		String val = value.toString();
		Boolean doesExist = false;
		List<Account> accountList = (List<Account>) component.getAttributes().get("accountList");
		
		

		for (Account account : accountList) {
			if(account.getName().toLowerCase().equals(val.toLowerCase())) {
				doesExist = true;
			}
		}

		if(doesExist) {
			FacesMessage msg = new FacesMessage("Account name already exists.", "An account already has same name.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
		}
	}



}
