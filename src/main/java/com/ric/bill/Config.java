package com.ric.bill;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.ric.bill.excp.EmptyStorable;
import com.ric.bill.mm.ObjMng;
import com.ric.bill.mm.ParMng;
import com.ric.bill.model.bs.Obj;

/**
 * Конфигуратор приложения
 * @author lev
 *
 */
@Service
@Slf4j
public class Config {

	@Autowired
	private ParMng parMng;
	@Autowired
	private ObjMng objMng;

	private Calendar calendar;
	static Date lastDt;//=new GregorianCalendar(2940, Calendar.JANUARY, 01).getTime();
	// наиболее ранняя и поздние даты в биллинге, константы
	static Date firstDt;//=new GregorianCalendar(1940, Calendar.JANUARY, 01).getTime();

	// даты текущего периода (не зависимо от перерасчета)
	Date curDt1;
	Date curDt2;
	
	// номер текущего запроса 
	private int reqNum = 0; 

	private List<Integer> workLst; // обрабатываемые лицевые счета 

	// Текущий период (для партицирования и проч.) 
	String period;
	// Период +1 месяц 
	String periodNext;
	// Период -1 месяц 
	String periodBack;
	
	// конструктор
	public Config() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
		
		//calendar = new GregorianCalendar(1940, Calendar.JANUARY, 1);
		//calendar.clear(Calendar.ZONE_OFFSET);
		firstDt = Utl.getFirstDt();//calendar.getTime();
		//calendar = new GregorianCalendar(2940, Calendar.JANUARY, 1);
		//calendar.clear(Calendar.ZONE_OFFSET);
		lastDt = Utl.getLastDt();//calendar.getTime();
		
		workLst = new ArrayList<Integer>();
	}
	
	/*
	 * Получить CD текущего пользователя
	 */
	public String getCurUserCd () {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		return auth.getName();
		//return "ttt";
	}
	
	@PostConstruct
	private void setUp() throws EmptyStorable {
		log.info("Версия модуля начисления - {}", "1.0.10");
		
		// Добавить path в Classpath, относительно нахождения Jar
		try {
			Utl.addPath("reports");
			Utl.addPath("config");
		} catch (Exception e) {
			log.error("Ошибка добавления path в Classpath");
		}
		// Распечатать Classpath
		ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();
    	log.info("**** Check Classpath: START ****");
        for(URL url: urls){
        	log.info(url.getFile());
        }
    	log.info("**** Check Classpath: END ****");
		
		
		// Объект приложения, получить даты текущего периода
		// TODO проверить RQN!
		Obj obj = objMng.getByCD(-1, "Модуль начисления");
		
		calendar = new GregorianCalendar();
		//calendar = new GregorianCalendar(2015, Calendar.OCTOBER, 15);
		calendar.clear(Calendar.ZONE_OFFSET);
		
		obj.getDw().size();
		
		calendar.setTime(parMng.getDate(-1, obj, "Начало расчетного периода"));
		setCurDt1(calendar.getTime());
		calendar.setTime(parMng.getDate(-1, obj, "Конец расчетного периода"));
		setCurDt2(calendar.getTime());
		
		//** здесь задаются периоды которые постоянны на всём времени работы программы!!! **// 
		//задать текущий период в виде ГГГГММ
		setPeriod(Utl.getPeriodByDate(getCurDt1()));
    	// период на 1 мес.вперед
		setPeriodNext(Utl.addMonth(getPeriod(), 1));
    	// период на 1 мес.назад
		setPeriodBack(Utl.addMonth(getPeriod(), -1));
		
		log.info("Начало расчетного периода = {}", getCurDt1());
		log.info("Конец расчетного периода = {}", getCurDt2());
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

	public static Date getLastDt() {
		return lastDt;
	}

	public static Date getFirstDt() {
		return firstDt;
	}

	// проверить наличие лицевого и добавить на обработку, если не найден
	public synchronized boolean checkLsk(Integer lsk) {
		if (this.workLst.contains(lsk)) {
			return false;
		} else {
			this.workLst.add(lsk);
			return true;
		}
		
	}
	
	// снять с обработки лицевой
	public synchronized void unCheckLsk(Integer lsk) {
		this.workLst.remove(lsk);
	}

	// получить следующий номер запроса
	public synchronized int incNextReqNum() {
		return this.reqNum++;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public String getPeriod() {
		return period;
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
