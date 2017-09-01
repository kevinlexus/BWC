package com.ric.bill.mm.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ric.bill.dao.ReportDAO;
import com.ric.bill.dto.PeriodReportsDTO;
import com.ric.bill.mm.ReportMng;
import com.ric.bill.model.bs.PeriodReports;
import com.ric.bill.model.bs.Report;

@Service
public class ReportMngImpl implements ReportMng {

	@Autowired
	private ReportDAO reportDao;

	
	/**
	 * Получить периоды отчета
	 * @param repCd - CD отчета
	 * @param tp - 0-Выбрать дни, 1-Выбрать месяцы 	 
	 * @return
	 */
	public List<PeriodReportsDTO> getPeriodByCD(String repCd, int tp) {
		
		return reportDao.getPeriodByCD(repCd, tp);
		
	}


	/** 
	 * Добавить новый период отчета
	 * @param repCd - CD отчета
	 * @param mg - месяц
	 * @param dt - дата
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void addPeriodReport(String repCd, String mg, Date dt) {
		if (mg != null) {
			// найти уже созданный период
			if (reportDao.getPeriod(repCd, mg) == null) {
				// не найден период - создать
				Report rep = reportDao.getReportByCd(repCd);
				PeriodReports pr = new PeriodReports(mg, null); 
				rep.getPeriod().add(pr);
			} 
		} else if (dt != null) {
			if (reportDao.getPeriod(repCd, dt) == null) {
				// не найден период - создать
				Report rep = reportDao.getReportByCd(repCd);
				PeriodReports pr = new PeriodReports(null, dt); 
				rep.getPeriod().add(pr);
			} 
		}
		
	}
	
}
