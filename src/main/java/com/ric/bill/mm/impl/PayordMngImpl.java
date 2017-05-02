package com.ric.bill.mm.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;

import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ric.bill.Config;
import com.ric.bill.RequestConfig;
import com.ric.bill.Utl;
import com.ric.bill.dao.PaymentDetDAO;
import com.ric.bill.dao.PayordCmpDAO;
import com.ric.bill.dao.PayordDAO;
import com.ric.bill.dao.PayordFlowDAO;
import com.ric.bill.dao.PayordGrpDAO;
import com.ric.bill.dto.PayordCmpDTO;
import com.ric.bill.dto.PayordDTO;
import com.ric.bill.dto.PayordFlowDTO;
import com.ric.bill.dto.PayordGrpDTO;
import com.ric.bill.dto.RepItemDTO;
import com.ric.bill.excp.WrongDate;
import com.ric.bill.mm.LstMng;
import com.ric.bill.mm.OrgMng;
import com.ric.bill.mm.PayordMng;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.bs.PeriodReports;
import com.ric.bill.model.fn.Payord;
import com.ric.bill.model.fn.PayordCmp;
import com.ric.bill.model.fn.PayordFlow;
import com.ric.bill.model.fn.PayordGrp;
import com.ric.bill.model.oralv.Ko;
import com.ric.bill.model.tr.Serv;

@Service
@Slf4j
public class PayordMngImpl implements PayordMng {

    @PersistenceContext
    private EntityManager em;
	@Autowired
	private PayordDAO payordDao;
	@Autowired
	private PayordGrpDAO payordGrpDao;
	@Autowired
	private PayordCmpDAO payordCmpDao;
	@Autowired
	private PayordFlowDAO payordFlowDao;
	@Autowired
	private LstMng lstMng;
	@Autowired
	private OrgMng orgMng;
	@Autowired
	private PaymentDetDAO paymentDetDao;
	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private Config config;
	
	/**
	 * Получить все платежки
	 * @return
	 */
	public List<Payord> getPayordAll() {
		
		return payordDao.getPayordAll();
		
	}

	/**
	 * Получить платежки по Id группы
	 * @return
	 */
	public List<Payord> getPayordByPayordGrpId(Integer payordGrpId) {
		
		return payordDao.getPayordByPayordGrpId(payordGrpId);
		
	}

	/**
	 * Получить все группы платежек
	 * @return
	 */
	public List<PayordGrp> getPayordGrpAll() {
		
		return payordGrpDao.getPayordGrpAll();
		
	}

	// Сохранить платежку из DTO
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setPayordDto(PayordDTO payordDTO) {
		Payord payord = em.find(Payord.class, payordDTO.getId());
    	payord.setName(payordDTO.getName());		
    	payord.setSelDays(payordDTO.getSelDays());
    	payord.setFormula(payordDTO.getFormula());
    	
    	if (payordDTO.getPeriodTpFk() != null) {
        	Lst periodTp = em.find(Lst.class, payordDTO.getPeriodTpFk());
        	payord.setPeriodTp(periodTp);
    	} else {
        	payord.setPeriodTp(null);
    	}
	}

	// Удалить группу платежек из DTO
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delPayordGrpDto(PayordGrpDTO p) {
		PayordGrp pg = em.find(PayordGrp.class, p.getId());
		em.remove(pg);
	}

	// Удалить платежку из DTO
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delPayordDto(PayordDTO p) {
		Payord pg = em.find(Payord.class, p.getId());
		em.remove(pg);
	}

	// Удалить платежку из DTO
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delPayordCmpDto(PayordCmpDTO p) {
		PayordCmp pg = em.find(PayordCmp.class, p.getId());
		em.remove(pg);
	}

    // сохранить группу платежки из DTO
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setPayordGrpDto(PayordGrpDTO p) {
		PayordGrp payordGrp = em.find(PayordGrp.class, p.getId());
		payordGrp.setName(p.getName());
	}

    // добавить группу платежки из DTO
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public PayordGrp addPayordGrpDto(PayordGrpDTO p) {
		PayordGrp payordGrp = new PayordGrp(p.getName());
		em.persist(payordGrp);
		
		return payordGrp;
	}

    // Добавить движение по платежке из DTO
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public PayordFlow addPayordFlowDto(PayordFlowDTO p) {
		
		//PayordGrp grp = em.find(PayordGrp.class, p.getPayordGrpFk());
		//Lst period =  em.find(Lst.class, p.getPeriodTpFk());
		Payord payord = em.find(Payord.class, p.getPayordFk());
		Org uk = em.find(Org.class, p.getUkFk());
		PayordFlow payordFlow = new PayordFlow(payord, uk, p.getSumma(), p.getSumma1(), p.getSumma2(), 
				p.getSumma3(), p.getSumma4(), p.getSumma5(), p.getSumma6(), p.getNpp(), p.getTp(), p.getPeriod(), p.getSigned(), false, p.getDt());
		em.persist(payordFlow);
		
		return payordFlow;
	}

	// обновить группы платежки из базы (чтобы перечитались все поля)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void refreshPayordGrp(PayordGrp p) {
		em.refresh(p);
	}	
	
	// сохранить компонент платежки из DTO
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setPayordCmpDto(PayordCmpDTO p) {
		PayordCmp pCmp = em.find(PayordCmp.class, p.getId());
		
		if (p.getVarFk() != null) {
			Lst var = em.find(Lst.class, p.getVarFk());	
			pCmp.setVar(var);
		} else {
			pCmp.setVar(null);
		}

		if (p.getOrgFk() != null) {
			Org org = em.find(Org.class, p.getOrgFk());	
			pCmp.setOrg(org);
		} else {
			pCmp.setOrg(null);
		}
		
		if (p.getKoFk() != null) {
			Ko ko = em.find(Ko.class, p.getKoFk());	
			pCmp.setKo(ko);
		} else {
			pCmp.setKo(null);
		}

		if (p.getServFk() != null) {
			Serv serv = em.find(Serv.class, p.getServFk());	
			pCmp.setServ(serv);
		} else {
			pCmp.setServ(null);
		}
	
		pCmp.setMark(p.getMark());
		
	}

	/**
	 * Получить компоненты платежки по её ID
	 * @return
	 */
	public List<PayordCmp> getPayordCmpByPayordId(Integer payordId) {

		return payordCmpDao.getPayordCmpByPayordId(payordId);
		
	}

	/**
	 * Получить группу платежек по её ID
	 * @return
	 */
	public PayordGrp getPayordGrpById(Integer id) {

		return payordGrpDao.getPayordGrpById(id);
		
	}

    // Добавить платежку из DTO
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Payord addPayordDto(PayordDTO p) {
		
		PayordGrp grp = em.find(PayordGrp.class, p.getPayordGrpFk());
		Lst period =  em.find(Lst.class, p.getPeriodTpFk()); 
		Payord payord = new Payord(p.getName(), p.getSelDays(), p.getFormula(), grp, period);
		em.persist(payord);
		
		return payord;
	}

    // Добавить формулу платежки из DTO
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public PayordCmp addPayordCmpDto(PayordCmpDTO p) {
		
		// найти компоненты
		Lst var = em.find(Lst.class, p.getVarFk());
		Serv serv = em.find(Serv.class, p.getServFk());
		Org org = em.find(Org.class, p.getOrgFk());
		Payord payord = em.find(Payord.class, p.getPayordFk());
		Ko ko =  em.find(Ko.class, p.getKoFk());
		// создать формулу
		PayordCmp cmp = new PayordCmp(payord, var, serv, org, ko, p.getMark());
		em.persist(cmp);
		
		return cmp;
	}

	// Обновить формулу платежки из базы (чтобы перечитались все поля)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void refreshPayordCmp(PayordCmp t) {
		em.refresh(t);
	}	

	// Обновить платежку из базы (чтобы перечитались все поля)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void refreshPayord(Payord p) {
		em.refresh(p);
	}

	// Обновить платежку из базы (чтобы перечитались все поля)
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void refreshPayordFlow(PayordFlow p) {
		em.refresh(p);
	}

	/**
     * Получить движения по всем платежкам по типу и периоду
     * @param tp - тип движение
     * @param period - период
     * @return
     */
    public List<PayordFlow> getPayordFlowByTpPeriod(Integer tp, String period) {
    	return payordFlowDao.getPayordFlowByTpPeriod(tp, period);
    }	
	
	/**
     * Получить движения по всем платежкам по типу и периоду
     * @param tp - тип движение
     * @param dt - дата
     * @return
     */
    public List<PayordFlow> getPayordFlowByTpDt(Integer tp, Date dt) {
    	return payordFlowDao.getPayordFlowByTpDt(tp, dt);
    }	

    // сохранить движение по платежке из DTO
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setPayordFlowDto(PayordFlowDTO p) {
		PayordFlow payordFlow = em.find(PayordFlow.class, p.getId());
		if (p.getPayordFk() != null) {
			Payord payord = em.find(Payord.class, p.getPayordFk());
			payordFlow.setPayord(payord);
		} else {
			payordFlow.setPayord(null);
		}
		if (p.getUkFk()!= null) {
			Org uk = em.find(Org.class, p.getUkFk());
			payordFlow.setUk(uk);
		} else {
			payordFlow.setUk(null);
		}
		payordFlow.setSumma(p.getSumma());
		payordFlow.setNpp(p.getNpp());
		payordFlow.setDt(p.getDt());
		payordFlow.setSigned(p.getSigned());
		
	}
 
	// Удалить группу платежек из DTO
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void delPayordFlowDto(PayordFlowDTO p) {
		PayordFlow pf = em.find(PayordFlow.class, p.getId());
		em.remove(pf);
	}

	// Сумматор сумм по УК и маркеру
	class AmntSummByUk {
		List<SummByUk> amnt = new ArrayList<SummByUk>(); // сгруппированные суммы по Маркеру и УК
		public void add(SummByUk s) {
			SummByUk summ = amnt.stream().filter(t-> t.getUk().equals(s.getUk()) && t.getMark().equals(s.getMark()))
					.findFirst().orElse(null);
			if (summ != null) {
				summ.setSumma(summ.getSumma().add(s.getSumma()) );
			} else {
				amnt.add(s);
			}
		}
		
		public List<SummByUk> getAmnt() {
			return amnt;
		}

	}
		
	// Одна строчка суммы по УК
	class SummByUk {
		String mark;
		Org uk;
		BigDecimal summa;
		
		public SummByUk(String mark, Org uk, BigDecimal summa) {
			super();
			this.mark = mark;
			this.uk = uk;
			this.summa = summa;
		}

		public String getMark() {
			return mark;
		}

		public void setMark(String mark) {
			this.mark = mark;
		}

		public Org getUk() {
			return uk;
		}

		public void setUk(Org uk) {
			this.uk = uk;
		}

		public BigDecimal getSumma() {
			return summa;
		}

		public void setSumma(BigDecimal summa) {
			this.summa = summa;
		}

	}
	

	// Итоговые суммы по PayordFlow
	class AmntFlow {
		BigDecimal summa =BigDecimal.ZERO;
	}
	
	/**
	 * Подсчет итоговых сумм по PayordFlow
	 * @param p - платежка
	 * @param uk - УК
	 * @param dt1 - дата нач.
	 * @param dt2 - дата кон.
	 * @param tp - Тип записи:  0-вх.сал., 1-вх.сал.Бух, 2-платежка, 3-корр.перечисл., 4-корр.сборов, 5- корр.удерж
	 * @return
	 */
	private AmntFlow calcFlow(Payord p, Org uk, String period, Date dt1, Date dt2, Integer tp) {
		AmntFlow amntFlow = new AmntFlow();
		p.getPayordFlow().stream()
				.filter(t-> (period==null || (t.getPeriod()!= null && t.getPeriod().equals(period)) ) 
						&& (dt1==null || (t.getDt()!=null && Utl.between(t.getDt(), dt1, dt2))) 
						&& t.getUk().equals(uk)
						&& t.getPayord().equals(p) && t.getTp().equals(tp)
						&& t.getSigned())
				.forEach(t-> {
			amntFlow.summa=amntFlow.summa.add(BigDecimal.valueOf(Utl.nvl(t.getSumma(), 0D)));
		});
		return amntFlow;
	}
	
	/**
	 * Подсчет сборов по параметрам
	 * @param markLst - список маркеров
	 * @param amntSummByUk - сгруппированные суммы по УК и Маркерам
	 * @param p - платежка
	 * @param uk - УК
	 * @return
	 */
	private BigDecimal calcMark(List<String> markLst, AmntSummByUk amntSummByUk, Payord p, Org uk) {
		BigDecimal summa = BigDecimal.ZERO; 
			// Подсчет сборов
			String formula = p.getFormula();
			log.info("Формула до изменений={}", formula);
			if (formula!=null) {
				for (String mark: markLst) {
					// по каждому маркеру
					BigDecimal summ = amntSummByUk.getAmnt().stream()
							.filter(e -> e.getUk().equals(uk) && e.getMark().equals(mark)).map(e-> e.getSumma())
							.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
					if (p.getId()==181) {
						log.info("amntSummByUk size()={}", amntSummByUk.getAmnt().size());
						log.info("Сумма={}", summ);
					}
					formula = formula.replaceAll(mark, String.valueOf(summ));
				}
				log.info("Формула после изменений={}", formula);
				Expression e = new Expression(formula);
				summa = BigDecimal.valueOf(e.calculate());
				summa = summa.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
		return summa;
	}
	
	/**
	 * Получить входящее сальдо на период (саму запись)
	 * @param p - платежка
	 * @param period - период выборки
	 * @return
	 */
	public PayordFlow getInsal(Payord p, String period, Integer tp) {
		PayordFlow payordFlow = payordFlowDao.getPayordFlowBeforePeriod(p.getId(), tp, period).stream().findFirst().orElse(null);
		return payordFlow;
	}
	
	/**
	 * Получить входящее сальдо на период (сумму)
	 * @param p - платежка
	 * @param period - период выборки
	 * @return
	 */
	public BigDecimal getInsalSumm(Payord p, String period, Integer tp) {
		PayordFlow payordFlow = payordFlowDao.getPayordFlowBeforePeriod(p.getId(), tp, period).stream().findFirst().orElse(null);
		if (payordFlow != null) {
			return BigDecimal.valueOf(payordFlow.getSumma());
		} else {
			return BigDecimal.ZERO;
		}
	}


	/**
	 * Получить список DTO платежек со вх.сальдо, для отчета 
	 * @param pr - Отчетный период
	 * @return
	 */
	public List<RepItemDTO> getPayordRep(PeriodReports pr) {
		List<RepItemDTO> lst;
		// получить список платежек на текущую дату, с входящим сальдо
		lst = getPayordFlowByTpDt(2, pr.getDt()).stream()
				//.filter(t -> t.getSigned()) // только подписанные??
				.map(t-> new RepItemDTO(t.getId(), t.getPayord().getPayordGrp().getName(),
				t.getPayord().getName(), t.getUk().getName(), 
				getInsalSumm(t.getPayord(), config.getPeriod(), 0), // вх.сальдо 
				BigDecimal.valueOf(t.getSumma()), BigDecimal.valueOf(t.getSumma1()), BigDecimal.valueOf(t.getSumma2()), BigDecimal.valueOf(t.getSumma3()), 
						BigDecimal.valueOf(t.getSumma4()), BigDecimal.valueOf(t.getSumma5()), BigDecimal.valueOf(t.getSumma6())))
				.collect(Collectors.toList());
		return lst;
	}
	
	/**
	 * Сформировать платежки за период
	 * Внимание!!! итоговое формирование, можно делать, если подписана финальная платёжка!
	 * 
	 * Порядок вызова:
	 * В течении месяца genDt - тек.дата, isFinal=false, isEndMonth=false
	 * Итоговая платежка genDt - обычно - дата след.месяца, но можно и текущего (например если конец года), isFinal=true, isEndMonth=false
	 * Итоговое формирование сальдо genDt - без разницы, isFinal=false, isEndMonth=true
	 * 
	 * @param genDt - обычно текущая дата
	 * @param isFinal - финальная платежка
	 * @param isEndMonth - итоговое формирование сальдо
	 * @throws WrongDate 
	 * @throws ParseException 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void genPayord(Date genDt, Boolean isFinal, Boolean isEndMonth) throws WrongDate, ParseException {
/*		DateFormat df = new SimpleDateFormat("dd.MM.yyyy"); // для проверки, пока не удалять
		Date dt;
		dt = df.parse("01.03.2017");
		Date curDt1 = dt;
		dt = df.parse("31.03.2017");
		Date curDt2 = dt;

		String period = "201703";//calc.getReqConfig().getPeriod();
		String periodNext = "201704";//calc.getReqConfig().getPeriodNext();
*/
		RequestConfig reqConfig = ctx.getBean(RequestConfig.class);
		reqConfig.setUp(config, "0", "0", null, -1, "", "");
		
		Date curDt1 = reqConfig.getCurDt1(); 
		Date curDt2 = reqConfig.getCurDt2();

		String period = reqConfig.getPeriod();
		String periodNext = reqConfig.getPeriodNext();
		
		if (isFinal && isEndMonth) {
			throw new WrongDate("Заданы некорректные параметры, при формировании платежки");
		}
		if (isFinal && (genDt.before(curDt2) || genDt.equals(curDt2))) {
			// если финальная платежка и дата формирования меньше последней даты текущего периода 
			throw new WrongDate("Некорректная дата, при итоговом формировании платежки");
		}
		
		// Перебрать все платежки
		for (Payord p :payordDao.getPayordAll()) {
			log.info("Payord.id={}", p.getId());
			AmntSummByUk amntSummByUk = new AmntSummByUk();
 			// distinct список Маркеров
			List<String> markLst = p.getPayordCmp().stream().distinct().map(t -> t.getMark()).collect(Collectors.toList());
			p.getPayordCmp().stream().forEach(t -> {
				//log.info("Payord.id={}", t.getId());
				if (t.getVar().getCd().equals("PAYORD_SUM_PAY_REP2")) {
					// собрать сумму по отчету оплаты, сгруппировать по Маркеру и УК
					paymentDetDao.getPaymentDetByPeriod(period).stream()
							.filter(d -> t.getServ() == null || d.getServ().equals(t.getServ()))
							.filter(d -> t.getOrg() == null || d.getOrg().equals(t.getOrg()))
							.forEach(d -> amntSummByUk.add(new SummByUk(t.getMark(), 
									d.getPayment().getKart().getUk(), BigDecimal.valueOf(d.getSumma()) )));
				} else {
					// прочие варианты сбора данных
				}
			});
			
 			// distinct список УК
			//List<Org> ukLst = amntSummByUk.getAmnt().stream().map(d -> d.getUk()).distinct().collect(Collectors.toList());
			List<Org> ukLst = orgMng.getOrgUkAll();
			ukLst.stream().forEach(uk -> {
				if (p.getId() == 181) {
					log.info("check");
				}
				// По каждой УК, за период:
				// получить сборы по всем маркерам
				BigDecimal summa1 = calcMark(markLst, amntSummByUk, p, uk);
				log.info("По УК.id={} и по Платежке id={}, сумма={}", uk.getId(), p.getId(), summa1);
				// получить сумму перечислений
				AmntFlow amntFlow = calcFlow(p, uk, period, null, null, 2);
				BigDecimal summa2 = amntFlow.summa;
				// получить сумму корректировок сборов
				amntFlow = calcFlow(p, uk, period, null, null, 3);
				BigDecimal summa3 = amntFlow.summa;
				// получить сумму корректировок перечислений
				amntFlow = calcFlow(p, uk, period, null, null, 4);
				BigDecimal summa4 = amntFlow.summa;
				// получить сумму удержаний
				amntFlow = calcFlow(p, uk, period, null, null, 5);
				BigDecimal summa5 = amntFlow.summa;
				// получить вх. сальдо
				PayordFlow salFlow = getInsal(p, period, 0); 
				BigDecimal insal = null;
				if (salFlow != null){
					insal = BigDecimal.valueOf(salFlow.getSumma());
				} else {
					insal = BigDecimal.ZERO;
				}
				log.info("insal={}, summa1={}, summa2={}, summa3={}, summa4={}, summa5={}", insal, summa1, summa2, summa3, summa4, summa5);

				// рассчитать сумму, рекомендованную к перечислению
				BigDecimal summa6 = insal.add(summa1).subtract(summa2).add(summa3).subtract(summa4).subtract(summa5);
				log.info("summa6={}", summa6);


				if (isEndMonth) {
					// Итоговое формирование по концу месяца
					PayordFlow flow;
					// удалить уже сформир.сальдо
					List<PayordFlow> lst = p.getPayordFlow().stream().filter(t-> t.getTp()==0 && 
							t.getPeriod().equals(periodNext)).collect(Collectors.toList());
					for (Iterator<PayordFlow> iterator = lst.iterator(); iterator.hasNext();) {
						p.getPayordFlow().remove(iterator.next());
					}
					// добавить сальдо, если изменилось
					if (!summa6.equals(insal) && summa6.compareTo(BigDecimal.ZERO)!=0) {
						flow = new PayordFlow(p, uk, 
							summa6.doubleValue(), null, 
							null, null, null, 
							null, null, null, 0, periodNext, false, false, null);  
						p.getPayordFlow().add(flow);
					}
					
					// получить вх. сальдо для бухг.
					salFlow = getInsal(p, period, 1); 
					if (salFlow != null) {
						insal = BigDecimal.valueOf(salFlow.getSumma());
					} else {
						insal = BigDecimal.ZERO;
					}

					// рассчитать сумму сальдо по бухгалтерии
					// получить сумму перечислений для сальдо по бухгалтерии (взять по фактическим датам)
					amntFlow = calcFlow(p, uk, null, curDt1, curDt2, 2);
					summa2 = amntFlow.summa;
					summa6 = insal.add(summa1).subtract(summa2).add(summa3).subtract(summa4).subtract(summa5);
					log.info("Сальдо по бух insal={}, summa1={}, summa2={}, summa3={}, summa4={}, summa5={}", insal, summa1, summa2, summa3, summa4, summa5);
					log.info("Сальдо по бух summa6={}", summa6);
					// удалить уже сформир.сальдо по бухг.
					lst = p.getPayordFlow().stream().filter(t-> t.getTp()==1 && 
						t.getPeriod().equals(periodNext)).collect(Collectors.toList());
					for (Iterator<PayordFlow> iterator = lst.iterator(); iterator.hasNext();) {
						p.getPayordFlow().remove(iterator.next());
					}
					// добавить сальдо, если изменилось
					if (!summa6.equals(insal) && summa6.compareTo(BigDecimal.ZERO)!=0 ) {
						flow = new PayordFlow(p, uk, 
								summa6.doubleValue(), null, 
								null, null, null, 
								null, null, null, 1, periodNext, false, false, null);  
						p.getPayordFlow().add(flow);
					}
				} else {
					
					
					
					
					// TODO! Сделать чтобы Итоговая платежка добавлялась только 1 раз!
					
					
					
					// добавить платежку
					if (!isFinal) {
						// округлить, если не итоговая плат. по концу мес.
						summa6=summa6.setScale(0, BigDecimal.ROUND_DOWN);
					}
					// занулить, если отрицательная
					if (summa6.floatValue() < 0) {
						summa6 = BigDecimal.ZERO; 
					}
					
					if (summa1.compareTo(BigDecimal.ZERO)!=0 ||
						summa2.compareTo(BigDecimal.ZERO)!=0 ||
						summa3.compareTo(BigDecimal.ZERO)!=0 ||
						summa4.compareTo(BigDecimal.ZERO)!=0 ||
						summa5.compareTo(BigDecimal.ZERO)!=0 ||
						summa6.compareTo(BigDecimal.ZERO)!=0) {
						// создать движение по платежке, если не нулевое
						PayordFlow flow = new PayordFlow(p, uk, 
									summa6.doubleValue(), summa1.doubleValue(), 
									summa2.doubleValue(), summa3.doubleValue(), summa4.doubleValue(), 
									summa5.doubleValue(), summa6.doubleValue(), null, 2, period, false, isFinal, genDt);  
						p.getPayordFlow().add(flow);
					}
				}
				
			});
			
		}
		
	}
	
}