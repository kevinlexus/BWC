package com.ric.bill.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.EolinkParDAO;
import com.ric.bill.model.exs.EolinkPar;


@Repository
public class EolinkParDAOImpl implements EolinkParDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Получить параметр по ID Eolink и cd параметра
     * @param taskId - Id задания
     * @param parCd - Cd параметра
     */
	@Override
	public EolinkPar getEolinkPar(Integer taskId, String parCd) {
		Query query =em.createQuery("select t from EolinkPar t where t.task.id = :taskId and t.par.cd = :parCd");
		query.setParameter("taskId", taskId);
		query.setParameter("parCd", parCd);
		try {
			return (EolinkPar) query.getSingleResult();
		} catch (NoResultException e) {
		  return null;
		} 
	}

}
