package com.ric.bill.mm;

import java.util.Date;

import com.ric.bill.excp.WrongGetMethod;
import com.ric.bill.model.exs.Task;

public interface EolinkParMng {

	public Double getDbl(Integer id, String parCd) throws WrongGetMethod;
	public String getStr(Integer id, String parCd) throws WrongGetMethod;
	public Date getDate(Integer id, String parCd) throws WrongGetMethod;

}