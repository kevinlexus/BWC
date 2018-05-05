package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.tr.Serv;


public interface ServDAO {

	public Serv getMain(Serv serv);
	public List<Serv> getForDistVol();
	public Serv getByCD(String cd);	
	public List<Serv> getServAll();
	public List<Serv> getServAutoVol();
}
