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

	//получить свойство тарифа по его CD
	//@Cacheable(cacheNames="readOnlyCache", key="{ #cd }") - здесь не кэшируется, только в DAO
	//public synchronized Prop getPropByCD(String cd) {
//		return tDao.getPropByCD(cd);
	//}

	/**
	 * Получить значение типа Double тарифа по CD 
	 * @param tc - объект
	 * @param cd - код свойства
	 * @return - свойство
	 */
	@Cacheable(cacheNames="rrr1", key="{#rqn, #tc.getKo().getId(), #serv.getId(), #cd, #genDt }") 
	public /*synchronized*/ Double getProp(Calc calc, int rqn, TarifContains tc, Serv serv, String cd, Date genDt) {
		// TODO - переписать на Java 8!
		//Prop prop = getPropByCD(cd);//так и не понял, как быстрее, искать тариф предварительно getPropByCD, или непосредственно через.getCd()
		//искать сперва по наборам тарифа объекта
		//if (serv.getId()==106) {
		//	log.info("getProp tc.id={}", tc.getKlskId());
		//}
			Boolean isChng = false;
			Chng chng = null; 
			// необходимо ли искать привязку тарифа к перерасчету
			if (calc.getReqConfig().getChng() != null && calc.getReqConfig().getChng().getTp().getCd().equals("Начисление за прошлый период")) {
				isChng = true;
				chng = calc.getReqConfig().getChng(); 
			}
				
			//log.info("TC.id={}", tc.getKlskId());

			for (TarifKlsk k : tc.getTarifklsk()) {
				//if (k.getChng() != null) {
				//	log.info("TarKlsk.id={}", k.getId());
				//}
				// Искать строки тарифа предназначенного для перерасчета (если задано) или не предназначенного
				if (isChng && k.getChng()!=null && k.getChng().equals(chng) || !isChng && k.getChng()==null) {
					//затем по строкам - составляющим тариф 
					for (TarifServProp t : k.getTarprop()) {
						if (Utl.between(genDt, t.getDt1(), t.getDt2())) {
							//if (t.getServ().getId()==106) {
							//	log.info("TarifServProp id={}, serv.id={}, prop.cd={}, n1={}", t.getId(), t.getServ().getId(), t.getProp().getCd(), t.getN1());
							//}
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
	//@Cacheable(cacheNames="rrr1") 
	//@Cacheable(cacheNames="rrr3", key="{#rqn,  #tc.getKlskId(), #serv.getId(), #genDt }") 
	@Cacheable(cacheNames="TarifMngImpl.getOrg", key="{#rqn,  #tc.getKo().getId(), #serv.getId(), #genDt }") 
	public /*synchronized*/ Org getOrg(Calc calc, int rqn, TarifContains tc, Serv serv, Date genDt) {
		/*if (serv.getId()==480) {
			log.info("Erfind set value: rqn={}, lsk={}, klskId={}, serv.id={}, genDt={}", rqn, calc.getKart().getLsk(), tc.getKlskId(), serv.getId(), genDt) ;
		}*/
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
		/*if (serv.getId()==480) {
			log.info("Erfind_area4: rqn={}, klskId={}, serv.id={}, genDt={}", rqn, tc.getKlskId(), serv.getId(), genDt) ;
		}*/
		return null;
		
	}

	/**
	 * Получить список всех услуг по тарифу данного объекта по всем датам
	 * @param tc - объект
	 * @return
	 */
	//@Cacheable(cacheNames="rrr1") 
	/*	@Cacheable(cacheNames="rrr1", key="{ #tc.getKlsk() }") 
	public synchronized List<Serv> getAllServ(TarifContains tc) {
		List<Serv> lst = new ArrayList<Serv>();
		//искать сперва по наборам тарифа объекта 
		for (TarifKlsk k : tc.getTarifklsk()) {
			//по соотв.периоду - сделал по всем датам, так как не понятно, как их фильтровать
			//if (Utl.between(dt1, k.getDt1(), k.getDt2())) {
				//затем по строкам - составляющим тариф 
				for (TarifServ t : k.getTarserv()) {
					lst.add(t.getServ());
				}
			//}
		}
		return lst;
	}
*/	
	/**
	 * Получить наличие услуги по тарифу данного объекта на заданную дату
	 * @param tc - объект
	 * @param genDt - дата выборки
	 * @param genDt - дата выборки
	 * @return - вернуть 0 - услуга не найдена, поискать на другом уровне
	 *                   1 - услуга помечена как удалена с уровня город (не искать её на уроне город)
	 *                   2 - услуга найдена, или помечена как Act=1 (добавлена на уровень лиц.счета) 
	 */
/*	@Cacheable(cacheNames="rrr1", key="{ #tc.getKlsk(), #serv.getId(), #genDt }") 
	public synchronized int getServ(TarifContains tc, Serv serv, Date genDt) {
		List<Serv> lst = new ArrayList<Serv>();
		//искать сперва по наборам тарифа объекта 
		for (TarifKlsk k : tc.getTarifklsk()) {
			//по соотв.периоду
			if (Utl.between(genDt, k.getDt1(), k.getDt2())) {
				//затем по строкам - составляющим тариф 
				for (TarifServ t : k.getTarserv()) {
					if (t.getServ().equals(serv)) {
						if (Utl.nvl(k.getAct(), 1) == 1) {
							return 2;
						} else {
							return 1;
						}
					}
				}
			}
		}
		return 0;
	}
*/	
	
	
	// получить подмененную расценку по перерасчету
	//public Double getChngPrice(Calc calc, Serv serv, Date genDt) {  // убрать synchronized??
		// получить расценку
		//return price = getChngVal(calc, serv, genDt, "Изменение расценки (тарифа)");
	//}

	
	/**
	 *  получить значение перерасчета
	 * @param genDt - при наличии - фильтр по дате
	 * @param serv - услуга
	 * @param cd - тип перерасчета
	 * @param lvlServ - уровень, как фильтровать услугу, 0 - по Chng, 1 - по ChngLsk  
	 * @return
	 */
	public Double getChngVal(Calc calc, Serv serv, Date genDt, String cd, int lvlServ) {
		log.trace("Serv={}", serv.getId());
		Chng chng = calc.getReqConfig().getChng();	
		Double val = null;
		if (chng.getTp().getCd().equals(cd) && (lvlServ == 1 || chng.getServ().equals(serv)) ) {
			Optional<ChngVal> chngVal;
			// по дате
			chngVal = calc.getReqConfig().getChng().getChngLsk().stream()
					.filter(t -> t.getKart().getLsk().equals(calc.getKart().getLsk())) // фильтр по лиц.счету
					.filter(t -> lvlServ == 0 || t.getServ().equals(serv) ) // фильтр по услуге по уровню ChngLsk или без этого фильтра
					.flatMap(t -> t.getChngVal().stream() // преобразовать в другую коллекцию
								.filter(d -> genDt == null || Utl.between(genDt, d.getDtVal1(), d.getDtVal2())) // и фильтр по дате или без даты
							).findFirst();
	
			if (chngVal.isPresent()) {
				val = chngVal.get().getVal();
			}
		}
		return val;
		
	}
}
