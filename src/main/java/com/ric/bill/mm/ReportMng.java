package com.ric.bill.mm;

import java.util.Date;
import java.util.List;

import com.ric.bill.dto.PeriodReportsDTO;

public interface ReportMng {
	
	public List<PeriodReportsDTO> getPeriodByCD(String repCd, int tp);
	public void addPeriodReport(String repCd, String mg, Date dt);

}
