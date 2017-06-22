package com.ric.bill.mm;

import com.ric.bill.model.exs.Eolink;

public interface EolinkMng {

	public Eolink getEolinkByGuid(String guid);
	public Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd, 
			String kw, String entry, String tp);

}