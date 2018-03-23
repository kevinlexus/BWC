package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.exs.Eolink;

public interface EolinkDAO {
	
	public List<Eolink> getAll();
	public Eolink getEolinkByGuid(String guid);
	public List<Eolink> getChildByTp(Eolink parent, String tp);
	public Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd, 
			String kw, String entry, String tp);
	public List<Eolink> getValsNotSaved();
	public List<Eolink> getEolinkByTpWoTaskTp(String eolTp, String actTp, String parentCD);
	  
}
