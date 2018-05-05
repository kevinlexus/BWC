package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.data.jpa.provider.HibernateUtils;
import org.springframework.stereotype.Repository;

import com.ric.bill.dao.VolDAO;
import com.ric.bill.model.mt.Vol;


	@Repository
	public class VolDAOImpl implements VolDAO {
	
		//EntityManager - EM нужен на каждый DAO или сервис свой!
	    @PersistenceContext
	    private EntityManager em;
	
	    private SessionFactory sessionFactory;
	    /*
	     * Добавить объем
	     * @param vol - объект объема
	     */
	    public synchronized void add(Vol vol) {
			em.persist(vol);
		}
	
/*	    @Override
	    public void testMe() {
	    	Session session = (Session) em.getDelegate();
	    	SQLQuery query = session.createSQLQuery("select t.id from mt.meter_vol t");
	    	int maxResults = 50;
	    	int batchSize = 50;
	    	for (int i = 0; ; i++) {
	    	    // query.setFirstResult(batchSize*i);
	    	    // query.setMaxResults(maxResults);
	    	     List resultSet = query.list();
	    	     if(resultSet.isEmpty())
	    	         break;
	    	     //process result set
	    	}
	    }*/
	}
