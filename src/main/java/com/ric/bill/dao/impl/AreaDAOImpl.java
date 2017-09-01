package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.AreaDAO;
import com.ric.bill.model.ar.Area;


@Repository
public class AreaDAOImpl implements AreaDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    
    /*
     * Получить все типы областей
     * 
     */
    public List<Area> getAllHaveKlsk(Integer areaId) {
		Query query =em.createQuery("select t from Area t where t.klskId is not null and (:areaId is null or t.id=:areaId)");
		query.setParameter("areaId", areaId);
		return query.getResultList();
	}

    /*
     * Получить область по klsk
     * 
     */
    public Area getByKlsk(Integer klsk) {
		Query query =em.createQuery("select t from Area t where t.klsk = :klsk");
		query.setParameter("klsk", klsk);
		return (Area) query.getSingleResult();
	}

}
