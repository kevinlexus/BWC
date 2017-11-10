package com.ric.bill.dao.impl;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.ReportDAO;
import com.ric.bill.dto.PeriodReportsDTO;
import com.ric.bill.model.bs.PeriodReports;
import com.ric.bill.model.bs.Report;


@Repository
public class ReportDAOImpl implements ReportDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

    /**
     * Получить отчет по его CD
     * @param cd - CD отчета
     * @return
     */
    public Report getReportByCd(String cd) {
		Query query = em.createQuery("select t from Report t where t.cd = :cd");
		query.setParameter("cd", cd);
		return (Report) query.getSingleResult();
    }

    /**
     * Получить периоды отчета для Web-интерфейса
     * param repCd - CD отчета
     * param tp - 0-Выбрать дни, 1-Выбрать месяцы 	 
     */
    public List<PeriodReportsDTO> getPeriodByCD(String cd, int tp) {
    	List<PeriodReportsDTO> lst = new ArrayList<PeriodReportsDTO>(0);
    	List<PeriodReports> lst2 = null;
		Query query = null;
		if (tp==0) {
			query = em.createQuery("select t from PeriodReports t join t.report r where r.cd = :cd and t.mg is not null order by t.mg desc");
			query.setParameter("cd", cd);
			 lst2 = query.getResultList();
			lst2.stream().forEach(t -> lst.add(new PeriodReportsDTO(t.getId(), t.getMg().substring(0, 4)+"."+t.getMg().substring(4, 6))));
				
		} else if (tp==1) {
			query = em.createQuery("select t from PeriodReports t join t.report r where r.cd = :cd and t.dt is not null order by t.dt");
			query.setParameter("cd", cd);
			lst2 = query.getResultList();
			Format formatter = new SimpleDateFormat("dd.MM.yyyy");
			lst2.stream().forEach(t -> lst.add(new PeriodReportsDTO(t.getId(), formatter.format(t.getDt())
					   )));
		}
		return lst;
	}


	public PeriodReports getPeriod(String repCd, String mg) {
		//System.out.println(repCd+"--"+mg);
		Query query = em.createQuery("select t from PeriodReports t join t.report r where r.cd = :cd and t.mg = :mg");
		query.setParameter("cd", repCd);
		query.setParameter("mg", mg);
		try {
			return (PeriodReports) query.getSingleResult();
		} catch (NoResultException e) {
		  return null;
		} 
	}

	public PeriodReports getPeriod(String repCd, Date dt) {
		Query query = em.createQuery("select t from PeriodReports t join t.report r where r.cd = :cd and t.dt = :dt");
		query.setParameter("cd", repCd);
		query.setParameter("dt", dt);
		try {
			return (PeriodReports) query.getSingleResult();
		} catch (NoResultException e) {
		  return null;
		} 
	}


}
