package org.springframework.samples.webflow.dao.impl;

import java.io.Serializable;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.webflow.dao.IPeopleDAO;
import org.springframework.samples.webflow.model.People;
import org.springframework.stereotype.Repository;

@Repository
public class PeopleDAO extends GenericDAO<People, Long> implements IPeopleDAO,Serializable {

	private static final long serialVersionUID = 1L;

    @Autowired
    public PeopleDAO(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }
}
