package com.ric.bill.dao;

import java.util.Date;
import java.util.List;

import com.ric.bill.ResultSet;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.ps.Pers;


public interface KartDAO {

	public Kart getByFlsk(String flsk);
	public List<ResultSet> findAllLsk(Integer houseId, Integer areaId, Integer tempLskId, Date dt1, Date dt2);	
}
