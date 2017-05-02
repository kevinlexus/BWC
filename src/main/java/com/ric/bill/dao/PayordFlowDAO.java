package com.ric.bill.dao;

import java.util.Date;
import java.util.List;

import com.ric.bill.model.fn.PayordFlow;


public interface PayordFlowDAO {

	public List<PayordFlow> getPayordFlowByTpPeriod(Integer tp, String period);
	public List<PayordFlow> getPayordFlowByTpDt(Integer tp, Date dt);
	public List<PayordFlow> getPayordFlowBeforeDt(Integer payordId, Integer tp, Date dt);
	public List<PayordFlow> getPayordFlowBeforePeriod(Integer payordId, Integer tp, String period);
	
}
