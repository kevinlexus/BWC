package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.PayordCmpDAO;
import com.ric.bill.model.fn.PayordCmp;


@Repository
public class PayordCmpDAOImpl implements PayordCmpDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	/**
	 * Получить компоненты платежки по её ID
	 * @return
	 */
	public List<PayordCmp> getPayordCmpByPayordId(Integer payordId) {

		Query query =em.createQuery("select t from PayordCmp t join t.payord p where p.id = :payordId");
		query.setParameter("payordId", payordId);
		return query.getResultList();

	}



}
