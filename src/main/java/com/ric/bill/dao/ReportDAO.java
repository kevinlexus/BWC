package com.ric.bill.dao;

import java.util.Date;
import java.util.List;

import com.ric.bill.dto.PeriodReportsDTO;
import com.ric.bill.model.bs.PeriodReports;
import com.ric.bill.model.bs.Report;


public interface ReportDAO {

	public Report getReportByCd(String cd);
	public List<PeriodReportsDTO> getPeriodByCD(String repCd, int tp);
	public PeriodReports getPeriod(String repCd, String mg);
	public PeriodReports getPeriod(String repCd, Date dt);
	
}
