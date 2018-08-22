package com.ric.bill.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ric.bill.dao.ParDAO;
import com.ric.bill.model.bs.Par;


@Repository
public class ParDAOImpl implements ParDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
    
	@SuppressWarnings("unchecked")
	@Cacheable(cacheNames="ParDAOImpl.getByCd", key="{#rqn, #cd }", unless = "#result == null")
	public Par getByCd(int rqn, String cd) {
		Query query =em.createQuery("from Par t where t.cd = :cd");
		query.setParameter("cd", cd);
		try {
			return (Par) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	//работает это медленнее чем была итерация по всем параметрам объекта!
	//@Cacheable(cacheNames="ParDAOImpl.checkPar", key="{#rqn, #id, #cd, #dataTp }")
	public/* synchronized */boolean checkPar(int rqn, int id, String cd, String dataTp) {
		Query query =em.createQuery("from Par t where t.id = :id and t.cd=:cd and t.dataTp=:dataTp");
		query.setParameter("id", id);
		query.setParameter("cd", cd);
		query.setParameter("dataTp", dataTp);
		try {
			Par p = (Par) query.getSingleResult();
		} catch (NoResultException e) {
			return false;
		}
		return true;
	}
	
}
