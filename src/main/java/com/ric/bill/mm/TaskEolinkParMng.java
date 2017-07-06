package com.ric.bill.mm;

import java.util.Date;

import com.ric.bill.excp.WrongGetMethod;
import com.ric.bill.model.exs.Task;

public interface TaskEolinkParMng {
	public Double getDbl(Task task, String parCd) throws WrongGetMethod;
	public String getStr(Task task, String parCd) throws WrongGetMethod;
	public Date getDate(Task task, String parCd) throws WrongGetMethod;
	public void acceptPar(Task task);
}
