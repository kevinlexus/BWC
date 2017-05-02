package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.PayordGrpDAO;
import com.ric.bill.model.fn.PayordGrp;


@Repository
public class PayordGrpDAOImpl implements PayordGrpDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	// получить все платежки
    public List<PayordGrp> getPayordGrpAll() {
		Query query =em.createQuery("from PayordGrp t");
		return query.getResultList();
	}

	// получить платежку по Id
	public PayordGrp getPayordGrpById(Integer id) {
		Query query =em.createQuery("from PayordGrp t where t.id=:id");
		query.setParameter("id", id);
		try {
			return (PayordGrp) query.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}


}
