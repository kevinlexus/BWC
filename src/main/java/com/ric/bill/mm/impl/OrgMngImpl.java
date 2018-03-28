package com.ric.bill.mm.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ric.bill.dao.OrgDAO;
import com.ric.bill.mm.OrgMng;
import com.ric.bill.model.bs.Org;

@Service("OrgMng_BWC")
public class OrgMngImpl implements OrgMng {

	@Autowired
	private OrgDAO orgDao;

    @PersistenceContext
    private EntityManager em;
	
	/**
	 * Получить все организации по типу
	 * @param tp - 0 - все, 1 - УК
	 * @return
	 */
    public List<Org> getOrgAll(int tp) {
		return orgDao.getOrgAll(tp);
	}


	
}