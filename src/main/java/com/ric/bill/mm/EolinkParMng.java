package com.ric.bill.mm;

import java.util.Date;

import com.ric.bill.excp.WrongGetMethod;
import com.ric.bill.model.exs.Eolink;

public interface EolinkParMng {

	public Double getDbl(Eolink eolink, String parCd) throws WrongGetMethod;
	public void setDbl(Eolink eolink, String parCd, Double val) throws WrongGetMethod;
	public String getStr(Eolink eolink, String parCd) throws WrongGetMethod;
	public void setStr(Eolink eolink, String parCd, String val) throws WrongGetMethod;
	public Date getDate(Eolink eolink, String parCd) throws WrongGetMethod;
	public void setDate(Eolink eolink, String parCd, Date val) throws WrongGetMethod;

}