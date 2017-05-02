package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.dto.PayordDTO;
import com.ric.bill.model.fn.Payord;


public interface PayordDAO {

	public List<Payord> getPayordByPayordGrpId(Integer payordGrpId);
	
	public List<Payord> getPayordAll();
	
}
