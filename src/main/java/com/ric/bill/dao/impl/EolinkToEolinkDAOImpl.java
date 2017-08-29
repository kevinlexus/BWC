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
		Query query =em.createQuery("select t.child from EolinkToEolink t where t.parent.id = :id");
		query.setParameter("id", eolink.getId());
		lst=query.getResultList();
		query =em.createQuery("select t.parent from EolinkToEolink t where t.child.id = :id");
		query.setParameter("id", eolink.getId());
		lst.addAll(query.getResultList());
		return lst;
	}

    /**
     * Получить родительские объекты по дочернему объекту и типу связи
     * @param eolink - Вх. объект
     * @return
     */
    public List<Eolink> getParentEolink(Eolink eolink, String tp) {
    	List<Eolink> lst; 
		Query query =em.createQuery("select t.parent from EolinkToEolink t where t.child.id = :id and t.tp.cd = :tp");
		query.setParameter("id", eolink.getId());
		query.setParameter("tp", tp);
		return query.getResultList();
	}

}
