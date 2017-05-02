package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.ar.House;
import com.ric.bill.model.bs.AddrTp;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.oralv.Ko;
import com.ric.bill.model.tr.Serv;


public interface KoDAO {

	public Ko getByKlsk(Integer klsk);
	public List<Ko> getKoByAddrTpFlt(Integer addrTp, String flt);
	
}
