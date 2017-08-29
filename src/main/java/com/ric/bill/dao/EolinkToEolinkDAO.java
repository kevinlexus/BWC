package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.exs.Eolink;

public interface EolinkToEolinkDAO {
	
	public List<Eolink> getLinkedEolink(Eolink eolink);
    public List<Eolink> getParentEolink(Eolink eolink, String tp);
	
}
