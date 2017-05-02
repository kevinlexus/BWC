package com.ric.bill.mm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ric.bill.dao.ReportDAO;
import com.ric.bill.dto.PeriodReportsDTO;
import com.ric.bill.mm.ReportMng;

@Service
public class ReportMngImpl implements ReportMng {

	@Autowired
	private ReportDAO reportDao;

	
	/**
	 * Получить периоды отчета
	 * @param cd - CD отчета
	 * @param tp - 0-Выбрать дни, 1-Выбрать месяцы 	 
	 * @return
	 */
	public List<PeriodReportsDTO> getPeriodsByCD(String repCd, int tp) {
		
		return reportDao.getPeriodsByCD(repCd, tp);
		
	}


}