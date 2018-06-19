package com.ric.bill.mm;

import java.util.Date;
import java.util.List;

import com.ric.bill.model.ar.House;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.oralv.Ko;

public interface HouseMng {

	List<House> findAll2(Integer houseId, Integer areaId, Integer tempLskId, Date dt1, Date dt2);
	Org getUkByDt(House house , Date dt);
	Ko getKoByKwNum(Integer houseKlsk, String num);
	Ko getPremisKo(Eolink houseEol, String num);
}
