package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.fn.PayordGrp;


public interface PayordGrpDAO {

	public PayordGrp getPayordGrpById(Integer id);
	public List<PayordGrp> getPayordGrpAll();
	
}
