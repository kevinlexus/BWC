package com.ric.bill.mm;

import java.util.Date;
import java.util.List;
import com.ric.bill.Calc;
import com.ric.bill.CntPers;
import com.ric.bill.RegContains;
import com.ric.bill.Standart;
import com.ric.bill.TarifContains;
import com.ric.bill.excp.EmptyStorable;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.fn.PersPrivilege;
import com.ric.bill.model.fn.Privilege;
import com.ric.bill.model.fn.PrivilegeServ;
import com.ric.bill.model.ps.Pers;
import com.ric.bill.model.tr.Serv;

public interface KartMng  {

	public abstract void getCntPers(int rqn, Calc calc, RegContains rc, Serv serv, CntPers cntPers, Date genDt) throws EmptyStorable;
	public abstract Double getServPropByCD(int rqn, Calc calc, Serv serv, String string, Date genDt);
	public abstract Standart getStandartVol(int rqn, Calc calc, Serv serv, CntPers cntPers, Date genDt, int tp) throws EmptyStorable;
	public Org getOrg(int rqn, Calc calc, Serv serv, Date genDt);
	public List<Serv> getServAll(int rqn, Calc calc);
	public double getCapPrivs(int rqn, Calc calc, RegContains rc, Date genDt);
	public boolean getServ(int rqn, Calc calc, Serv serv, Date genDt);
	public List<Kart> findAll(Integer houseId, Integer areaId, Integer tempLskId, Date dt1, Date dt2);
    public List<Serv> checkServ(Calc calc, TarifContains tc, List lst, String cd, int cmd);
    public PersPrivilege getPersPrivilege(Pers pers, Serv serv, Date genDt);
    public PrivilegeServ getPrivilegeServ(Privilege priv, Serv serv);
}