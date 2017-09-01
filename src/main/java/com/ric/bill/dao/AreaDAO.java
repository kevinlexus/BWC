package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.ar.Area;


public interface AreaDAO {

	public List<Area> getAllHaveKlsk(Integer areaId);
	public Area getByKlsk(Integer klsk);
}
