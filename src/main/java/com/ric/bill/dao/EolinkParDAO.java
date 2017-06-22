package com.ric.bill.dao;

import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.exs.EolinkPar;


public interface EolinkParDAO {


	public EolinkPar getEolinkPar(Eolink eolink, String parCd);
	
}
