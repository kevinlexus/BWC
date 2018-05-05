package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ric.bill.MeterContains;
import com.ric.bill.dao.MeterLogDAO;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.mt.MLogs;
import com.ric.bill.model.mt.Meter;
import com.ric.bill.model.tr.Serv;


/**
 * DAO сущности MeterLog
 * @version 1.00
 * @author lev
 *
 */
@Repository
public class MeterLogDAOImpl implements MeterLogDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	/**
	 * Получить лиц.счет, содержащий указанный счетчик (не стал возвращать интерфейс)
	 * @param mLog - Счетчик
	 * @return
	 */
	//@Cacheable(cacheNames="MeterLogDAOImpl.getKart", key="{#rqn, #mLog.getId() }")
	public synchronized Kart getKart(int rqn, MLogs mLog) {
		Query query =em.createQuery("from Kart t where t.klsk =:klsk");
		query.setParameter("klsk", mLog.getKlskObj());
		try {
		  return (Kart) query.getSingleResult();
		} catch (NoResultException e) {
		  return null;
		} 
	}
	
}
