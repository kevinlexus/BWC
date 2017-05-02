package com.ric.bill.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ric.bill.dao.TarifDAO;
import com.ric.bill.model.tr.Prop;


@Repository
public class TarifDAOImpl implements TarifDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	@SuppressWarnings("unchecked")
	@Cacheable(cacheNames="neverWipe")
	public synchronized Prop getPropByCD(String cd) {
		Query query =em.createQuery("from Prop t where t.cd in (:cd)");
		query.setParameter("cd", cd);
		return (Prop) query.getSingleResult();
	}
    
	
}
