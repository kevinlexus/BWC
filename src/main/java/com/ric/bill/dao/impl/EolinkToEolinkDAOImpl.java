package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.EolinkToEolinkDAO;
import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.exs.EolinkToEolink;


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
	@Override
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
	@Override
    public List<Eolink> getParentEolink(Eolink eolink, String tp) {
		Query query =em.createQuery("select t.parent from EolinkToEolink t where t.child.id = :id and t.tp.cd = :tp");
		query.setParameter("id", eolink.getId());
		query.setParameter("tp", tp);
		return query.getResultList();
	}

    /**
     * Получить отношение родительской сущности к дочерней, по типу
     * @author lev
     * @param parent - родительская сущность
     * @param child  - дочерняя сущность
     * @param tp     - тип 
     */
	@Override
	public List<EolinkToEolink> getEolinkToEolink(Eolink parent, Eolink child, String tp) {
		Query query =em.createQuery("from EolinkToEolink t where t.parent.id = :parentId and t.child.id = :childId and t.tp.cd = :tp");
		query.setParameter("parentId", parent.getId());
		query.setParameter("childId", child.getId());
		query.setParameter("tp", tp);
		return query.getResultList();
		
	}

}
