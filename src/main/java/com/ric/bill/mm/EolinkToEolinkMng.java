package com.ric.bill.mm;

import java.util.List;

import com.ric.bill.model.exs.Eolink;

public interface EolinkToEolinkMng {

	public boolean saveParentChild(Eolink parent, Eolink child, String tp);

}