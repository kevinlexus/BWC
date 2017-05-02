package com.ric.bill.mm;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.ric.bill.Calc;
import com.ric.bill.CntPers;
import com.ric.bill.MeterContains;
import com.ric.bill.RegContains;
import com.ric.bill.Standart;
import com.ric.bill.TarifContains;
import com.ric.bill.excp.EmptyServ;
import com.ric.bill.excp.EmptyStorable;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.mt.MLogs;
import com.ric.bill.model.mt.MeterLog;
import com.ric.bill.model.tr.Serv;
import com.ric.bill.model.tr.TarifKlsk;

public interface KartMng  {

	public abstract void getCntPers(int rqn, Calc calc, RegContains rc, Serv serv, CntPers cntPers, Date genDt) throws EmptyStorable;
	public abstract Double getServPropByCD(int rqn, Calc calc, Serv serv, String string, Date genDt);
	public abstract Standart getStandartVol(int rqn, Calc calc, Serv serv, CntPers cntPers, Date genDt, int tp) throws EmptyStorable;
	public Org getOrg(int rqn, Calc calc, Serv serv, Date genDt);
	public List<Serv> getServAll(int rqn, Calc calc);
	public double getCapPrivs(int rqn, Calc calc, RegContains rc, Date genDt);
	public boolean getServ(int rqn, Calc calc, Serv serv, Date genDt);
	public List<Kart> findAll(Integer houseId, Integer areaId, Date dt1);
}