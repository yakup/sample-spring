package org.springframework.samples.webflow.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.webflow.dao.IUserDAO;
import org.springframework.samples.webflow.model.User;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO extends GenericDAO<User, Long> implements IUserDAO,Serializable {

	private static final long serialVersionUID = 1L;

	@Autowired
    public UserDAO(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
