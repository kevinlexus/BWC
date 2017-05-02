package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ric.bill.dao.ServDAO;
import com.ric.bill.model.tr.Serv;


@Repository
public class ServDAOImpl implements ServDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	/**
	 * Найти основную услугу по линии основная - ОДН
	 * @param serv - услуга ОДН
	 * @return
	 */
	//@Cacheable(cacheNames="rrr1", key="{ #serv.getId() }")
	@Cacheable(cacheNames="neverWipe")
	public synchronized Serv findMain(Serv serv) {
		Query query =em.createQuery("from Serv t where t.servOdn=:serv");
		query.setParameter("serv", serv);
		return (Serv) query.getSingleResult();
	}

	/**
	 * Найти и отсортировать, все услуги для распределения объемов 
	 */
	@SuppressWarnings("unchecked")
	public List<Serv> findForDistVol() {
		Query query =em.createQuery("from Serv t where t.cd in (:s1,:s2,:s3,:s4,:s5) order by t.npp2");
		query.setParameter("s1", "Холодная вода");
		query.setParameter("s2", "Горячая вода");
		query.setParameter("s3", "Водоотведение");
		query.setParameter("s4", "Отопление");
		query.setParameter("s5", "Электроснабжение");
		return query.getResultList();
	}

	/**
	 * Найти и отсортировать, все услуги для распределения объемов 
	 */
	@SuppressWarnings("unchecked")
	public List<Serv> findForDistVolForKart() {
		Query query =em.createQuery("from Serv t where t.cd in (:s1,:s2,:s3,:s4,:s5) order by t.npp2");
		query.setParameter("s1", "Холодная вода");
		query.setParameter("s2", "Горячая вода");
		query.setParameter("s3", "Водоотведение");
		query.setParameter("s4", "Отопление");
		query.setParameter("s5", "Электроснабжение");
		return query.getResultList();
	}

	/**
	 * Найти услугу по CD
	 * @param cd - услуги
	 * @return
	 */
	@Cacheable(cacheNames="neverWipe")
	public synchronized Serv getByCD(String cd){
		Query query =em.createQuery("from Serv t where t.cd=:cd");
		query.setParameter("cd", cd);
		return (Serv) query.getSingleResult();
	}

	/*
	 * Получить все услуги
	 * 
	 */
	public List<Serv> getServAll() {
		Query query =em.createQuery("from Serv t order by t.name");
		return query.getResultList();
	}

}
