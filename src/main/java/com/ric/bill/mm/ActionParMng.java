package com.ric.bill.mm;

import java.util.Date;

import com.ric.bill.excp.WrongGetMethod;
import com.ric.bill.model.exs.Action;

public interface ActionParMng {

	public Double getDbl(Integer actionId, String parCd) throws WrongGetMethod;
	public String getStr(Integer actionId, String parCd) throws WrongGetMethod;
	public Date getDate(Integer actionId, String parCd) throws WrongGetMethod;

}