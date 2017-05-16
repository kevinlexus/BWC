package com.ric.bill.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.ActionParDAO;
import com.ric.bill.model.bs.Par;
import com.ric.bill.model.exs.Action;


@Repository
public class ActionParDAOImpl implements ActionParDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
    
	@Override
	public Par getByActionCd(Action action, String cd) {
		//Query query =em.createQuery("select t from Action t where t.soapAct = :act and t.");
		//query.setParameter("klsk", klsk);
		//return (Area) query.getSingleResult();
		// TODO сделать реализацию!
		return null;

	}

}
