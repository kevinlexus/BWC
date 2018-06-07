package com.ric.bill.mm;

import java.util.List;

import com.ric.bill.excp.NotFoundUpperLevel;
import com.ric.bill.excp.TooManyRecursiveCalls;
import com.ric.bill.model.tr.Serv;
import com.ric.bill.model.tr.ServTree;

public interface ServMng {
	
	public Serv getMain(Serv serv);
	public List<Serv> getForDistVol();
	public Serv getByCD(String cd);
	public Serv getUpper(Serv serv, String tp) throws TooManyRecursiveCalls, NotFoundUpperLevel;
	public ServTree getUpperTree(ServTree servTree, String tp, int itr) throws TooManyRecursiveCalls;
	public List<Serv> getServAll();	

}
