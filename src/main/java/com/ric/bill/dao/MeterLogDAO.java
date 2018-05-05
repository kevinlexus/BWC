package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.MeterContains;
import com.ric.bill.model.ar.House;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.mt.MLogs;
import com.ric.bill.model.mt.Meter;
import com.ric.bill.model.tr.Serv;


public interface MeterLogDAO {

	public Kart getKart(int rqn, MLogs mLog);
	
}
