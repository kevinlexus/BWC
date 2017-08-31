package com.ric.bill.mm;

import java.util.List;

import com.ric.bill.model.ar.Area;
import com.ric.bill.model.bs.AddrTp;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.oralv.Ko;

public interface LstMng {
	
	public Lst getByCD(String cd);
	public List<Lst> getByTp(String cdTp);
	public List<Ko> getKoByAddrTpFlt(Integer addrTp, String flt);
	public List<AddrTp> getAddrTpByTp(Integer tp);
	public AddrTp getAddrTpByCD(String cd);
}
