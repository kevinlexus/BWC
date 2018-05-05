package com.ric.bill.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ric.bill.dao.PayordFlowDAO;
import com.ric.bill.mm.impl.PayordMngImpl;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.fn.PayordFlow;

import lombok.extern.slf4j.Slf4j;


@Repository
@Slf4j
public class PayordFlowDAOImpl implements PayordFlowDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить движения по всем платежкам по типу и периоду (используется ли?)
     * @param uk - УК
     * @param tp - тип 
     * @param period - период
     * @return
     */
    @Override
    public List<PayordFlow> getPayordFlowByTpPeriod(Integer tp, Org uk, String period) {
		Query query =em.createQuery("select t from PayordFlow t where "
				+ "t.period = :period and t.tp = :tp and t.uk.id = :id and "
				+ "t.status = 1 "
				+ "order by t.dt");
		query.setParameter("period", period);
		query.setParameter("tp", tp);
		query.setParameter("id", uk.getId());
		return query.getResultList();
	}

    /**
     * Получить движения по всем платежкам по типу и дате
     * @param tp - тип
     * @param dt1 - дата начала
     * @param dt2 - дата окончания
     * @param uk - УК
     * @return
     */
    @Override
    public List<PayordFlow> getPayordFlowByTpDt(Integer tp, Date dt1, Date dt2, Integer uk) {
    	int ukId;
		if (uk==null) {
    		ukId = -1;
    	} else {
    		ukId = uk;
    	}
    	Query query = null;
    	if (dt1 != null && dt2 != null ) {
    		query =em.createQuery("select t from PayordFlow t where "
    				+ "t.dt between :dt1 and :dt2 and t.tp = :tp and "
    				+ "t.uk.id = decode(:uk, -1, t.uk.id, :uk) and " // NVL не получилось сделать - ORA-00932: inconsistent datatypes: expected BINARY got NUMBER
    				+ "t.status = 1 "
    				+ "order by t.payord.name, t.uk.id");
    		query.setParameter("dt1", dt1);
    		query.setParameter("dt2", dt2);
    		query.setParameter("tp", tp);
    		query.setParameter("uk", ukId);
    	} else {
    		query =em.createQuery("select t from PayordFlow t where "
    				+ "t.tp = :tp and "
    				+ "t.uk.id = decode(:uk, -1, t.uk.id, :uk) and  "
    				+ "t.status = 1 "
    				+ "order by t.payord.name, t.uk.id");
    		query.setParameter("tp", tp);
    		query.setParameter("uk", ukId);
    	}
		return query.getResultList();
	}

    
    /**
     * Получить движение по платежке, до определенной даты
     * @param payordId - ID платежки
     * @param uk - УК
     * @param tp - тип
     * @param dt - дата
     */
    @Override
    public List<PayordFlow> getPayordFlowBeforeDt(Integer payordId, Org uk, Integer tp, Date dt) {
		Query query =em.createQuery("select t from PayordFlow t join t.payord p where p.id = :payordId "
				+ "and t.dt <= :dt and t.tp = :tp and t.uk.id = :id and "
				+ "t.status = 1 "
				+ "order by t.dt desc");
		query.setParameter("payordId", payordId);
		query.setParameter("dt", dt);
		query.setParameter("tp", tp);
		query.setParameter("id", uk.getId());
		return query.getResultList();
	}

    /** НЕ УДАЛЯТЬ!
     * Получить движение по платежке, до определенного периода (напр.для вычисления сальдо)
     * @param payordId - Id платежки
     * @param uk - УК
     * @param tp - тип движения
     * @param period - период
     */
/*    public List<PayordFlow> getPayordFlowBeforePeriod(Integer payordId, Org uk, Integer tp, String period) {
    	Integer ukId = (uk==null? null : uk.getId());
		Query query =em.createQuery("select t from PayordFlow t join t.payord p where coalesce(:payordId, p.id) = p.id "
				+ "and t.period <= :period and t.tp = :tp and coalesce(:id, t.uk.id) = t.uk.id and "
				+ "t.status = 1 "
				+ "order by t.period desc");
		query.setParameter("payordId", payordId);
		query.setParameter("period", period);
		query.setParameter("tp", tp);
		query.setParameter("id", ukId );
		return query.getResultList();
	}*/

    
    /**
     * Получить движение по платежке, до определенного периода даты (напр.для вычисления сальдо)
     */
    @Override
    public List<PayordFlow> getPayordFlowBeforePeriod(Integer payordId, Org uk, Integer tp, String period, Integer status) {
    	log.info("===============getPayordFlowBeforePeriod 1={}, 2={}, 3={}, 4={}, 5={}", payordId, period, status, tp, uk.getId());
		Query query =em.createQuery("select t from PayordFlow t join t.payord p where p.id = :payordId "
				+ "and t.period <= :period and t.tp = :tp and t.uk.id = :id and "
				+ "(:status is null or t.status = :status)"
				+ "order by t.period desc");
		query.setParameter("payordId", payordId);
		query.setParameter("period", period);
		query.setParameter("status", status);
		query.setParameter("tp", tp);
		query.setParameter("id", uk.getId());
		return query.getResultList();
	}
    
    /**
     * Удалить движение по платежке по определенной дате или периоду
     * @param dt - дата
     */
    @Override
    public void delPayordFlow(Date dt) {
    	Query query = em.createNativeQuery("delete from fn.payord_flow t where "
    			+ "t.dt = :dt");
		query.setParameter("dt", dt);
    	query.executeUpdate();
	}
    
}
