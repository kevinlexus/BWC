package com.ric.bill.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.PaymentDetDAO;
import com.ric.bill.model.cash.PaymentDet;


@Slf4j
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
	 * @param trimDt - дата инкассации, по которой отсечь платежи
	 */
	public List<PaymentDet> getPaymentDetByPeriod(String period, Date curDt1, Date curDt2, Date trimDt) {
		log.info("curDt1={}, curDt2={}, period={}, trimDt={}", curDt1, curDt2, period, trimDt);
		// получить первую дату периода
		Query query =em.createQuery("select t from PaymentDet t join t.payment p join p.wp e "
				+ "left join p.wpClct d where "
				+ "e.id in (600) and trunc(nvl(d.dtClose, t.payment.dtf)) between trunc(:dt1) and trunc(:trimDt) " // Банки - поменяли 04.12.17 обратно  t.payment.dtPayBank --> t.payment.dtf  
				+ "or e.id in (5000) and trunc(t.payment.dtf) between trunc(:dt1) and trunc(:trimDt) " // Web (Газпромбанк, Виталя делает)
				+ "or t.payment.tp.id in (2012,1624,2096,2087) and trunc(t.payment.dtf) between trunc(:dt1) and trunc(:trimDt) " // Безналичные платежи УК и т.п.
				+ "or trunc(d.dtClose) between trunc(:dt1) and trunc(:trimDt)) "); // Прочие
		query.setParameter("dt1", curDt1);
//		query.setParameter("dt2", curDt2);
		query.setParameter("trimDt", trimDt);
		
		//log.info("dt1={}, dt2={}, trimDt={}", curDt1, curDt2, trimDt);
		
		return query.getResultList();
	}



}
