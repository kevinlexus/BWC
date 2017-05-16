package com.ric.bill.dao;

import com.ric.bill.model.bs.Par;
import com.ric.bill.model.exs.Action;


public interface ActionParDAO {


	public Par getByActionCd(Action action, String cd);
	
}
