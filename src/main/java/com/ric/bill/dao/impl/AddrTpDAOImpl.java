package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ric.bill.dao.AddrTpDAO;
import com.ric.bill.dao.LstDAO;
import com.ric.bill.model.bs.AddrTp;
import com.ric.bill.model.bs.Lst;


@Repository
public class AddrTpDAOImpl implements AddrTpDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /* Получить типы адресов
	 * @param tp - 0 - весь список, 1 - ограниченный основными типами
     */
    public List<AddrTp> getByTp(Integer tp) {
		Query query =em.createQuery("select t from AddrTp t where t.cd in ('РКЦ','ЖЭО','РЭУ','Дом') order by t.npp");
		return query.getResultList();
	}

}
