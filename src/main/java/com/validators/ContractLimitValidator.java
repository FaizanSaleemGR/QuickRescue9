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

import com.entities.Contact;
import com.services.AccountService;
import com.services.ContactService;
import com.services.impl.AccountServiceImpl;
import com.services.impl.ContactServiceImpl;

@FacesValidator("ContractLimitValidator")
public class ContractLimitValidator implements Validator, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	AccountService accountService = new AccountServiceImpl();
	ContactService contactService = new ContactServiceImpl();
	List<Contact> contactsList = new ArrayList<>();

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 	{

		if(value == null || Integer.valueOf(value.toString()) <= 0) {
			FacesMessage msg = new FacesMessage("Invalid Limit.", "Please enter a number greater than zero.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}

	}
}
