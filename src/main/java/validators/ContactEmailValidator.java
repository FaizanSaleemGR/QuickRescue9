package validators;

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

@FacesValidator("ContactEmailValidator")
public class ContactEmailValidator implements Validator, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	AccountService accountService = new AccountServiceImpl();
	ContactService contactService = new ContactServiceImpl();
	List<Contact> contactsList = new ArrayList<>();

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException 	{

		Account acc = (Account) component.getAttributes().get("account");
		String email = value.toString();
		Boolean doesExist = false;


		for (Contact contact : acc.getContacts()) {
			if(contact.getEmailAddress().equals(email)) {
				doesExist = true;
			}
		}

		if(doesExist) {
			FacesMessage msg = new FacesMessage("E-mail already exists.", "A contact associated with this account already has same email address.");
			msg.setSeverity(FacesMessage.SEVERITY_ERROR);
			throw new ValidatorException(msg);
		}
	}



}