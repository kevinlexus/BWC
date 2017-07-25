package com.ric.bill.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.Utl;
import com.ric.bill.dao.PaymentDetDAO;
import com.ric.bill.model.cash.PaymentDet;


@Repository
public class PaymentDetDAOImpl implements PaymentDetDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
	 * Получить все платежи за выбранный период 
	 * @param period - период
	 * @param curDt1 - первая дата периода
	 * @param curDt2 - последняя дата периода
	 * @param genDt - дата инкассации, по которой отсечь платежи
	 */
	public List<PaymentDet> getPaymentDetByPeriod(String period, Date curDt1, Date curDt2, Date genDt) {
		// получить первую дату периода
		Query query =em.createQuery("select t from PaymentDet t join t.payment p where t.payment.dtf between :dt1 and :dt2 "
				+ "and p.wpClct.dtClose between :dt1 and :dt4");
		query.setParameter("dt1", curDt1);
		query.setParameter("dt2", curDt2);
		query.setParameter("dt4", genDt);
		return query.getResultList();
	}



}
