package com.ric.bill.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ric.bill.dao.ObjDAO;
import com.ric.bill.model.bs.Obj;


@Repository
public class ObjDAOImpl implements ObjDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	/**
	 * Найти элемент списка по CD 
	 */
	@Cacheable("rrr1")
	public synchronized Obj getByCD(int rqn, String cd) {
		Query query =em.createQuery("from Obj t where t.cd in (:cd)");
		query.setParameter("cd", cd);
		return (Obj) query.getSingleResult();
	}

}
