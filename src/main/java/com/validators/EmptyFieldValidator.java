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

@FacesValidator("EmptyFieldValidator")
public class EmptyFieldValidator implements Validator, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	AccountService accountService = new AccountServiceImpl();
	ContactService contactService = new ContactServiceImpl();
	List<Contact> contactsList = new ArrayList<>();

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 	{

		if(value == null) {
			FacesMessage msg = new FacesMessage("Empty Field.", "This field must not be empty.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	
	}
}
