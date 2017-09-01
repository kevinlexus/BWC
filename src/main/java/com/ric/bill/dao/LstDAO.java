package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.bs.Lst;


public interface LstDAO {

	public Lst getByCD(String cd);

	public List<Lst> getByTp(String cdTp);
	
}
