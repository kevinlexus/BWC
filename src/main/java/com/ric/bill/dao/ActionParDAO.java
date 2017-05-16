package com.ric.bill.dao;

import com.ric.bill.model.exs.ActionPar;


public interface ActionParDAO {


	public ActionPar getByActionCd(Integer actionId, String parCd);
	
}
