package com.ric.bill;

import java.util.Date;

import com.ric.bill.model.fn.Chng;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import javax.annotation.Generated;

/**
 * Конфиг запроса
 * @author lev
 * @version 1.00
 */
@Slf4j
@Getter @Setter
public class RequestConfig {
	// тип операции (0-начисление, 1-перерасчет)
	private Integer operTp;
	// включить распределение?
	private Boolean isDist;
	// перерасчет
	private Chng chng;
	
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

	/**
	 * Инициализация
	 * @param dist - признак распределения объемов
	 * @param tp - тип операции
	 * @param chngId - Id перерасчета
	 * @param rqn - уникальный номер запроса
	 * @param genDt1 - заданная принудительно начальная дата начисления
	 * @param genDt2 - заданная принудительно конечная дата начисления
	 * @param curDt1 - начальная дата начисления из базы
	 * @param curDt2 - заданная дата начисления из базы
	 */
	public void setUp(String dist, String tp, Integer chngId, int rqn, Date genDt1, Date genDt2, Chng chng, Date curDt1, Date curDt2) {
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
    	} else if (tp.equals("1")) {
			// перерасчет
	    	setOperTp(1);  // тип-перерасчёт
        	setChng(chng);
        	if (chng.getTp().getCd().equals("Корректировка показаний ИПУ")) {
	        	setIsDist(true); // распределять объем
	    	} else {
	    		// для прочих видов перерасчетов
	        	setIsDist(false); // не распределять объем
	    	}
		} else {
			// начисление
		}
		
    	// прочие настройки
		if (operTp==0) {
			//начисление
			if (genDt1 != null && genDt2 != null) {
				log.info("ВНИМАНИЕ! Для расчета RQN={}, заданы следующие даты расчета: dt1={}, dt2={}", rqn, genDt1, genDt2);
				// установить даты периода из параметров
		    	setCurDt1(genDt1);
		    	setCurDt2(genDt2);
			} else {
				// установить текущие даты периода
		    	setCurDt1(curDt1);
		    	setCurDt2(curDt2);
			}
		} else if (operTp==1) {
			// установить параметры перерасчета
			// установить текущие даты, для перерасчета
	    	setCurDt1(chng.getDt1());
	    	setCurDt2(chng.getDt2());
		}
		
		//задать текущий период в виде ГГГГММ
		setPeriod(Utl.getPeriodFromDate(getCurDt1()));

		//кол-во дней в месяце
		setCntCurDays(Utl.getCntDaysByDate(getCurDt1()));
		
		//доля одного дня в периоде
		setPartDays(1/getCntCurDays());
		
    	// период на 1 мес.вперед
		setPeriodNext(Utl.addMonths(getPeriod(), 1));
    	// период на 1 мес.назад
		setPeriodBack(Utl.addMonths(getPeriod(), -1));
		
		log.info("Установлены периоды:");
		log.info("Текущий:{}, предыдущий:{}, будущий:{}", getPeriod(), getPeriodBack(), getPeriodNext());
	}
	
}
