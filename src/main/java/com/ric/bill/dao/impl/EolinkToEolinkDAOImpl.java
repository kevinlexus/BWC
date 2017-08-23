package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.EolinkToEolinkDAO;
import com.ric.bill.model.exs.Eolink;


@Repository
public class EolinkToEolinkDAOImpl implements EolinkToEolinkDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Получить Связанные внешние объекты по объекту
     * @param eolink - Вх. объект
     * @return
     */
    public List<Eolink> getLinkedEolink(Eolink eolink) {
    	List<Eolink> lst; 
		Query query =em.createQuery("select t.eolink2 from EolinkToEolink t where t.eolink.id = :id");
		query.setParameter("id", eolink.getId());
		lst=query.getResultList();
		query =em.createQuery("select t.eolink from EolinkToEolink t where t.eolink2.id = :id");
		query.setParameter("id", eolink.getId());
		lst.addAll(query.getResultList());
		return lst;
	}


}
