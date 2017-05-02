package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.ar.Area;
import com.ric.bill.model.ar.House;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.tr.Serv;


public interface AreaDAO {

	public List<Area> getAll();
	public Area getByKlsk(Integer klsk);
}
