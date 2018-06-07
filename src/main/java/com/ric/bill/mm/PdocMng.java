package com.ric.bill.mm;

import java.util.List;

import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.exs.Pdoc;

public interface PdocMng {

	public List<Pdoc> getPdocForLoadByHouse(Eolink houseEol);

}