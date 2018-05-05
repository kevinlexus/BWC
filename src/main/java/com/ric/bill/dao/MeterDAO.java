package com.ric.bill.dao;

import java.util.Date;
import java.util.List;

import com.ric.bill.dto.MeterDTO;
import com.ric.bill.model.ar.House;
import com.ric.bill.model.mt.Meter;
import com.ric.bill.model.mt.Vol;
import com.ric.bill.model.tr.Serv;


public interface MeterDAO {
	public List<MeterDTO> getAllWoVolMeterByHouseServ(House house, Serv serv, Date dt1, Date dt2);
	public List<MeterDTO> getAllBrokenMeterByHouseServ(House house, Serv serv, Date dt);
	public Vol getLastVol(Meter meter);
	public List<Meter> getXxx();
	public Double getVolPeriod(Meter meter, Date dt1, Date dt2);
}
