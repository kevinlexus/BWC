package com.ric.bill.mm;

import java.util.List;

import com.ric.bill.model.exs.Eolink;

public interface EolinkMng {

	public Eolink getEolinkByGuid(String guid);
	public List<Eolink> getLinkedEolink(Eolink eolink);
	public Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd, 
			String kw, String entry, String tp);

}