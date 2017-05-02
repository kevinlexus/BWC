package com.ric.bill.mm.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ric.bill.dao.OrgDAO;
import com.ric.bill.mm.OrgMng;
import com.ric.bill.model.bs.Org;

@Service
public class OrgMngImpl implements OrgMng {

	@Autowired
	private OrgDAO orgDao;

    @PersistenceContext
    private EntityManager em;
	
	// Список всех организаций
    public List<Org> getOrgAll() {
		return orgDao.getOrgAll();
	}

	// Список всех УК
	public List<Org> getOrgUkAll() {
		return orgDao.getOrgUkAll();
	}

	
}