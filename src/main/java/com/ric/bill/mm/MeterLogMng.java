package com.ric.bill.mm;

import java.util.Date;
import java.util.List;
import com.ric.bill.MeterContains;
import com.ric.bill.SumNodeVol;
import com.ric.bill.excp.CyclicMeter;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.fn.Chng;
import com.ric.bill.model.mt.MLogs;
import com.ric.bill.model.tr.Serv;

public interface MeterLogMng {

	public List<MLogs> getAllMetLogByServTp(int rqn, MeterContains mm, Serv serv, String tp);
	public boolean checkExsKartMet(int rqn, Kart kart, Serv serv, Date genDt);
	public boolean checkExsMet(int rqn, MLogs mLog, Date genDt, boolean isFindGrp);
    public SumNodeVol getVolPeriod (int rqn, Integer chngId, Chng chng, MLogs mLog, int tp, Date dt1, Date dt2);
	public SumNodeVol getVolPeriod (int rqn, Integer chngId, Chng chng, MeterContains mc, Serv serv, Date dt1, Date dt2);
	public MLogs getLinkedNode(int rqn, MLogs lnkMLog, String string, Date genDt, boolean isCheckServ);
	public void delNodeVol(int rqn, Integer chngId, Chng chng, MLogs ml, int tp, Date dt1, Date dt2) throws CyclicMeter;
	public Kart getKart(int rqn, MLogs mLog);

}