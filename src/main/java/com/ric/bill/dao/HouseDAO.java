package com.ric.bill.dao;

import java.util.Date;
import java.util.List;

import com.ric.bill.model.ar.House;


public interface HouseDAO {


	public List<House> findAll();
	public List<House> findAll2(Integer houseId, Integer areaId, Date dt1);
	
}
