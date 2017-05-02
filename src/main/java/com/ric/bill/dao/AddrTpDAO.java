package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.bs.AddrTp;


public interface AddrTpDAO {

	public List<AddrTp> getByTp(Integer tp);
	
}
