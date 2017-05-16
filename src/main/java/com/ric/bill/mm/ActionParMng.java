package com.ric.bill.mm;

import java.util.Date;

import com.ric.bill.model.exs.Action;

public interface ActionParMng {

	public Double getDbl(Action act, String cd);
	public String getStr(Action act, String cd);
	public Date getDate(Action act, String cd);

}