package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import lombok.extern.slf4j.Slf4j;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ric.bill.dao.VsecDAO;
import com.ric.bill.model.sec.User;
import com.ric.bill.model.sec.Vsec;


	@Repository
	@Slf4j
	public class VsecDAOImpl implements VsecDAO {
	
		//EntityManager - EM нужен на каждый DAO или сервис свой!
	    @PersistenceContext
	    private EntityManager em;
	
	    /*
	     * Проверить доступна ли пользователю привилегия по объекту, роли, действию 
	     * @param vol - объект объема
	     */
		//@Cacheable(cacheNames="VsecDAOImpl.getPrivByUserRoleAct")
	    public List<Vsec> getPrivByUserRoleAct(String userCd, String roleCd, String actCd) {
	    	log.info("userCd={}, roleCd={}, actCd={}", userCd, roleCd, actCd);
			Query query =em.createQuery("from Vsec t where upper(t.userCd)=upper(:usercd) and t.roleCd=:rolecd and t.actCd=:actcd");
			
			query.setParameter("usercd", userCd);
			query.setParameter("rolecd", roleCd);
			query.setParameter("actcd", actCd);
			
			return query.getResultList();
		}
	    	    
	    /*
	     * Получить пользователя по его CD 
	     * @param cd - CD пользователя
	     */
		//@Cacheable(cacheNames="VsecDAOImpl.getUserByCd")
	    public User getUserByCd(String userCd) {
			Query query =em.createQuery("from User t where upper(t.cd)=upper(:usercd)");
			query.setParameter("usercd", userCd);
			return (User) query.getSingleResult();
		}

	}
