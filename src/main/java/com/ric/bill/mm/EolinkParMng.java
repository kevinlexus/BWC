package com.ric.bill.mm;

import java.util.Date;

import com.ric.bill.model.exs.Eolink;
import com.ric.cmn.excp.WrongGetMethod;

public interface EolinkParMng {

	public Boolean getBool(Eolink eolink, String parCd) throws WrongGetMethod;
	public void setBool(Eolink eolink, String parCd, Boolean val) throws WrongGetMethod;
	public Double getDbl(Eolink eolink, String parCd) throws WrongGetMethod;
	public void setDbl(Eolink eolink, String parCd, Double val) throws WrongGetMethod;
	public String getStr(Eolink eolink, String parCd) throws WrongGetMethod;
	public void setStr(Eolink eolink, String parCd, String val) throws WrongGetMethod;
	public Date getDate(Eolink eolink, String parCd) throws WrongGetMethod;
	public void setDate(Eolink eolink, String parCd, Date val) throws WrongGetMethod;

}