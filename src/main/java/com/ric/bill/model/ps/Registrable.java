package com.ric.bill.model.ps;

import java.util.Date;

import com.ric.bill.Simple;
import com.ric.bill.model.bs.Lst;

public interface Registrable {

	public Lst getTp();

	public void setTp(Lst tp);

	public Pers getPers();

	public void setPers(Pers pers);

	public Date getDtReg();

	public void setDtReg(Date dtReg);
	
	public Date getDtRegTs();
	
	public void setDtRegTs(Date dtRegTs);
	
	public Date getDtUnReg();
	
	public void setDtUnReg(Date dtUnReg);
	
	public Date getDtUnRegTs();
	
	public void setDtUnRegTs(Date dtUnRegTs);

}
