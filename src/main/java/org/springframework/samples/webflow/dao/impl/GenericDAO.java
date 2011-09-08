package org.springframework.samples.webflow.dao.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.samples.webflow.dao.IGenericDAO;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public abstract class GenericDAO<T, ID extends Serializable> extends HibernateDaoSupport implements IGenericDAO<T, ID> {

    private static Log LOG = LogFactory.getLog(GenericDAO.class);

    private Class<T> persistentClass;

    @SuppressWarnings("unchecked")
    public GenericDAO() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
	@Autowired
	private SessionFactory sessionFactory;

    
    public Class<T> getPersistentClass() {
        return this.persistentClass;
    }

    public void exclui(T entity) {
        try {
            getHibernateTemplate().delete(entity);
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
    }

    
    public T buscaPeloID(ID id) {
        try {
        	//return (T) sessionFactory.getCurrentSession().load(getPersistentClass(), id);
        	return (T) getHibernateTemplate().get(getPersistentClass(), id);
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return null;
    }

    public List<T> buscaTodos() {
        try {
        	//Session session = sessionFactory.getCurrentSession();
            return getHibernateTemplate().loadAll(persistentClass);
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return null;
    }

    public T salva(T entity) {
        try {
        	//sessionFactory.getCurrentSession().merge(entity);
        	getHibernateTemplate().save(entity);
            return entity;
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return entity;
    }

    public T atualiza(T entity) {
        try {
            getHibernateTemplate().update(entity);
            return entity;
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return entity;
    }

    public List<T> buscaListaPeloCriterio(Criterion... criterion) {
        try {
            Session session = getSession();
            Criteria crit = session.createCriteria(getPersistentClass());
            for (Criterion c : criterion) {
                crit.add(c);
            }
            return crit.list();
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return null;
    }

    public T buscaUmPeloCriterio(Criterion... criterion) {
        try {
            Session session = getSession();
            Criteria crit = session.createCriteria(getPersistentClass());
            for (Criterion c : criterion) {
                crit.add(c);
            }
            return (T) crit.uniqueResult();
        } catch (final HibernateException ex) {
            GenericDAO.LOG.error(ex);
        }
        return null;
    }
}
