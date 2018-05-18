package com.ric.bill;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

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
import com.ric.cmn.Utl;

import lombok.extern.slf4j.Slf4j;

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

	static Date lastDt;//=new GregorianCalendar(2940, Calendar.JANUARY, 01).getTime();
	// наиболее ранняя и поздние даты в биллинге, константы
	static Date firstDt;//=new GregorianCalendar(1940, Calendar.JANUARY, 01).getTime();

	// даты текущего периода (не зависимо от перерасчета)
	Date curDt1;
	Date curDt2;

	// номер текущего запроса
	private int reqNum = 0;

	//private List<Integer> workLst; // обрабатываемые лицевые счета

	// Текущий период (для партицирования и проч.)
	String period;
	// Период +1 месяц
	String periodNext;
	// Период -1 месяц
	String periodBack;
	// Тип приложения, по умолчанию - 0
	//Integer appTp = 0;
	// Путь и наименование файла, для отправки в квартплату показаний по счетчикам
	//String pathCounter ="C:\\temp\\test1.txt";

	// Запретить начислять по лиц.счетам, если формируется глобальное начисление
	Boolean isRestrictChrgLsk = false;

	// внутренний класс, для обработки блокировок по объектам
	class Lock {
		public List<Integer> lskChrg;
		public List<Integer> houseChrg;
		public List<Integer> houseDist;
		// конструктор
		Lock() {
			lskChrg = new ArrayList<Integer>();
			houseChrg = new ArrayList<Integer>();
			houseDist = new ArrayList<Integer>();
		}

		// блокировка для начисления и распределения по лиц.счету
		public synchronized Boolean setLockChrgLsk(Integer rqn, Integer lsk, Integer house) {
			if (this.houseDist.contains(house)) {
				// запрет начисления, идёт распределение объемов по дому
				log.info("==LOCK== RQN={}, запрет начисления по lsk={}, идёт распределение объемов по дому: house.id={}!", rqn, lsk, house);
				return false;
			} else if (this.lskChrg.contains(lsk)) {
				// запрет начисления, идёт распределение объемов по дому
				log.info("==LOCK== RQN={}, запрет начисления по lsk={}, идёт начисление другим потоком по: house.id={}", rqn, lsk, house);
				return false;
			} else {
				// выполнить блокировку для начисления
				this.houseChrg.add(house);
				this.lskChrg.add(lsk);
				log.info("==LOCK== RQN={}, блокировка для начисления выполнена: house.id={}, lsk={}", rqn, house, lsk);
				return true;
			}

		}

		// разблокировать лиц.счет
		public synchronized void unlockChrgLsk(Integer rqn, Integer lsk, Integer house) {
			log.info("==LOCK== RQN={}, блокировка для начисления снята: house.id={}, lsk={}", rqn, house, lsk);
			this.lskChrg.remove(lsk);
			this.houseChrg.remove(house);
		}

		// разблокировать дом
		public synchronized void unlockDistHouse(Integer rqn, Integer house) {
			log.info("==LOCK== RQN={}, блокировка для распределения снята: house.id={}", rqn, house);
			this.houseDist.remove(house);
		}

		// блокировка для распределения по дому
		public synchronized Boolean setLockDistHouse(Integer rqn, Integer house) {

			if (this.houseDist.contains(house)) {
				// запрет начисления, идёт распределение объемов по дому
				log.info("==LOCK== RQN={}, запрет распределения, уже идёт распределение объемов по этому дому: house.id={}", rqn, house);
				return false;
			} else if (this.houseChrg.contains(house)) {
				// запрет начисления, идёт начисление по лицевому в этом доме
				log.info("==LOCK== RQN={}, запрет распределения, идёт начисление по лицевому в этом доме: house.id={}", rqn, house);
				return false;
			} else {
				// выполнить блокировку для начисления
				this.houseDist.add(house);
				log.info("==LOCK== RQN={}, блокировка для распределения выполнена: house.id={}", rqn, house);
				return true;
			}

		}
	}

	// блокировщик выполнения процессов
	public Lock lock;

	// конструктор
	public Config() {
		TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
		firstDt = Utl.getFirstDt();//calendar.getTime();
		lastDt = Utl.getLastDt();//calendar.getTime();
		// блокировщик процессов
		lock = new Lock();
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
		log.info("");
		log.info("-----------------------------------------------------------------");
		log.info("Версия модуля начисления - {}", "1.0.16");

		// Добавить path в Classpath, относительно нахождения Jar
		try {
			Utl.addPath("reports");
			Utl.addPath("config");
		} catch (Exception e) {
			log.error("Ошибка добавления path в Classpath");
		}
		// Распечатать Classpath
/*		ClassLoader cl = ClassLoader.getSystemClassLoader();
        URL[] urls = ((URLClassLoader)cl).getURLs();
    	log.info("**** Check Classpath: START ****");
        for(URL url: urls){
        	log.info(url.getFile());
        }
    	log.info("**** Check Classpath: END****");*/

		log.info("Начало расчетного периода = {}", getCurDt1());
		log.info("Конец расчетного периода = {}", getCurDt2());
		log.info("-----------------------------------------------------------------");
		log.info("");
	}

	// Получить Calendar текущего периода
	////@Cacheable(cacheNames="Config.getCalendarCurrentPeriod") Пока отключил 24.11.2017
	private List<Calendar> getCalendarCurrentPeriod() {
			List<Calendar> calendarLst = new ArrayList<Calendar>();
	    	Obj obj = objMng.getByCD(-1, "Модуль начисления");

			Calendar calendar1, calendar2;
			calendar1 = new GregorianCalendar();
			calendar1.clear(Calendar.ZONE_OFFSET);

			calendar2 = new GregorianCalendar();
			calendar2.clear(Calendar.ZONE_OFFSET);

			obj.getDw().size();

			try {
				calendar1.setTime(parMng.getDate(-1, obj, "Начало расчетного периода"));
				calendarLst.add(calendar1);

				calendar2.setTime(parMng.getDate(-1, obj, "Конец расчетного периода"));
				calendarLst.add(calendar2);
			} catch (EmptyStorable e) {
				e.printStackTrace();
				throw new RuntimeException("Параметр Расчетного периода не может быть загружен!");
			}
			return calendarLst;
	}

	public String getPeriod() {
		return Utl.getPeriodFromDate(getCalendarCurrentPeriod().get(0).getTime());
	}

	public String getPeriodNext() {
		return Utl.addMonths(Utl.getPeriodFromDate(getCalendarCurrentPeriod().get(0).getTime()), 1);
	}

	public String getPeriodBack() {
		return Utl.addMonths(Utl.getPeriodFromDate(getCalendarCurrentPeriod().get(0).getTime()), -1);
	}

	public Date getCurDt1() {
		return getCalendarCurrentPeriod().get(0).getTime();
	}

	public Date getCurDt2() {
		return getCalendarCurrentPeriod().get(1).getTime();
	}

	public static Date getLastDt() {
		return lastDt;
	}

	public static Date getFirstDt() {
		return firstDt;
	}

	// получить следующий номер запроса
	public synchronized int incNextReqNum() {
		return this.reqNum++;
	}

	/*public Integer getAppTp() {
		return appTp;
	}

	public void setAppTp(Integer appTp) {
		this.appTp = appTp;
	}

	public String getPathCounter() {
		return pathCounter;
	}

	public void setPathCounter(String pathCounter) {
		this.pathCounter = pathCounter;
	}*/

	public Boolean getIsRestrictChrgLsk() {
		return isRestrictChrgLsk;
	}

	public void setIsRestrictChrgLsk(Boolean isRestrictChrgLsk) {
		this.isRestrictChrgLsk = isRestrictChrgLsk;
	}


}
