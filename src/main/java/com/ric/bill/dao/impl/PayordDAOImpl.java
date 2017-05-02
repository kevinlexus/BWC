package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.PayordDAO;
import com.ric.bill.model.fn.Payord;


@Repository
public class PayordDAOImpl implements PayordDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить платежки по Id группы
     */
    public List<Payord> getPayordByPayordGrpId(Integer payordGrpId) {
    	
		Query query =em.createQuery("select t from Payord t join t.payordGrp p where p.id = :payordGrpId");
		query.setParameter("payordGrpId", payordGrpId);
		return query.getResultList();
    
    }

    /**
     * Получить все платежки
     */
	public List<Payord> getPayordAll() {
		Query query =em.createQuery("select t from Payord t");
		return query.getResultList();

	}
	

}
