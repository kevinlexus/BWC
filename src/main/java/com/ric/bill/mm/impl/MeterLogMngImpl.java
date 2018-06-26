package com.ric.bill.mm.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ric.bill.Calc;
import com.ric.bill.CntPers;
import com.ric.bill.MeterContains;
import com.ric.bill.SumNodeVol;
import com.ric.bill.dao.MeterDAO;
import com.ric.bill.dao.MeterLogDAO;
import com.ric.bill.dao.ServDAO;
import com.ric.bill.dto.MeterDTO;
import com.ric.bill.excp.CyclicMeter;
import com.ric.bill.excp.EmptyStorable;
import com.ric.bill.mm.KartMng;
import com.ric.bill.mm.MeterLogMng;
import com.ric.bill.mm.ParMng;
import com.ric.bill.mm.ServMng;
import com.ric.bill.model.ar.House;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.fn.Chng;
import com.ric.bill.model.mt.MLogs;
import com.ric.bill.model.mt.Meter;
import com.ric.bill.model.mt.MeterExs;
import com.ric.bill.model.mt.MeterLog;
import com.ric.bill.model.mt.MeterLogGraph;
import com.ric.bill.model.mt.Vol;
import com.ric.bill.model.oralv.Ko;
import com.ric.bill.model.sec.User;
import com.ric.bill.model.tr.Serv;
import com.ric.cmn.Utl;

import lombok.extern.slf4j.Slf4j;
/**
 * Сервис обработки счетчиков
 * @author lev
 * @version 1.00
 */
@Service
@Slf4j
public class MeterLogMngImpl implements MeterLogMng {

	@Autowired
	private MeterLogDAO mDao;
	@Autowired
	private MeterDAO meterDao;
	@Autowired
	private ServDAO servDao;
	@Autowired
	private ParMng parMng;
	@Autowired
	private KartMng kartMng;
	@Autowired
	private ServMng servMng;

	@PersistenceContext
    private EntityManager em;

	/**
	 * Получить все лог.счетчики по определённому объекту, типу и услуге
	 * @param - Объект
	 * @param serv - Услуга
	 * @param tp - Тип, если не указан - по всем
	 * @return - искомый список
	 */
	//@Cacheable(cacheNames="MeterLogMngImpl.getAllMetLogByServTp", key="{ #rqn, #mm.getKo().getId(), #serv.getId(), #tp }")
	@Override
	public List<MLogs> getAllMetLogByServTp(int rqn, MeterContains mm, Serv serv, String tp) {
		List<MLogs> lstMlg = new ArrayList<MLogs>(0);
		for (MLogs ml : mm.getMlog()) {
			//по типу, если указано
			if (tp == null || ml.getTp().getCd().equals(tp)) {
				//и услуге
				if (ml.getServ().equals(serv)) {
					lstMlg.add(ml);
				}
			}
		}
		return lstMlg;
	}

	/**
	 * проверить существование хотя бы одного физ.счетчика по лиц.счету по данной услуге
	 * @param rqn   - уникальный номер запроса
	 * @param kart  - лиц.счет
	 * @param serv  - услуга
	 * @param genDt - дата проверки
	 * @return - существует/нет
	 */
	//@Cacheable(cacheNames="MeterLogMngImpl.checkExsKartMet", key="{ #rqn, #kart.getLsk(), #serv.getId(), #genDt}")
	@Override
	public boolean checkExsKartMet(int rqn, Kart kart, Serv serv, Date genDt) {
		Optional<MeterLog> mLog;
		mLog = kart.getMlog().stream().filter(t -> t.getServ().equals(serv))
							.filter(t -> checkExsMet(rqn, t, genDt, true)==true).findAny();
		return mLog.isPresent();
	}

	/**
	 * проверить существование физ.счетчика (обычно для поиска счетчика ОДПУ)
	 * @param rqn - номер запроса
	 * @param mLog - лог.счетчик
	 * @param genDt - дата
	 * @param isFindGrp - поиск группового, если не найден по заданному
	 */
	//@Cacheable(cacheNames="MeterLogMngImpl.checkExsMet", key="{ #rqn, #mLog.getId(), #genDt, #isFindGrp}")
	@Override
	public boolean checkExsMet(int rqn, MLogs mLog, Date genDt, boolean isFindGrp) {
		//log.info("check mLog.id={}", mLog.getId());
    	// проверить существование хотя бы одного из физ счетчиков, по этому лог.сч.
    	for (Meter m: mLog.getMeter()) {
    		// период существования
    		for (MeterExs e: m.getExs()) {
    			// по соотв.периоду
    			if (Utl.between(genDt, e.getDt1(), e.getDt2())) {

	    			//if (e.getPrc() > 0d) // есть поставка объема Lev: убрал 19.04.2018
    				if (!Utl.nvl(e.getTp(),0D).equals(1D)) // ПУ в любом статусе, кроме "Отключенный" ред. Lev: 13.06.18
					 {
	    				return true;
	    			}
    			}
    		}
    	}

    	if (isFindGrp) {
        	// не найдены физ.счетчики по данному логическому,
        	// попробовать найти групповой счетчик, связанный с данным
        	// и поискать по нему физ.счетчики
        	MLogs grp = getLinkedNode(rqn, mLog, "ЛГрупп", genDt, true);
        	if (grp == null) {
        		//log.info("check групповой не найден! mLog.id={}", mLog.getId());
        		return false;
        	} else {
        		//log.info("check mLogGrp.id={}", grp.getId());
        		return checkExsMet(rqn, grp, genDt, false);
        	}
    	}
    	return false;
	}

	/**
	 * Получить объем, по ЛОГ СЧЕТЧИКУ, за период
	 * @param mLog - узел
	 * @param chngId - Id перерасчета (Integer - потому что chng.getId() - приведёт к NPE)
	 * @param chng - перерасчет - сам перерасчет, чтобы не искать em.find
	 * @param tp - тип распределения (здесь ТОЛЬКО для КЭША!)
	 * @param dt1 - нач.период
	 * @param dt2 - кон.период
	 * @return - возвращаемый объем
	 */
	//@Cacheable(cacheNames="MeterLogMngImpl.getVolPeriod1", key="{ #rqn, #chngId, #mLog.getId(), #tp, #dt1, #dt2}")
    @Override
	public  SumNodeVol getVolPeriod(int rqn, Integer chngId, Chng chng, MLogs mLog, int tp, Date dt1, Date dt2) {
		SumNodeVol lnkVol = new SumNodeVol();
		/* Java 8 */
		mLog.getVol().stream()  // ВАЖНО! ЗДЕСЬ нельзя parallelStream - получается непредсказуемый результат!!!
	                .filter(t-> isGetVol(t, chng)) // по перерасчету
	            	.filter(t-> Utl.between(t.getDt1(), dt1, dt2) && //здесь фильтр берет даты снаружи!
	        				    Utl.between(t.getDt2(), dt1, dt2))
					.forEach(t -> {
								if (t.getTp().getCd().equals("Фактический объем")) {
					    			lnkVol.addVol(t.getVol1());
								} else if (t.getTp().getCd().equals("Площадь и проживающие")) {
					    			lnkVol.addArea(t.getVol1());
					    			lnkVol.addPers(t.getVol2());
					    		} else if (t.getTp().getCd().equals("Лимит ОДН") ) {
					    			lnkVol.setLimit(t.getVol1()); //здесь set вместо add (будет одно значение) (как правило для ЛОДН счетчиков)
					    		}
							});

		return lnkVol;
	}


	/**
	 * получить совокупный объем по ОБЪЕКТУ, содержащему счетчики, по услуге, за период
	 * @param chngId - Id перерасчета
	 * @param chng - перерасчет
	 * @param mc - Объект, содержащий объемы
	 * @param serv - Услуга, по которой искать
	 * @param dt1 - нач.период
	 * @param dt2 - кон.период
	 * @return - возвращаемый объем
	 */
	//@Cacheable(cacheNames="MeterLogMngImpl.getVolPeriod2", key="{ #rqn, #chngId, #mc.getId(), #serv.getId(), #dt1, #dt2}")
	@Override
	public SumNodeVol getVolPeriod (int rqn, Integer chngId, Chng chng, MeterContains mc, Serv serv, Date dt1, Date dt2) {
		SumNodeVol amntSum = new SumNodeVol();
		MLogs lastMlwithVol = null, lastMl = null;
		//перебрать все лог.счетчики, доступные по объекту, сложить объемы
		for (MeterLog mLog: mc.getMlog()) {
			//log.info("check2 = {}", mLog.getServ());
			//по заданной услуге
			if (mLog.getServ().equals(serv)) {
				SumNodeVol tmp = getVolPeriod(rqn, chngId, chng, mLog, 0, dt1, dt2);
				amntSum.addArea(tmp.getArea());
				amntSum.addPers(tmp.getPers());
				amntSum.addVol(tmp.getVol());
				if ( tmp.getVol() != null) {
					if (tmp.getVol() > 0D) {
						// сохранить последний счетчик, если по нему есть объем
						lastMlwithVol = mLog;
					}
				}
				// сохранить последний счетчик
				lastMl = mLog;
			}
		}
		// сохранить номер ввода последнего счетчика
		if (lastMlwithVol != null) {
			amntSum.setEntry(lastMlwithVol.getEntry());
		} else if (lastMl != null) {
			amntSum.setEntry(lastMl.getEntry());
		}

		//вернуть объект, содержащий объемы
		return amntSum;

	}

	/**
	 * Вернуть логический счетчик определенного типа, связанный с заданным
	 * Если связано несколько - будет возвращён первый
	 * @param rqn - номер расчета
	 * @param mLog - тестируемый лог.счетчик
	 * @param tp - тип счетчика
	 * @param genDt - дата формир.
	 * @param serv - услуга (может быть не указана)
	 * @return лог.счетчик
	 */
	//@Cacheable(cacheNames = "MeterLogMngImpl.getLinkedNode", key="{#rqn, #mLog.getId(), #tp, #genDt, #isCheckServ }")
	@Override
	public MLogs getLinkedNode(int rqn, MLogs mLog, String tp, Date genDt, boolean isCheckServ) {
		MLogs lnkMLog = null;
		//найти прямую связь (направленную внутрь или наружу, не важно) указанного счетчика со счетчиком указанного типа
    	//сперва направленные внутрь
    	for (MeterLogGraph g : mLog.getInside()) {
			if (Utl.between(genDt, g.getDt1(), g.getDt2())) {
	    		if (g.getSrc().getTp().getCd().equals(tp)
	    				&& !isCheckServ? true : g.getSrc().getServ().equals(mLog.getServ()) ) {
	    			//найдено
	    			lnkMLog = g.getSrc();
	    			break;
	    		}
			}
    	}
    	//потом направленные наружу, если не найдено
    	if (lnkMLog == null) {
	    	for (MeterLogGraph g : mLog.getOutside()) {
				if (Utl.between(genDt, g.getDt1(), g.getDt2())) {
		    		if (g.getDst().getTp().getCd().equals(tp)
		    				&& !isCheckServ? true : g.getDst().getServ().equals(mLog.getServ())) {
		    			//найдено
		    			lnkMLog = g.getSrc();
		    			break;
		    		}
				}
	    	}
		}
		return lnkMLog;
	}

    /**
     * удалить рекурсивно все объемы по всем счетчикам начиная с указанного
     * @param mLog - начальный счетчик
     * @param tp - тип расчета
	 * @param chngId - Id перерасчета
	 * @param chng - перерасчет
     * @return
     * @throws CyclicMeter
     */
	//@Cacheable(cacheNames = "MeterLogMngImpl.delNodeVol", key="{#rqn, #chngId, #mLog.getId(), #tp, #dt1, #dt2 }") TODO 25.04.2018 временно отключил
	@Override
	public void delNodeVol(int rqn, Integer chngId, Chng chng, MLogs mLog, int tp, Date dt1, Date dt2)
			throws CyclicMeter {
		//удалять итератором, иначе java.util.ConcurrentModificationException
		for (Iterator<Vol> iterator = mLog.getVol().iterator(); iterator.hasNext();) {
		    Vol vol = iterator.next();
		    if (isGetVol(vol, chng) && // по перерасчету
		    			(vol.getTp().getCd().equals("Фактический объем") ||
	    				vol.getTp().getCd().equals("Площадь и проживающие") ||
	    				vol.getTp().getCd().equals("Лимит ОДН"))) {
			    if (dt1.getTime() <= vol.getDt1().getTime() && dt2.getTime() >= vol.getDt2().getTime()) {  //здесь диапазон дат "снаружи"
					iterator.remove();
		    	}
			}
		}

		//найти все направления, с необходимым типом, указывающие в точку из других узлов, удалить их объемы рекурсивно
		for (MeterLogGraph g : mLog.getInside()) {
			//log.info("mlog.id={}", g.getId());
			if (dt1.getTime() >= g.getDt1().getTime() && dt1.getTime() <= g.getDt2().getTime() ||
				dt2.getTime() >= g.getDt1().getTime() && dt2.getTime() <= g.getDt2().getTime()
					) { //здесь диапазон дат "внутри" (чтобы хотя бы одна из заданных дат была внутри диапазона

				try {
					if (tp==0 && g.getTp().getCd().equals("Расчетная связь")
							 || tp==1 && g.getTp().getCd().equals("Связь по площади и кол-во прож.")
							 || tp==2 && g.getTp().getCd().equals("Расчетная связь ОДН")
							 || tp==3 && g.getTp().getCd().equals("Расчетная связь пропорц.площади")) {
								//log.info("Check id={}", g.getSrc().getId());
								delNodeVol(rqn, chngId, chng, g.getSrc(), tp, dt1, dt2);
					}
				} catch (StackOverflowError e) {
					e.printStackTrace();
					throw new CyclicMeter("Возможно зациклен в графе счетчик MeterLog.id="+g.getSrc().getId());
				}
			}
		}

	}



	/**
	 * Получить лиц.счет, содержащий указанный счетчик
	 * @param mLog - Счетчик
	 * @return
	 */
	//@Cacheable(cacheNames="MeterLogMngImpl.getKart", key="{#rqn, #mLog.getId()}" )
	@Override
	public Kart getKart(int rqn, MLogs mLog) {
		return mDao.getKart(rqn, mLog);
	}



	/**
	 * Использовать ли в выборке данный объем
	 * @param vol - объем
	 * @param chng - перерасчет
	 */
	private boolean isGetVol(Vol vol, Chng chng) {
		if (chng==null && vol.getChng()==null || // брать обычный объем, без перерасчетов
			chng!=null && vol.getChng()!=null && vol.getChng().getId().equals(chng.getId())
					   && chng.getTp().getCd().equals("Корректировка показаний ИПУ") || // брать перерасчетный объем для данного типа перерасчета
			chng!=null && vol.getChng()==null && !chng.getTp().getCd().equals("Корректировка показаний ИПУ")) {// брать обычный объем для других типов перерасчетов
			return true;
		}
		return false;
	}

	/**
	 * Получить средний объем по физическому счетчику, за последние cntPeriod периодов
	 * Включая объемы по автоначислению.
	 * mLog - лог.счетчик
	 * cntPeriod - кол-во периодов ДО даты последней передачи показаний
	 * dt1 - дата начала текущего периода
	 */
	@Override
	public Double getAvgVol(Meter meter, int cntPeriod, Date dt1) {
		Double vol = null;
		// получить дату -N периодов назад
		Date backDateFirst = Utl.addMonths(dt1, cntPeriod * -1);// текущий период здесь НЕ входит
		// получить первый день периода -N
		Date backDateFirstDt = Utl.getFirstDate(backDateFirst);
		// получить дату -1 период назад
		Date backDateLast = Utl.addMonths(dt1, -1);
		// получить последний день периода -1 назад
		Date backDateLastDt = Utl.getLastDate(backDateLast);
		// получить весь объем за период
		Double vl = meterDao.getVolPeriod(meter, backDateFirstDt, backDateLastDt);
		// получить среднее арифм.
		BigDecimal volD = new BigDecimal(Utl.nvl(vl, 0D)/cntPeriod);
		volD = volD.setScale(6, RoundingMode.HALF_UP);
		vol = volD.doubleValue();

		return vol;
	}

	/**
	 * Средний объём и кол-во месяцев спустя последней передачи показаний
	 * @author lev
	 *
	 */
	public static class AvgVol {
		// средний объём
		public Double vol;
		// кол-во месяцев спустя последней передачи показаний
		public int cnt;
	}

	/**
	 * Получить средний объем по физическому счетчику, за последние cntPeriod периодов и кол-во месяцев спустя последней передачи показаний
	 * ДО даты последней передачи показаний, когда счетчик стал неисправен (недопуск и т.п.)
	 * Включая объемы по автоначислению.
	 * mLog - лог.счетчик
	 * cntMonthBack - кол-во месяцев ДО даты последней передачи показаний, для вычисления среднего объема
	 * dt1 - дата начала текущего периода
	 */
	@Override
	public AvgVol getAvgVolBeforeLastSend(Meter meter, int cntMonthBack, Date dt1) {
		AvgVol avgVol = new AvgVol();
		avgVol.vol = 0D;
		avgVol.cnt = 0;
		// получить последний переданный объем (в период исправного счетчика)
		Vol vol = meterDao.getLastVol(meter, dt1);
		if (vol != null) {
			log.info("Последний переданный объем: vol={}, dt1={}, dt2={}", vol.getVol1(), Utl.getStrFromDate(vol.getDt1()), Utl.getStrFromDate(vol.getDt2()));
			// получить кол-во месяцев спустя последней передачи показаний
			avgVol.cnt = (int) Utl.getDiffMonths(vol.getDt1(), dt1);
			if (avgVol.cnt <= cntMonthBack) {
				// кол-во периодов допустимо, для расчета по среднему
				// получить дату -N периодов назад
				Date backDateFirst = Utl.addMonths(vol.getDt1(), (cntMonthBack-1) * -1 ); // вычесть -1 так как последний период тоже входит
				// получить первый день периода -N
				Date backDateFirstDt = Utl.getFirstDate(backDateFirst);
				// получить весь объем за период
				Double vl = meterDao.getVolPeriod(meter, backDateFirstDt, vol.getDt2());
				log.info("Объем за весь период, когда счетчик был исправен(был доступ): vol={}, dt1={}, dt2={}", vl,
						Utl.getStrFromDate(backDateFirstDt), Utl.getStrFromDate(vol.getDt2()));
				// получить среднее арифм.
				BigDecimal avgVolD = new BigDecimal(vl/cntMonthBack);
				avgVolD = avgVolD.setScale(6, RoundingMode.HALF_UP);
				avgVol.vol = avgVolD.doubleValue();
			}
		}

		return avgVol;
	}

	/* Получить коэффициент к объему
	 * Параметры образуют коэффициент, который будет умножен на базовый объем физ. счетчика
	 * @param tp - статус счетчика
	 * @param user - пользователь
	 */
	@Override
	public Double getVolCoeff(Integer tp, User user) {
		Integer tpCheck = Utl.nvl(tp, 0);
		if (tpCheck.equals(1)) {
			// отключен счетчик, вернуть 0, без автоначисления
			return 0D;
		} else if (tpCheck.equals(0))  {
			// включен счетчик
			return 1D;
		} else if (user.getCd().equals("GEN"))  {
			// 2- неисправный, 3- не прошел поверку, 4- отказ допуска
			// вернуть 1, если он был автоначислен, но не переданный объем
			return 1D;
		}
		return 0D;

	}

	/* Получить все счетчики для автоначисления, отсортированные по лицевому счету
	 * @param house - дом, если не задан - весь фонд
	 * @param serv - услуга
	 * @param dt1 - начало периода
	 * @param dt2 - окончание периода
	 */
	@Override
	public List<MeterDTO> getAllMeterAutoVol(House house, Serv serv, Date dt1, Date dt2) {
		// получить все неисправные счетчики
		List<MeterDTO> lst = meterDao.getAllBrokenMeterByHouseServ(house, serv, dt2);
		// добавить те, по которым не было передано показаний
		lst.addAll(meterDao.getAllWoVolMeterByHouseServ(house, serv, dt1, dt2));

		// сортировка по лиц.сч.
//		Comparator<MeterDTO> byKartLsk = (e1, e2) -> e1
//				.getMeter().getMeterLog().getKart().compareTo(e2.getMeter().getMeterLog().getKart());
		Comparator<MeterDTO> byKartLsk = (e1, e2) -> e1
				.getLsk().compareTo(e2.getLsk());
		// сортировка по неисправности, в обратном порядке
		Comparator<MeterDTO> byTp = (e1, e2) -> - e1
				.getTp().compareTo(e2.getTp());
		// отсортировать по лиц.счету и по неисправности, в обратном порядке
		return lst.stream().sorted(byKartLsk.thenComparing(byTp))
				.collect(Collectors.toList());
	}


	/**
	 * Получить Сумму кол-во проживающих и общую площадь (в доле указанного дня), всех лицевых счетов,
	 * достигаемых с помощью направленной наружу Прямой связи (без переходов через другие счетчики) группового счетчика,
	 * с типом "Расчетная связь", для подсчета данных характеристик по всему Групповому счетчику mlog
	 * @param mlog - Групповой счетчик
	 * @param dt - дата подсчета
	 * @throws EmptyStorable
	 */
	@Override
	public SumNodeVol getSumOutsideCntPersSqr(Calc calc, Serv servChrg, MLogs mlog, Date genDt) throws EmptyStorable {
		int rqn = calc.getReqConfig().getRqn();
		Chng chng = calc.getReqConfig().getChng();
		SumNodeVol nodeVol = new SumNodeVol();

		List<MLogs> lst = mlog.getOutside().stream()
			.filter(t->
				Utl.between(genDt, t.getDt1(), t.getDt2()) &&
					t.getTp().getCd().equals("Расчетная связь"))
			.filter(t-> t.getDst().getTp().getCd().equals("ЛИПУ"))
			.map(t-> t.getDst())
			.collect(Collectors.toList());

		for (MLogs t : lst) {
				// общая площадь, в доле дня
				Double tmpArea = Utl.nvl(parMng.getDbl(rqn, t.getKart(), "Площадь.Общая", genDt, chng), 0d);
				Double partArea = tmpArea
						/ calc.getReqConfig().getCntCurDays();
				log.info("для группового сч Mlog.id={}, взято из Kart.flsk={}, общ.площадь={}",
						mlog.getId(), t.getKart().getFlsk(), tmpArea);
				//проживающие, в доле дня
				CntPers cntPers = kartMng.getCntPers(rqn, calc, t.getKart(), servChrg, genDt);
				Double partPers = cntPers.cntForVol / calc.getReqConfig().getCntCurDays();
				log.info("для группового сч Mlog.id={}, взято из Kart.flsk={}, кол-во прож.={}",
						mlog.getId(), t.getKart().getFlsk(), cntPers.cntForVol);

				// добавить кол-во прож. и общую площадь
				nodeVol.addPers(partPers);
				nodeVol.addArea(partArea);
		}

		log.info("Итого по групповому по дате={}, сч Mlog.id={}, кол-во прож.={}, общ.площадь={}",
				genDt, mlog.getId(), nodeVol.getPers(), nodeVol.getArea());

		return nodeVol;
	}


	/**
	 * Получить Ko счетчика, по Ko лиц.счета и номеру счетчика
	 * @param koLsk - Ko лиц.счета
	 * @param num - номер счетчика
	 */
	@Override
	public Ko getKoByLskNum(Ko koLsk, String num, String servCd) {
		Serv serv = servDao.getByCD(servCd);
		return meterDao.getKoByLskNum(koLsk, serv, num).stream().findFirst().orElse(null);
	}


	/**
	 * Сохранение объема по счетчику
	 * @param meter - физ.счетчик
	 * @param vol1 - объем
	 * @param chng - заголовок автоначисления
	 * @param user - пользователь
	 * @param dt1 - начало периода
	 * @param dt2 - окончание периода
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
	public void saveMeterVol(Meter meter, Double vol1, Chng chng, User user, Date dt1, Date dt2) {
		//log.info("================== Chng.id={}", chng.getId());
		// вызвать процедуру PL/SQL
		StoredProcedureQuery qr = em.createStoredProcedureQuery("mt.P_METER.meter_vol_ins_upd_java");
		qr.registerStoredProcedureParameter(1/*"P_IR"*/, Integer.class, ParameterMode.OUT);
		qr.registerStoredProcedureParameter(2/*"P_FK_METER"*/, Integer.class, ParameterMode.IN);
		qr.registerStoredProcedureParameter(3/*"P_FK_CHNG"*/, Integer.class, ParameterMode.IN);
		qr.registerStoredProcedureParameter(4/*"P_VOL1"*/, Double.class, ParameterMode.IN);
		qr.registerStoredProcedureParameter(5/*"P_DT1"*/, Date.class, ParameterMode.IN);
		qr.registerStoredProcedureParameter(6/*"P_DT2"*/, Date.class, ParameterMode.IN);
		qr.registerStoredProcedureParameter(7/*"P_USERNAME"*/, String.class, ParameterMode.IN);

		//log.info("{},{},{}", meter.getId(), chng.getId(), vol1);
		qr.setParameter(2/*"P_FK_METER"*/, meter.getId());
		qr.setParameter(3/*"P_FK_CHNG"*/, chng.getId());
		qr.setParameter(4/*"P_VOL1"*/, vol1);
		qr.setParameter(5/*"P_DT1"*/, dt1);
		qr.setParameter(6/*"P_DT2"*/, dt2);
		qr.setParameter(7/*"P_USERNAME"*/, user.getCd());
		qr.executeUpdate();//.execute();

		log.info("Вызвана Java процедура с параметрами chngId={}, p_fk_meter={}, p_vol1={}", chng.getId(), meter.getId(), vol1);

//		Пример, как вызывать процедуру с параметром, не удалять!
//		StoredProcedureQuery qr = em.createStoredProcedureQuery("mt.P_METER.meter_vol_ins_upd3");
//		qr.registerStoredProcedureParameter(1, Integer.class, ParameterMode.OUT);
//		qr.registerStoredProcedureParameter(2, Integer.class, ParameterMode.INOUT);
//		qr.registerStoredProcedureParameter(3, Integer.class, ParameterMode.IN);
//
//		Integer r =0;
//		Integer a =0;
//		//qr.setParameter(1, 177);
//		qr.setParameter(2, r);
//		qr.setParameter(3, a);
//		qr.execute();
//		Integer b = (Integer) qr.getOutputParameterValue(1);
		//log.info("Out param======================={}", b);
		//em.close();
	}

/*	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public void testTransact() {

			String ip = null;

			for (User user: meterDao.testTransactDao()) {
				//log.info("old={}, new={}", ip, user.getIp());
				if (ip!=null) {
					assert(ip.equals(user.getIp()));
				}
				ip = user.getIp();

				log.info("user.id={}, user.ip={}", user.getId(), user.getIp());
			}

			//User user = em.find(User.class, 4);
			//log.info("before ={}", user.getIp());

			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//em.clear();
			User user2 = em.find(User.class, 4);
			log.info("after ={}", user2.getIp());
		}
*/
}