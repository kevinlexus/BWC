package com.ric.bill.dao;

import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.mt.MLogs;


public interface MeterLogDAO {
	public Kart getKart(int rqn, MLogs mLog);
	/*public House getHouse(MLogs mLog);*/
	
}
