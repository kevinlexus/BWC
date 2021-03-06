package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.bs.Org;


public interface OrgDAO {

	public Org getByKlsk(int klsk);
	public Org getByCD(String cd);
	public List<Org> getOrgAll(int tp);
}
