package com.ric.bill.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.ActionParDAO;
import com.ric.bill.model.bs.Par;
import com.ric.bill.model.exs.Action;
import com.ric.bill.model.exs.ActionPar;


@Repository
public class ActionParDAOImpl implements ActionParDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Получить действие по ID и cd параметра
     * @param actionId - Id действия
     * @param parCd - Cd параметра
     */
	@Override
	public ActionPar getByActionCd(Integer actionId, String parCd) {
		Query query =em.createQuery("select t from ActionPar t where t.id = :actionId and t.par.cd = :parCd");
		query.setParameter("actionId", actionId);
		return (ActionPar) query.getSingleResult();
	}

}
