package com.ric.bill.dao;

import java.util.Date;
import java.util.List;

import com.ric.bill.model.ar.Kart;


public interface KartDAO {

	public List<Kart> findAll(Integer houseId, Integer areaId, Integer tempLskId, Date dt1, Date dt2);
	
}
