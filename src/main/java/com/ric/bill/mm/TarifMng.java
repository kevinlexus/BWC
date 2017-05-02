package com.ric.bill.mm;

import java.util.Date;

import com.ric.bill.Calc;
import com.ric.bill.TarifContains;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.tr.Serv;

public interface TarifMng {

	public Double getProp(Calc calc, int rqn, TarifContains tc, Serv serv, String cd, Date genDt);	
	public Org getOrg(Calc calc, int rqn, TarifContains tc, Serv serv, Date genDt);
	public Double getChngVal(Calc calc, Serv serv, Date genDt, String cd, int lvlServ);
}