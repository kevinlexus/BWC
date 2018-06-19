package com.ric.bill.mm;

import java.util.Date;
import java.util.List;

import com.ric.bill.Calc;
import com.ric.bill.MeterContains;
import com.ric.bill.SumNodeVol;
import com.ric.bill.dto.MeterDTO;
import com.ric.bill.excp.CyclicMeter;
import com.ric.bill.excp.EmptyStorable;
import com.ric.bill.mm.impl.MeterLogMngImpl.AvgVol;
import com.ric.bill.model.ar.House;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.fn.Chng;
import com.ric.bill.model.mt.MLogs;
import com.ric.bill.model.mt.Meter;
import com.ric.bill.model.oralv.Ko;
import com.ric.bill.model.sec.User;
import com.ric.bill.model.tr.Serv;

public interface MeterLogMng {

	List<MLogs> getAllMetLogByServTp(int rqn, MeterContains mm, Serv serv, String tp);
	boolean checkExsKartMet(int rqn, Kart kart, Serv serv, Date genDt);
	boolean checkExsMet(int rqn, MLogs mLog, Date genDt, boolean isFindGrp);
    SumNodeVol getVolPeriod (int rqn, Integer chngId, Chng chng, MLogs mLog, int tp, Date dt1, Date dt2);
	SumNodeVol getVolPeriod (int rqn, Integer chngId, Chng chng, MeterContains mc, Serv serv, Date dt1, Date dt2);
	MLogs getLinkedNode(int rqn, MLogs lnkMLog, String string, Date genDt, boolean isCheckServ);
	void delNodeVol(int rqn, Integer chngId, Chng chng, MLogs ml, int tp, Date dt1, Date dt2) throws CyclicMeter;
	Kart getKart(int rqn, MLogs mLog);
	List<MeterDTO> getAllMeterAutoVol(House house, Serv serv, Date dt1, Date dt2);
	Double getAvgVol(Meter meter, int cntPeriod, Date dt1);
	AvgVol getAvgVolBeforeLastSend(Meter meter, int cntMonthBack, Date dt1);
	Double getVolCoeff(Integer tp, User user);
	SumNodeVol getSumOutsideCntPersSqr(Calc calc, Serv servChrg, MLogs mlog, Date genDt) throws EmptyStorable;
	void saveMeterVol(Meter meter, Double vol1, Chng chng, User user, Date dt1, Date dt2);
	Ko getKoByLskNum(Ko koLsk, String num, String servCd);

}