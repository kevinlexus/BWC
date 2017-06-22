package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.EolinkDAO;
import com.ric.bill.model.exs.Eolink;


@Repository
public class EolinkDAOImpl implements EolinkDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
    
    //конструктор
    public EolinkDAOImpl() {
    	
    }
    
    //вернуть список необработанных действий
    public List<Eolink> getAll() {
			Query query =em.createQuery("from Eolink t");
			return query.getResultList();
	}
    
    /**
     * Получить Связанный объект по GUID
     * @param guid - GUID
     * @return
     */
    public Eolink getEolinkByGuid(String guid) {
		Query query =em.createQuery("select t from Eolink t where t.guid = :guid");
		query.setParameter("guid", guid);
		try {
			return (Eolink) query.getSingleResult();
		} catch (NoResultException e) {
		  return null;
		} 
	}

    /**
     * Получить Связанный объект по reu,kul,nd
     * @param reu - REU из Квартплаты
     * @param kul - KUL из Квартплаты
     * @param nd -  ND из Квартплаты
     * @param kw -  KW из Квартплаты
     * @param entry -  ENTRY из Квартплаты
     * @param tp -  тип объекта
     */
	public Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd, 
			String kw, String entry, String tp) {
		Query query = null;
		switch (tp) {
		case "Дом":
			query =em.createQuery("select t from Eolink t where t.reu = :reu and "
					+ "t.kul=:kul and t.nd=:nd and t.objTp.cd = :tp");
			query.setParameter("reu", reu);
			query.setParameter("kul", kul);
			query.setParameter("nd", nd);
			query.setParameter("tp", tp);
			break;

		case "Квартира":
			query =em.createQuery("select t from Eolink t where t.reu = :reu and "
					+ "t.kul=:kul and t.nd=:nd and t.kw=:kw and t.objTp.cd = :tp");
			query.setParameter("reu", reu);
			query.setParameter("kul", kul);
			query.setParameter("nd", nd);
			query.setParameter("kw", kw);
			query.setParameter("tp", tp);
			break;

		case "Подъезд":
			query =em.createQuery("select t from Eolink t where t.reu = :reu and "
					+ "t.kul=:kul and t.nd=:nd and t.entry=:entry and t.objTp.cd = :tp");
			query.setParameter("reu", reu);
			query.setParameter("kul", kul);
			query.setParameter("nd", nd);
			query.setParameter("entry", entry);
			query.setParameter("tp", tp);
			break;

		case "Организация":
			query =em.createQuery("select t from Eolink t where t.reu = :reu and t.objTp.cd = :tp");
			query.setParameter("reu", reu);
			query.setParameter("tp", tp);
			break;

		}

		try {
			return (Eolink) query.getSingleResult();
		} catch (NoResultException e) {
		  return null;
		} 
	}

}
