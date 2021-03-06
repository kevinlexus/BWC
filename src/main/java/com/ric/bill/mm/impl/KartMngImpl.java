package com.ric.bill.mm.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.ric.bill.Calc;
import com.ric.bill.CntPers;
import com.ric.bill.Config;
import com.ric.bill.Standart;
import com.ric.bill.TarifContains;
import com.ric.bill.dao.KartDAO;
import com.ric.bill.dao.PersDAO;
import com.ric.bill.excp.EmptyServ;
import com.ric.bill.excp.EmptyStorable;
import com.ric.bill.mm.KartMng;
import com.ric.bill.mm.ParMng;
import com.ric.bill.mm.TarifMng;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.fn.Chng;
import com.ric.bill.model.fn.PersPrivilege;
import com.ric.bill.model.fn.Privilege;
import com.ric.bill.model.fn.PrivilegeServ;
import com.ric.bill.model.ps.Pers;
import com.ric.bill.model.ps.Reg;
import com.ric.bill.model.ps.Registrable;
import com.ric.bill.model.tr.Serv;
import com.ric.cmn.Utl;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class KartMngImpl implements KartMng {

	@Autowired
	private KartDAO kDao;
	@Autowired
	private PersDAO persDao;
	@Autowired
	private ParMng parMng;
	@Autowired
	private TarifMng tarMng;
    @Autowired
    private Config config;

	// состояние проживающего
	private class PersStatus {
		boolean exist;
		String kinShip;
		//конструктор
		public PersStatus (boolean exist, String kinShip) {
			this.exist = exist;
			this.kinShip = kinShip;
		}
	}

	/**
	 * Проверить, считали ли персону
	 * нельзя кэшировать!
	 */
	private /*synchronized*/ boolean foundPers (List<Pers> counted, Pers p) {
		if (counted.contains(p)) {
			//уже считали персону
			return true;
		} else {
			//добавить персону в массив
			counted.add(p);
			return false;
		}
	}

	/**
	 * Проверить наличие проживающего по постоянной регистрации или по временному присутствию на дату формирования! (на Calc.getGenDt())
	 */
	//@Cacheable(cacheNames="KartMngImpl.checkPersStatus", key="{#rqn, #rc.getLsk(), #p.getId(), #status, #tp,  #genDt}")
	private /*synchronized */boolean checkPersStatus (int rqn, Calc calc, Kart rc, Pers p, String status, int tp, Date genDt) {
		PersStatus ps = checkPersStatusExt(rqn, calc, rc, p, status, tp, genDt);
		return ps.exist;
	}


	/**
	 * Проверить наличие проживающего по постоянной регистрации или по временному присутствию на дату формирования! (на Calc.getGenDt())
	 * и вернуть объект, содержащий наличие проживающего и его отношение к нанимателю
	 */
	//@Cacheable(cacheNames="KartMngImpl.checkPersStatusExt", key="{#rqn, #rc.getLsk(), #p.getId(), #status, #tp,  #genDt}")
	private /*synchronized */PersStatus checkPersStatusExt (int rqn, Calc calc, Kart rc, Pers p, String status, int tp, Date genDt) {
		Date dt1, dt2;
		List<? extends Registrable> rg;
		if (tp==0) {
			rg = rc.getReg();
		} else {
			rg = rc.getRegState();
		}
		for (Registrable r : rg) {
			//проверять только у тех, где pers (fk_pers) заполнено!
			if (r.getPers()!=null && r.getPers().equals(p)) {
				if (r.getTp().getCd().equals(status)) {
					if (r.getDtRegTs() == null ||
					    r.getDtRegTs().before(calc.getReqConfig().getCurDt2())) {
						dt1=Utl.nvl(r.getDtReg(), config.getFirstDt());
					} else {
						dt1=config.getLastDt();
					}

					if (r.getDtUnRegTs() == null ||
						r.getDtUnRegTs().before(calc.getReqConfig().getCurDt2())) {
						dt2=Utl.nvl(r.getDtUnReg(), config.getLastDt());
					} else {
						dt2=config.getLastDt();
					}
					if (Utl.between(genDt, dt1, dt2)) {
						//наличие статуса подтвердилось,  - проживающий присутствует
						if (tp==0) {
							Reg reg = (Reg) r;
							if (reg.getKinship()==null) {
								return new PersStatus(true, null);
							} else {
								return new PersStatus(true, reg.getKinship().getCd());
							}
						} else {
							return new PersStatus(true, null);
						}
					}
				}
			}
		}
		//статус - проживающий отсутствует
		return new PersStatus(false, null);
	}

	/**
	 * Проверить наличие проживающего при fk_pers = null на дату формирования! (на Calc.getGenDt())
	 */
	//@Cacheable(cacheNames="KartMngImpl.checkPersNullStatus", key="{#rqn, #reg.getId(), #genDt}")
	private boolean checkPersNullStatus (int rqn, Calc calc, Registrable reg, Date genDt) {
		//проверить статус, даты
		Date dt1, dt2;
		if (reg.getTp().getCd().equals("Временное присутствие")) {
			if (reg.getDtRegTs() == null ||
					reg.getDtRegTs().before(calc.getReqConfig().getCurDt2())) {
				dt1=Utl.nvl(reg.getDtReg(), config.getFirstDt());
			} else {
				dt1=config.getLastDt();
			}

			if (reg.getDtUnRegTs() == null ||
					reg.getDtUnRegTs().before(calc.getReqConfig().getCurDt2())) {
				dt2=Utl.nvl(reg.getDtUnReg(), config.getLastDt());
			} else {
				dt2=config.getLastDt();
			}
			if (Utl.between(genDt, dt1, dt2)) {
				//наличие статуса подтвердилось
				return true;
			}
		}

		//статус отсутствует
		return false;
	}

	/**
	 * Получить кол-во проживающих  на дату формирования! (на Calc.getGenDt())
	 * @param serv - Рассчитываемая услуга
	 * @param rc - Список дат регистрации
	 * @param cntPers - объект для заполнения
	 * @return
	 * @throws EmptyStorable
	 */
	@Override
	//@Cacheable(cacheNames="KartMngImpl.getCntPers", key="{#rqn, #rc.getLsk(), #serv.getId(), #genDt}")
	public CntPers getCntPers(int rqn, Calc calc, Kart rc, Serv serv, Date genDt) throws EmptyStorable{
		CntPers cntPers = new CntPers();
		List<Pers> counted = new ArrayList<Pers>();
		Chng chng = calc.getReqConfig().getChng();
		cntPers.setUp();
		//поиск сперва по постоянной регистрации
		for (Registrable p : rc.getReg()) {
			if (p.getPers()!=null && !foundPers(counted, p.getPers())) {
				if (checkPersStatus(rqn, calc, rc, p.getPers(), "Постоянная прописка", 0, genDt)) {
					//постоянная регистрация есть, проверить временное отсутствие, если надо по этой услуге
					if (!Utl.nvl(serv.getInclAbsn(), false)) {
						if (!checkPersStatus(rqn, calc, rc, p.getPers(), "Временное отсутствие", 1, genDt)) {
							//временного отсутствия нет, считать проживающего
							cntPers.cnt++;
							cntPers.cntForVol++;
							cntPers.cntFact++;
							cntPers.persLst.add(p.getPers());
							/*if (p.getPers().getId().equals(38693)) {
								log.info("добавлено1 Pers.id={}", p.getPers().getId());
							}*/
						}
					} else {
						//не проверять временное отсутствие, считать проживающего
						cntPers.cnt++;
						cntPers.cntForVol++;
						cntPers.cntFact++;
						cntPers.persLst.add(p.getPers());
						/*if (p.getPers().getId().equals(38693)) {
							log.info("добавлено2 Pers.id={}", p.getPers().getId());
						}*/
					}
					cntPers.cntEmpt++;
				} else {
					//нет постоянной регистрации, поискать временную прописку
					if (checkPersStatus(rqn, calc, rc, p.getPers(), "Временная прописка", 0, genDt)) {
						//временное присутствие есть, считать проживающего
						if (Utl.nvl(serv.getInclPrsn(), false)) {
							cntPers.cnt++;
							cntPers.cntForVol++;
							cntPers.cntFact++;
							cntPers.persLst.add(p.getPers());
							/*if (p.getPers().getId().equals(38693)) {
								log.info("добавлено3 Pers.id={}", p.getPers().getId());
							}*/
						}
						cntPers.cntEmpt++;
					}

				}
			}
		}
		//поиск по временной регистрации
		for (Registrable p : rc.getRegState()) {
			//там где NULL fk_pers,- обычно временно зарег., считать их
			if (p.getPers()==null) {
				if (checkPersNullStatus(rqn, calc, p, genDt)){
					if (Utl.nvl(serv.getInclPrsn(), false)) {
						cntPers.cnt++;
						cntPers.cntForVol++;
						cntPers.cntFact++;
						cntPers.persLst.add(p.getPers());
						/*if (p.getPers().getId().equals(38693)) {
							log.info("добавлено4 Pers.id={}", p.getPers().getId());
						}*/
					}
				}
			}
		}

		// если кол-во проживающих для объема установить невозможно, установить по кол-ву собственников
		// здесь не считать кол-во прожив факт cntFact
		if (cntPers.cntForVol == 0) {
			cntPers.cntOwn = Utl.nvl(parMng.getDbl(rqn, calc.getKart(), "Количество собственников на ЛС", genDt, chng), 0d).intValue();
			cntPers.cntForVol = cntPers.cntOwn;
		}
		return cntPers;
	}

	/**
	 * Получить нормативный объём по лиц.счету
	 * @param mLog - Расчетный узел
	 * @param serv - Рассчитываемая услуга
	 * @param cnt - Переданное кол-во проживающих
	 * @param calcCd - CD Варианта расчета начисления
	 * @param tp - тип норматива, 0 - для определения нормы и свыше, 1 - для определения объема
	 * @throws EmptyServ
	 */
	@Override
	//@Cacheable(cacheNames="KartMngImpl.getStandartVol", key="{#rqn, #calc.getKart().getLsk(), #serv.getId(), #genDt }") // сделал отдельный кэш, иначе валится с Cannot Cast Standart to Boolean!
	public Standart getStandartVol(int rqn, Calc calc, Serv serv, Date genDt, int tp) throws EmptyStorable {
		log.trace("STANDART1="+serv.getId()+" dt="+genDt);
		Chng chng = calc.getReqConfig().getChng();
		//получить услугу основную, для начисления
		Serv servChrg = serv.getServChrg();
		//получить услугу, по которой записывается норматив (в справочнике
		//строго должна быть указана fk_st_keep! по услуге счетчика)
		Serv servSt = serv.getServStKeep();
		Standart st = new Standart();

		Double stVol = 0d;
		CntPers cntPers = getCntPers(rqn, calc, calc.getKart(), servChrg, genDt);

		int cnt;
		if (tp==0) {
			// для определения нормы и свыше
			cnt=cntPers.cnt;
		} else {
			// для определения объема
			cnt=cntPers.cntForVol;
		}

		log.trace("STANDART4="+serv.getId()+" dt="+genDt);
		log.trace("===="+Utl.nvl(parMng.getDbl(rqn, servChrg, "Вариант расчета по общей площади-1"), 0d));

		if (Utl.nvl(parMng.getDbl(rqn, servChrg, "Вариант расчета по общей площади-1"), 0d)==1d
				|| Utl.nvl(parMng.getDbl(rqn, servChrg, "Вариант расчета по объему-2"), 0d)==1d) {
			if (cnt==1) {
				stVol = getServPropByCD(rqn, calc, servSt, "Норматив-1 чел.", genDt);
			} else if (cnt==2) {
				stVol = getServPropByCD(rqn, calc, servSt, "Норматив-2 чел.", genDt);
			} else if (cnt >= 3) {
				stVol = getServPropByCD(rqn, calc, servSt, "Норматив-3 и более чел.", genDt);
			} else {
				stVol = 0d;
			}

		} else if (Utl.nvl(parMng.getDbl(rqn, servChrg, "Вариант расчета по объему-1"),0d)==1d
				&& !servChrg.getCd().equals("Электроснабжение")) {
			// получить норматив, не зависящий от кол-ва прожив (например по Х.В., Г.В.)
			stVol = getServPropByCD(rqn, calc, servSt, "Норматив", genDt);
		} else if (Utl.nvl(parMng.getDbl(rqn, servChrg, "Вариант расчета по объему-1"),0d)==1d
				&& servChrg.getCd().equals("Электроснабжение")) {
			Double kitchElStv = 0d;
			String s2;
			kitchElStv = parMng.getDbl(rqn, calc.getKart(), "Электроплита. основное количество", genDt, chng);
			if (Utl.nvl(kitchElStv, 0d) != 0d) {
				//с эл.плитой
				switch (cnt) {
					case 0 :
						s2 = null;
						break;
					case 1 :
						s2 = "Норматив-1 чел. с эл. плитой";
						break;
					case 2 :
						s2 = "Норматив-2 чел. с эл. плитой";
						break;
					case 3 :
						s2 = "Норматив-3 чел. с эл. плитой";
						break;
					case 4 :
						s2 = "Норматив-4 чел. с эл. плитой";
						break;
					case 5 :
						s2 = "Норматив-5 чел. с эл. плитой";
						break;
					default :
						s2 = "Норматив-6 и более чел. с эл. плитой";
						break;
				}
			} else {
				//без эл.плиты
				switch (cnt) {
				case 0 :
					s2 = null;
					break;
				case 1 :
					s2 = "Норматив-1 чел. без эл. плиты";
					break;
				case 2 :
					s2 = "Норматив-2 чел. без эл. плиты";
					break;
				case 3 :
					s2 = "Норматив-3 чел. без эл. плиты";
					break;
				case 4 :
					s2 = "Норматив-4 чел. без эл. плиты";
					break;
				case 5 :
					s2 = "Норматив-5 чел. без эл. плиты";
					break;
				default :
					s2 = "Норматив-6 и более чел. без эл. плиты";
					break;
			}
			}
			//получить норматив, зависящий от проживающих
			stVol = getServPropByCD(rqn, calc, servSt, s2, genDt);

		}
		if (stVol!=null) {
			st.vol = stVol;
			st.partVol=cnt * stVol/calc.getReqConfig().getCntCurDays();
		} else {
			st.vol = 0d;
			st.partVol= 0d;
		}
		log.trace("STANDART_END="+serv.getId()+" dt="+genDt);
		return st;

	}

	/**
	 * Найти значение свойства типа Double услуги (по лиц.счету!)
	 * @param Kart - Лиц.счет
	 * @param serv -Услуга
	 * @param cd - CD свойства
	 * @param genDt - Дата выборки
	 * @return
	 */
	@Override
	//@Cacheable(cacheNames="KartMngImpl.getServPropByCD", key="{#rqn, #calc.getKart().getLsk(), #serv.getId(), #cd, #genDt }")
	public Double getServPropByCD(int rqn, Calc calc, Serv serv, String cd, Date genDt) {
		//log.info("проверка кэша ----------> rqn={}, serv.id={}, cd={}, genDt={}", rqn, serv.getId(), cd, genDt);
		Double val;
		//в начале ищем по дому
		val=tarMng.getProp(calc, rqn, calc.getHouse(), serv, cd, genDt);
		if (val==null) {
			//потом ищем по лиц. счету
			val=tarMng.getProp(calc, rqn, calc.getKart(), serv, cd, genDt);
		}
		if (val==null) {
			//потом ищем по городу
			val=tarMng.getProp(calc, rqn, calc.getArea(), serv, cd, genDt);
		}
		return val;
	}

	/**
	 * Найти обслуживающую организацию по услуге (по лиц.счету!)
	 * @param Kart - Лиц.счет
	 * @param serv -Услуга
	 * @param genDt - Дата выборки
	 * @return
	 */
	@Override
	//@Cacheable(cacheNames="KartMngImpl.getOrg", key="{#rqn, #calc.getKart().getLsk(), #serv.getId(), #genDt }")
	public /*synchronized*/ Org getOrg(int rqn, Calc calc, Serv serv, Date genDt) {
		Org org;

		//в начале ищем по дому
		org=tarMng.getOrg(calc, rqn, calc.getHouse(), serv, genDt);
		if (org==null) {
			//потом ищем по лиц. счету
			org=tarMng.getOrg(calc, rqn, calc.getKart(), serv, genDt);
		}
		if (org==null) {
			//потом ищем по городу
/*			if (serv.getId()==480 && calc.getArea().getKlskId() == 228) {
				log.info("Erfind_area1: rqn={}, lsk={}, klskId={}, serv.id={}, genDt={}, org.id={}", rqn, calc.getKart().getLsk(), calc.getArea().getKlskId(), serv.getId(), genDt,
						org!=null ? org.getId():null);
			}*/
			org=tarMng.getOrg(calc, rqn, calc.getArea(), serv, genDt);
/*			if (serv.getId()==480 && calc.getArea().getKlskId() == 228) {
				log.info("Erfind_area2: rqn={}, lsk={}, klskId={}, serv.id={}, genDt={}, org.id={}", rqn, calc.getKart().getLsk(), calc.getArea().getKlskId(), serv.getId(), genDt,
						org!=null ? org.getId():null);
			}*/
		}
		return org;
	}

	/**
	 * Найти наличие услуги (по лиц.счету!)
	 * @param Kart - Лиц.счет
	 * @param serv -Услуга
	 * @param genDt - Дата выборки
	 * @return
	 */
	@Override
	//@Cacheable(cacheNames="KartMngImpl.getServ", key="{#rqn, #calc.getKart().getLsk(), #serv.getId(), #genDt }")
	public /*synchronized*/ boolean getServ(int rqn, Calc calc, Serv serv, Date genDt) {
		boolean exs = false;
		//искать свойство "Поставщик"
		Double n1 = getServPropByCD(rqn, calc, serv, "Поставщик", genDt);
		if (n1 != null) {
			exs = true;
		}
		return exs;
	}

	/**
	 * Создать список услуг, по параметру
	 * @param tc -тарифосодержащий объект
	 * @param lst -список услуг
	 * @param cd - параметр, который проверять
	 * @param cmd - добавлять ли услугу в список(0) или удалять(1)?
	 * @return - обновленный список услуг
	 */
	@Override
    public List<Serv> checkServ(Calc calc, TarifContains tc, List lst, String cd, int cmd) {
    	tc.getTarifklsk().stream().forEach(t -> {
    		t.getTarprop().stream()
    			.filter(d -> d.getServ().getServChrg() != null && d.getServ().getServChrg().equals(d.getServ()))
    			.filter(d -> Utl.between2(calc.getReqConfig().getCurDt1(), calc.getReqConfig().getCurDt2(), d.getDt1(), d.getDt2()))
    			.filter(d -> d.getProp().getCd().equals(cd))
    			.forEach(d -> {
					//найдено свойство cd в этом периоде, добавить или удалить из списка услуг
					switch (cmd) {
						case 0 :
							//добавить элемент, если еще не добавлен
							if (!lst.contains(d.getServ())) {
								lst.add(d.getServ());
							}
						break;
						case 1 :
							//удалить элемент, если существует
							if (lst.contains(d.getServ())) {
								lst.remove(d.getServ());
							}

						break;
					}
					});
    	});

    	/*for (TarifKlsk k : tc.getTarifklsk()) {
				//затем по строкам - составляющим тариф
				for (TarifServProp t : k.getTarprop()) {
					if (t.getServ().getServChrg() != null && t.getServ().getServChrg().equals(t.getServ())) {
						//искать наличие свойства, например, "Поставщик", оно и определяет наличие услуги
						if (Utl.between2(calc.getReqConfig().getCurDt1(), calc.getReqConfig().getCurDt2(), t.getDt1(), t.getDt2())) {
							if (t.getProp().getCd().equals(cd)) {
									//найдено свойство cd в этом периоде, добавить или удалить из списка услуг
									switch (cmd) {
										case 0 :
											//добавить элемент, если еще не добавлен
											if (!lst.contains(t.getServ())) {
												lst.add(t.getServ());
											}
										break;
										case 1 :
											//удалить элемент, если существует
											if (lst.contains(t.getServ())) {
												lst.remove(t.getServ());
											}

										break;
								}
							}
						}
					}
				}
			//}
		}*/
		return lst;
    }

	/**
	 * Получить distinct список всех услуг потенциально начисляемых в лиц.счете
	 * @param tc - объект
	 * @return
	 * @throws EmptyServ
	 */
	@Override
	@Cacheable(cacheNames="KartMngImpl.getServAll", key="{#rqn, #calc.getHouse().getId(), #calc.getKart().getLsk() }")
	public List<Serv> getServAll(int rqn, Calc calc) {
		List<Serv> lst = new ArrayList<Serv>();
		// искать и добавить по наборам тарифа
		// города:
		lst = checkServ(calc, calc.getArea(), lst, "Поставщик", 0);
		// дома:
		lst = checkServ(calc, calc.getHouse(), lst, "Поставщик", 0);
		// лиц.счета
		lst = checkServ(calc, calc.getKart(), lst, "Поставщик", 0);

		// УБРАТЬ позже, эта проверка работает в ChrgThr TODO
		// удалить отключенные услуги по наборам тарифа
		// города:
		/*lst = checkServ(calc, calc.getArea(), lst, "Отключение", 1);
		// дома:
		lst = checkServ(calc, calc.getHouse(), lst, "Отключение", 1);
		// лиц.счета
		lst = checkServ(calc, calc.getKart(), lst, "Отключение", 1);
		*/
		return lst;
	}

	/**
	 * Узнать наличие льготы по капремонту (>=70 лет, собственник, другие проживающие тоже >=70 лет)
	 * @param kart
	 * @param genDt
	 */
	@Override
	//@Cacheable(cacheNames="KartMngImpl.getCapPrivs", key="{#rqn, #calc.getKart().getLsk(), #genDt }")
	public double getCapPrivs(int rqn, Calc calc, Kart kart, Date genDt) {
		boolean above70owner=false;
		boolean above70=false;
		boolean under70=false;
		List<Pers> counted = new ArrayList<Pers>();
		for (Registrable p : kart.getReg()) {
			Pers pers = p.getPers();
			//если дата рождения - пустая - считать сегодняшней датой
			Date dtBrn = Utl.nvl(pers.getDtBorn(), new Date());
			if (pers!=null && !foundPers(counted, pers)) {
				PersStatus ps = checkPersStatusExt(rqn, calc, kart, pers, "Постоянная прописка", 0, genDt);
				if (!ps.exist) {
					// если нет Постоянной прописки, поискать по временной прописке, ред.LEV 01.06.2018 (просьба Кацуро И.В.)
					ps = checkPersStatusExt(rqn, calc, kart, pers, "Временная прописка", 0, genDt);
				}

				if (ps.exist) {
					//постоянная регистрация есть
					if (Utl.getDiffYears(dtBrn, genDt) >= 70) {
						//если 70 летний
						if (ps.kinShip != null && (ps.kinShip.equals("Собственник") || ps.kinShip.equals("Наниматель"))) {
							//да еще и Наниматель (Собственник)
							above70owner = true;
						} else {
							//но не Наниматель (Собственник)
							above70 = true;
						}
					} else {
						//если постоянно прож., но не 70 летний
						under70 = true;
					}
				} else {
					//нет постоянной регистрации, поискать временную прописку
					ps = checkPersStatusExt(rqn, calc, kart, pers, "Временная прописка", 0, genDt);
					if (ps.exist) {
						//временное присутствие есть
						if (Utl.getDiffYears(dtBrn, genDt) < 70) {
							//временно зарег <70 лет
							under70 = true;
						}
					}
				}
			}
		}

		if (above70owner && !above70 && !under70) {
			return 0d; //льгота
		} else if (above70owner && above70 && !under70) {
			return 0d; //льгота
		} else {
			return 1d; //нет льготы
		}

	}


	// все Id лицевых по определённому критерию
/*	@Override
	public List<ResultSet> findAllLsk(Integer houseId, Integer areaId, Integer tempLskId, Date dt1, Date dt2) {
		return kDao.findAllLsk(houseId, areaId, tempLskId, dt1, dt2);
	}
*/
	/**
	 * получить Льготу по проживающему, по услуге
	 * @param pers - проживающий
	 * @param serv - услуга
	 * @param genDt - дата формирования
	 * @return
	 */
	@Override
	public PersPrivilege getPersPrivilege(Pers pers, Serv serv, Date genDt) {
		if (pers == null) {
			// по пустому проживающему - нет льготы
			return null;
		} else {
			// вернуть льготу
			return persDao.getPersPrivilege(pers.getId(), serv.getId(), genDt).stream().findFirst().orElse(null);
		}
	}

	/**
	 * получить Отношение привилегии к услуге
	 * @param priv - льгота
	 * @param serv - услуга
	 * @return
	 */
	@Override
	public PrivilegeServ getPrivilegeServ(Privilege priv, Serv serv) {
		return persDao.getPrivilegeServ(priv.getId(), serv.getId());
	}


}