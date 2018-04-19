package com.ric.bill.mm.impl;

import java.util.Date;
import java.util.Optional;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ric.bill.Calc;
import com.ric.bill.TarifContains;
import com.ric.bill.Utl;
import com.ric.bill.dao.TarifDAO;
import com.ric.bill.mm.TarifMng;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.fn.Chng;
import com.ric.bill.model.fn.ChngVal;
import com.ric.bill.model.tr.Serv;
import com.ric.bill.model.tr.TarifKlsk;
import com.ric.bill.model.tr.TarifServProp;

/**
 * Сервис работы с тарифами
 * @author lev
 *
 */
@Slf4j
@Service
public class TarifMngImpl implements TarifMng {
	
	@Autowired
	private TarifDAO tarDao;

	/**
	 * Получить значение типа Double тарифа по CD 
	 * @param tc - объект
	 * @param cd - код свойства
	 * @return - свойство
	 */
	//@Cacheable(cacheNames="TarifMngImpl.getProp", key="{#rqn, #tc.getKo().getId(), #serv.getId(), #cd, #genDt }") 
	public Double getProp(Calc calc, int rqn, TarifContains tc, Serv serv, String cd, Date genDt) {
			Boolean isChng = false;
			Chng chng = null; 
			// необходимо ли искать привязку тарифа к перерасчету
			if (calc.getReqConfig().getChng() != null && calc.getReqConfig().getChng().getTp().getCd().equals("Начисление за прошлый период")) {
				isChng = true;
				chng = calc.getReqConfig().getChng(); 
			}
				
			for (TarifKlsk k : tc.getTarifklsk()) {
				// Искать строки тарифа предназначенного для перерасчета (если задано) или не предназначенного
				if (isChng && k.getChng()!=null && k.getChng().equals(chng) || !isChng && k.getChng()==null) {
					//затем по строкам - составляющим тариф 
					for (TarifServProp t : k.getTarprop()) {
						if (Utl.between(genDt, t.getDt1(), t.getDt2())) {
							if (t.getServ().equals(serv) && t.getProp().getCd().equals(cd)) {
								return t.getN1();
							}
						}
					}
				}
			}
		return null;
		
	}

	/**
	 * Получить организацию по услуге в тарифе 
	 * @param tc - объект
	 * @param cd - код свойства
	 * @return - свойство
	 */
	//@Cacheable(cacheNames="TarifMngImpl.getOrg", key="{#rqn,  #tc.getKo().getId(), #serv.getId(), #genDt }") 
	public Org getOrg(Calc calc, int rqn, TarifContains tc, Serv serv, Date genDt) {
		Boolean isChng = false;
		Chng chng = null; 
		// необходимо ли искать привязку тарифа к перерасчету
		if (calc.getReqConfig().getChng() != null && calc.getReqConfig().getChng().getTp().getCd().equals("Начисление за прошлый период")) {
			isChng = true;
			chng = calc.getReqConfig().getChng(); 
		}

		for (TarifKlsk k : tc.getTarifklsk()) {
			// Искать строки тарифа предназначенного для перерасчета (если задано) или не предназначенного
			if (isChng && k.getChng()!=null && k.getChng().equals(chng) || !isChng && k.getChng()==null) {
				//затем по строкам - составляющим тариф 
				for (TarifServProp t : k.getTarprop()) {
					if (Utl.between(genDt, t.getDt1(), t.getDt2())) {
						if (t.getServ().equals(serv) && t.getProp().getCd().equals("Поставщик")) {
							/* Если включить, покажет, откуда была извлечена организация
							if (serv.getId()==32) {
								log.info("TarifKlsk.id={}", k.getId());
								log.info("TarifServProp.id={}", t.getId());
								log.info("TarifServProp.dt1={}, dt2", t.getId());
								log.info("Erfind2 set value: rqn={}, serv.id={}, genDt={}, org={}", rqn, serv.getId(), genDt, t.getOrg().getId()) ;
							}*/
							return t.getOrg();
						}
					}
				}
			}
		}
		return null;
		
	}

	/**
	 *  получить значение перерасчета
	 * @param genDt - при наличии - фильтр по дате
	 * @param serv - услуга
	 * @param cd - тип перерасчета
	 * @param lvlServ - уровень, как фильтровать услугу, 0 - по Chng, 1 - по ChngLsk  
	 * @return
	 */
	public Double getChngVal(Calc calc, Serv serv, Date genDt, String cd, int lvlServ) {
		log.trace("Serv={} lsk={}", serv.getId(), calc.getKart().getLsk());
		Chng chng = calc.getReqConfig().getChng();	
		Double val = null;
		if (chng.getTp().getCd().equals(cd) && (lvlServ == 1 || chng.getServ().equals(serv)) ) {
			Optional<ChngVal> chngVal;
			
			// если не нашли, искать значение по дочерним записям ред.Lev 29.03.2018 - придумал Диман М.
			chngVal = calc.getReqConfig().getChng().getChngLsk().stream()
					.filter(t -> t.getKart().getLsk().equals(calc.getKart().getLsk())) // фильтр по лиц.счету
					.filter(t -> lvlServ == 0 || 
						t.getParent() == null && t.getServ() !=null && t.getServ().equals(serv) ) // фильтр по услуге по уровню ChngLsk или без этого фильтра (у кого нет родителя)
					.flatMap(t -> t.getChngVal().stream() // преобразовать в другую коллекцию
								.filter(d -> genDt == null || Utl.between(genDt, d.getDtVal1(), d.getDtVal2())) // и фильтр по дате или без даты
							).findFirst();
			if (chngVal.isPresent()) {
				val = chngVal.get().getVal();
				return val;
			} else {
				// если не нашли, искать значение по родительской записи ред.Lev 29.03.2018 - придумал Диман М.
				chngVal = calc.getReqConfig().getChng().getChngLsk().stream()
						.filter(t -> t.getKart().getLsk().equals(calc.getKart().getLsk())) // фильтр по лиц.счету
						.filter(t -> lvlServ == 0 || t.getParent() != null) // фильтр у кого есть родитель
						.flatMap(t -> t.getParent().getChngVal().stream() // преобразовать в другую коллекцию
									.filter(d -> genDt == null || Utl.between(genDt, d.getDtVal1(), d.getDtVal2())) // и фильтр по дате или без даты
								).findFirst();
		
				if (chngVal.isPresent()) {
					val = chngVal.get().getVal();
				}
			}
		}
		return val;
		
	}
}
