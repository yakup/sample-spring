package org.springframework.samples.webflow.ajax;

import java.io.Serializable;

import javax.inject.Singleton;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.context.annotation.Scope;
import org.springframework.samples.webflow.dao.impl.PeopleDAO;
import org.springframework.samples.webflow.model.People;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.RequestContext;

@Component("userBean")
@Scope("session")
public class UserBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String firstName;
	private String lastName;

	private PeopleDAO peopleDAO;
	
	@Autowired
	public UserBean(PeopleDAO peopleDAO) {
		this.peopleDAO = peopleDAO;
	}
	
	public String createEmailSuggestion(RequestContext context) {

		boolean haveFirst = StringUtils.hasText(firstName);
		boolean haveLast = StringUtils.hasLength(lastName);

		if (haveFirst && haveLast) {
			People people = new People();
			people.setName(firstName);
			peopleDAO.salva(people);
			return firstName + "." + lastName + "@fastmail.com";

		} else if (haveFirst) {
			peopleDAO.buscaTodos();			
			return firstName + "@fastmail.com";

		} else if (haveLast) {
			return lastName + "@fastmail.com";

		} else {
			context.getMessageContext().addMessage(
					new MessageBuilder().error().defaultText("Please enter a first or a last name of both!").build());
			return "";
		}
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

}
