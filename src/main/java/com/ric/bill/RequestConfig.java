package com.ric.bill;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.ric.bill.model.fn.Chng;

/**
 * Конфиг запроса
 * @author lev
 *
 */
@Slf4j
@Scope("prototype")
@Service
public class RequestConfig {
	// тип операции (0-начисление, 1-перерасчет)
	private Integer operTp;
	// включить распределение?
	private Boolean isDist;
	// перерасчет
	private Chng chng;
	// статус записи объема
	private Integer statusVol;
	
	// даты текущего периода (могут быть зависимы от перерасчета)
	Date curDt1;
	Date curDt2;
	// доля одного дня в периоде
	double partDays;
	// кол-во дней в периоде
	double cntCurDays;
	// Текущий период (для партицирования и проч.) 
	String period;
	// Период +1 месяц 
	String periodNext;
	// Период -1 месяц 
	String periodBack;
	
	// номер запроса
	private int rqn;
		
    @PersistenceContext
    private EntityManager em;
	
	/**
	 * инициализация
	 * @param config
	 * @param chng - объект перерасчета
	 * @param dist - признак распределения
	 * @param tp - тип операции
	 * @param chngId - id перерасчета
	 * @param rqn - уникальный номер запроса
	 * @param dt1 - заданная принудительно начальная дата начисления
	 * @param dt2 - заданная принудительно конечная дата начисления
	 */
	public void setUp(Config config, String dist, String tp, Integer chngId, int rqn, String genDt1, String genDt2) {
		// установить текущий номер запроса
		setRqn(rqn);
		// основные настройки
		
		// установить тип операции 
    	if (tp.equals("0")) {
			// начисление
    		setOperTp(0); //тип-начисление
    		if (dist.equals("1")) {
        		// распределять объем
        		setIsDist(true);
    		} else {
    			// не распределять объем
        		setIsDist(false);
    		}
			// установить статус записи объема
			setStatusVol(0);
    	} else if (tp.equals("1")) {
			// перерасчет
	    	setOperTp(1);  // тип-перерасчёт
        	Chng chng = em.find(Chng.class, chngId); // ID перерасчета = 175961
        	setChng(chng);
        	if (chng.getTp().getCd().equals("Корректировка показаний ИПУ")) {
	        	setIsDist(true); // распределять объем
	        	// статус записи объема - перерасчетный
	    		setStatusVol(1);
	    	} else {
	    		// для прочих видов перерасчетов
	        	setIsDist(false); // не распределять объем
				// статус записи объема
				setStatusVol(0);
	    	}
		} else {
			// начисление
			// установить статус записи объема
			setStatusVol(0);
		}
		

    	// прочие настройки
		if (operTp==0) {
			//начисление
			if (genDt1.length() > 0 && genDt2.length() > 0) {
				Date dt1 = Utl.getDateFromStr(genDt1);
				Date dt2 = Utl.getDateFromStr(genDt2);

				log.info("ВНИМАНИЕ! Для расчета RQN={}, заданы следующие даты расчета: dt1={}, dt2={}", rqn, dt1, dt2);

				// установить даты периода из параметров
		    	setCurDt1(dt1);
		    	setCurDt2(dt2);
			} else {
				// установить текущие даты периода
		    	setCurDt1(config.getCurDt1());
		    	setCurDt2(config.getCurDt2());
			}
		} else if (operTp==1) {
			// установить параметры перерасчета
			// установить текущие даты, для перерасчета
	    	setCurDt1(chng.getDt1());
	    	setCurDt2(chng.getDt2());
		}
		
		//задать текущий период в виде ГГГГММ
		setPeriod(Utl.getPeriodByDate(getCurDt1()));

		//кол-во дней в месяце
		setCntCurDays(Utl.getCntDaysByDate(getCurDt1()));
		
		//доля одного дня в периоде
		setPartDays(1/getCntCurDays());
		
    	// период на 1 мес.вперед
		setPeriodNext(Utl.addMonth(getPeriod(), 1));
    	// период на 1 мес.назад
		setPeriodBack(Utl.addMonth(getPeriod(), -1));
		
		log.info("Установлены периоды:");
		log.info("Текущий:{}, предыдущий:{}, будущий:{}", getPeriod(), getPeriodBack(), getPeriodNext());
	}

	public Integer getOperTp() {
		return operTp;
	}

	public void setOperTp(Integer operTp) {
		this.operTp = operTp;
	}

	public Boolean getIsDist() {
		return isDist;
	}

	private void setIsDist(Boolean isDist) {
		
		this.isDist = isDist;
	}

	public Chng getChng() {
		return chng;
	}

	public void setChng(Chng chng) {
		this.chng = chng;
	}
	
	public void setCurDt1(Date curDt1) {
		this.curDt1 = curDt1;
	}

	public Date getCurDt1() {
		return curDt1;
	}
	
	public void setCurDt2(Date curDt2) {
		this.curDt2 = curDt2;
	}

	public Date getCurDt2() {
		return curDt2;
	}

	public Integer getStatusVol() {
		return statusVol;
	}

	public void setStatusVol(Integer statusVol) {
		this.statusVol = statusVol;
	}

	public double getCntCurDays() {
		return this.cntCurDays;
	}

	public void setCntCurDays(double cntCurDays) {
		this.cntCurDays = cntCurDays;
	}
	
	public void setPartDays(double partDays) {
		this.partDays = partDays;
	}

	public double getPartDays() {
		return this.partDays;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPeriod() {
		return period;
	}

	public int getRqn() {
		return rqn;
	}

	public void setRqn(int rqn) {
		this.rqn = rqn;
	}

	public String getPeriodNext() {
		return periodNext;
	}

	public void setPeriodNext(String periodNext) {
		this.periodNext = periodNext;
	}

	public String getPeriodBack() {
		return periodBack;
	}

	public void setPeriodBack(String periodBack) {
		this.periodBack = periodBack;
	}
	
}
