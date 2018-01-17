package com.ric.bill.mm.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;

import org.mariuszgromada.math.mxparser.Expression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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
import com.ric.bill.excp.EmptyStorable;
import com.ric.bill.excp.WrongDate;
import com.ric.bill.excp.WrongExpression;
import com.ric.bill.mm.HouseMng;
import com.ric.bill.mm.LstMng;
import com.ric.bill.mm.ObjMng;
import com.ric.bill.mm.OrgMng;
import com.ric.bill.mm.ParMng;
import com.ric.bill.mm.PayordMng;
import com.ric.bill.mm.ReportMng;
import com.ric.bill.mm.SecMng;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.bs.Obj;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.bs.PeriodReports;
import com.ric.bill.model.cash.PaymentDet;
import com.ric.bill.model.fn.Payord;
import com.ric.bill.model.fn.PayordCmp;
import com.ric.bill.model.fn.PayordFlow;
import com.ric.bill.model.fn.PayordGrp;
import com.ric.bill.model.oralv.Ko;
import com.ric.bill.model.tr.Serv;
import com.ric.bill.Config;

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
	private OrgMng orgMng;
	@Autowired
	private PaymentDetDAO paymentDetDao;
	@Autowired
	private ReportMng reportMng;
	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private Config config;
	@Autowired
	private ObjMng objMng;
	@Autowired
	private ParMng parMng;
	@Autowired
	private SecMng sec;
	@Autowired
	private HouseMng houseMng;
	
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
		log.info("user={}", sec.getCurUser().getCd());
		PayordGrp payordGrp = new PayordGrp(p.getName(), sec.getCurUser().getCd());
		em.persist(payordGrp);
		
		return payordGrp;
	}

    // Добавить движение по платежке из DTO
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public PayordFlow addPayordFlowDto(PayordFlowDTO p) throws EmptyStorable {
    	Obj obj = objMng.getByCD(-1, "Модуль платежек");
		Date dt1 = parMng.getDate(-1, obj, "Начало расчетного периода");
		Date dt2 = parMng.getDate(-1, obj, "Конец расчетного периода");
		RequestConfig reqConfig = ctx.getBean(RequestConfig.class);
		reqConfig.setUp(/*config, */"0", "0", null, -1, dt1, dt2);

		//PayordGrp grp = em.find(PayordGrp.class, p.getPayordGrpFk());
		//Lst period =  em.find(Lst.class, p.getPeriodTpFk());
		Payord payord = em.find(Payord.class, p.getPayordFk());
		Org uk = em.find(Org.class, p.getUkFk());
		PayordFlow payordFlow = new PayordFlow(payord, uk, p.getSumma(), p.getSumma1(), p.getSumma2(), 
				p.getSumma3(), p.getSumma4(), p.getSumma5(), p.getSumma6(), p.getNpp(), p.getTp(), 
				reqConfig.getPeriod(), p.getSigned(), false, p.getDt(), p.getComm(), 1);
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

		if (p.getKoExtFk() != null) {
			Ko ko = em.find(Ko.class, p.getKoExtFk());	
			pCmp.setKoExt(ko);
		} else {
			pCmp.setKoExt(null);
		}

		if (p.getServFk() != null) {
			Serv serv = em.find(Serv.class, p.getServFk());	
			pCmp.setServ(serv);
		} else {
			pCmp.setServ(null);
		}
	
		if (p.getPeriodTpFk() != null) {
			Lst periodTp = em.find(Lst.class, p.getPeriodTpFk());	
			pCmp.setPeriodTp(periodTp);
		} else {
			pCmp.setPeriodTp(null);
		}

		pCmp.setSelDays(p.getSelDays());
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
		Payord payord = new Payord(p.getName(), p.getSelDays(), p.getFormula(), grp, period, sec.getCurUser().getCd());
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
		Ko koExt =  em.find(Ko.class, p.getKoExtFk());
		Lst periodTp = (p.getPeriodTpFk()!=null ? em.find(Lst.class, p.getPeriodTpFk()) : null);
		// создать формулу
		PayordCmp cmp = new PayordCmp(payord, var, serv, org, ko, koExt, p.getMark(), 
				sec.getCurUser().getCd(), p.getSelDays(), periodTp);
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
     * @param uk - УК
     * @param tp - тип движение
     * @param period - период
     * @return
     */
    public List<PayordFlow> getPayordFlowByTpPeriod(Integer tp, Org uk, String period) {
    	return payordFlowDao.getPayordFlowByTpPeriod(tp, uk, period);
    }	
	
	/**
     * Получить движения по всем платежкам по типу и периоду и УК
     * @param tp - тип движение
     * @param dt1 - дата начала
     * @param dt2 - дата окончания
     * @param uk - УК
     * @return
     */
    public List<PayordFlow> getPayordFlowByTpDt(Integer tp, Date dt1, Date dt2, Integer uk) {
    	return payordFlowDao.getPayordFlowByTpDt(tp, dt1, dt2, uk);
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
		payordFlow.setSumma((double)Math.round(p.getSumma() * 100d) / 100d ); // Округлить до 2 знаков
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
		List<SummByUk> amnt = new ArrayList<SummByUk>(20); // сгруппированные суммы по Маркеру и УК
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
	 * @param tp - Тип записи:  2-платежка, 3-корр.перечисл., 4-корр.сборов, 5- корр.удерж, (0-вх.сал., 1-вх.сал.Бух, - не использовать!)
	 * @return
	 */
	@Cacheable(cacheNames="PayordMngImpl.calcFlow", key="{ #p.getId(), #uk.getId(), #period, #dt1, #dt2, #tp }")
	private AmntFlow calcFlow(Payord p, Org uk, String period, Date dt1, Date dt2, Integer tp) {
		// кроме 0 и 1
		assert(!tp.equals(0) && !tp.equals(1));
		AmntFlow amntFlow = new AmntFlow();
		p.getPayordFlow().stream()
				.filter(t-> (period==null || (t.getPeriod()!= null && t.getPeriod().equals(period)) ) 
						&& (dt1==null || (t.getDt()!=null && Utl.between(t.getDt(), dt1, dt2))) 
						&& t.getUk().equals(uk)
						&& t.getPayord().equals(p) && t.getTp().equals(tp)
						&& t.getSigned() // только подписанные
						&& t.getStatus().equals(1) // взять только платежки к перечислению (не для информации)
						)
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
			//log.info("Формула до изменений={}", formula);
			if (formula!=null) {
				for (String mark: markLst) {
					// по каждому маркеру
					BigDecimal summ = amntSummByUk.getAmnt().stream()
							.filter(e -> e.getUk().equals(uk) && e.getMark().equals(mark)).map(e-> e.getSumma())
							.reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
					formula = formula.replaceAll(mark, String.valueOf(summ));
				}
				//log.info("Формула после изменений={}", formula);
				Expression e = new Expression(formula);
				summa = BigDecimal.valueOf(e.calculate());
				summa = summa.setScale(2, BigDecimal.ROUND_HALF_UP);
			}
		return summa;
	}
	
	/**
	 * Получить входящее сальдо на период (саму запись)
	 * @param p - платежка
	 * @param uk 
	 * @param period - период выборки
	 * @return
	 */
    @Cacheable(cacheNames="PayordMngImpl.getInsal", key="{ #p.getId(), #uk.getId(), #period, #tp }")
	public PayordFlow getInsal(Payord p, Org uk, String period, Integer tp) {
		PayordFlow payordFlow = payordFlowDao.getPayordFlowBeforePeriod(p.getId(), uk, tp, period, null).stream().findFirst().orElse(null);
		return payordFlow;
	}
	
	/**
	 * Получить входящее сальдо на период (сумму)
	 * @param p - платежка
	 * @param period - период выборки
	 * @return
	 */
	public BigDecimal getInsalSumm(Payord p, Org uk, String period, Integer tp) {
		PayordFlow payordFlow = payordFlowDao.getPayordFlowBeforePeriod(p.getId(), uk, tp, period, null).stream().findFirst().orElse(null);
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
		lst = getPayordFlowByTpDt(2, pr.getDt(), pr.getDt(), null).stream()
				//.filter(t -> t.getSigned()) // только подписанные??
				.map(t-> new RepItemDTO(t.getId(), t.getPayord().getPayordGrp().getName(),
				t.getPayord().getName(), t.getUk().getName(), 
				getInsalSumm(t.getPayord(), t.getUk(), config.getPeriod(), 0), // вх.сальдо 
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
	 * ВНИМАНИЕ! Итоговое формирование - Строго после формирования и подписания итоговой платежки!
	 * @param genDt - обычно текущая дата
	 * @param isFinal - финальная платежка
	 * @param isEndMonth - итоговое формирование сальдо
	 * @param payordId - ID платежки, для выборочного формирования
	 * @param payordCmpId - ID компонента платежки, для выборочного формирования 
	 * @throws WrongDate 
	 * @throws ParseException 
	 * @throws EmptyStorable 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	@CacheEvict(value = {"PayordMngImpl.calcFlow", "PayordMngImpl.getInsal"})
	public void genPayord(Date genDt, Boolean isFinal, Boolean isEndMonth, Integer payordId, Integer payordCmpId
			) throws WrongDate, ParseException, EmptyStorable, WrongExpression {
		long beginTime = System.currentTimeMillis();

		// приложение - новая разработка
    	Obj obj = objMng.getByCD(-1, "Модуль платежек");
		Date dt1 = parMng.getDate(-1, obj, "Начало расчетного периода");
		Date dt2 = parMng.getDate(-1, obj, "Конец расчетного периода");
		RequestConfig reqConfig = ctx.getBean(RequestConfig.class);
		reqConfig.setUp("0", "0", null, -1, dt1, dt2);
		
		// Даты текущего периода
		Date curDt1 = reqConfig.getCurDt1(); 
		Date curDt2 = reqConfig.getCurDt2();
		log.info("Дата начала тек.периода:{} окончания:{}", curDt1, curDt2);

		// Получить дату, по которой отсечь платежи
		Date trimDt;
		if (isFinal || isEndMonth) {
			// Финальную - по последней дате месяца
			trimDt = curDt2;
		} else {
			// Текущую - по числу -1
			trimDt = Utl.addDays(genDt, -1);
		}
		
		String period = reqConfig.getPeriod();
		String periodNext = reqConfig.getPeriodNext();
		
		if (isFinal && isEndMonth) {
			throw new WrongDate("Заданы некорректные параметры, при формировании платежки");
		}
		if (isFinal && (genDt.before(curDt2) || genDt.equals(curDt2))) {
			// если финальная платежка и дата формирования меньше или равна последней дате текущего периода 
			throw new WrongDate("Некорректная дата, при итоговом формировании платежки");
		}
		if (!isEndMonth && (genDt.before(curDt1))) {
			// если текущее формирование и дата меньше первой даты периода
			throw new WrongDate("Некорректная дата, при текущем или финальном формировании платежки");
		}

		// почистить движение по платежкам
		if (isFinal || !isEndMonth){
			//  по текущей или по итоговой
			payordFlowDao.delPayordFlow(genDt);
		}
		
		// суммы оплаты 
		List<PaymentDet> lstDetPrep = paymentDetDao.getPaymentDetByPeriod(period, curDt1, curDt2, trimDt).stream().collect(Collectors.toList());
		// distinct список УК
		List<Org> ukLst = orgMng.getOrgAll(1);
		
		// Перебрать все платежки
		for (Payord p : payordDao.getPayordAll()) {//.stream().filter(tt->tt.getId()==317).collect(Collectors.toList())) {
			// запретить формировать по умолчанию
			Boolean isGen = false;
			
			if (p.getStatus() != 1) {
				// не формировать с неактивным статусом
				isGen = false;
			} else if (isFinal || isEndMonth) {
				// всегда формировать итоговую платежку или итоговое формирование сальдо
				isGen = true;
			} else {
				// Текущие платежки
				// Проверить необходимость формирования платежки, по дню недели и т.п.
				if (p.getPeriodTp() == null) {
					// Если не заполнена периодичность платежки, значит периодичность определена на уровне формул
					isGen = true;
				} else {
					isGen = isGenByDt(genDt, p.getSelDays(), p.getPeriodTp().getCd());
				}
			}
			
				log.info("Обрабатывается платежка id={} name = {}", p.getId(), p.getName());
				
/*				Double ssum2 = lstDetPrep.stream().filter(d->d.getSumma() != null && d.getPayment().getKart().getUk().getId().equals(384)
						 														  && d.getOrg().getId().equals(111)
						).mapToDouble(d -> d.getSumma()).sum();
				Double pen2 = lstDetPrep.stream().filter(d->d.getPen() != null && d.getPayment().getKart().getUk().getId().equals(384)
						  													   && d.getOrg().getId().equals(111)
						  ).mapToDouble(d -> d.getPen()).sum();
				log.info("ssum2={}, pen2={}", ssum2, pen2);
				*/
				// если разрешено формировать
				AmntSummByUk amntSummByUk = new AmntSummByUk();
				// distinct список Маркеров платежки
				List<String> markLst = p.getPayordCmp().stream().distinct().map(t -> t.getMark())
						.collect(Collectors.toList());

				//p.getPayordCmp().stream().filter(t-> isGenByDt(genDt, t.getSelDays(), t.getPeriodTp().getCd()) == true )
				//		.forEach(t -> {
				for (PayordCmp t: p.getPayordCmp()) {
					    if (t.getPayord().getId() == 317) {
						    log.info("Обрабатывается маркер={}", t.getMark());
					    }
					    log.info("Обрабатывается маркер={}", t.getMark());
						// по Каждой формуле-маркеру, если пусто в периодичности или задан период совпадающий с тек.днём
						// или итоговая платежка	
						if (t.getPeriodTp() == null || isGenByDt(genDt, t.getSelDays(), t.getPeriodTp().getCd()) || (isFinal || isEndMonth)) {
							// исключать ли средства собранные УК за свой фонд
							Boolean isExcl = false;
							if (t.getVar().getCd().equals("PAYORD_SUM_PAY_EX") || t.getVar().getCd().equals("PAYORD_SUM_PEN_EX")) {
								isExcl = true;
							    log.info("Будут исключены средства собранные УК за свой фонд");
							}
							final Boolean excl = isExcl;

							// исключать ли средства собранные УК за свой фонд и безнал
							Boolean isExclNoCash = false;
							if (t.getVar().getCd().equals("PAYORD_SUM_PAY2") || t.getVar().getCd().equals("PAYORD_SUM_PEN2")) {
								isExclNoCash = true;
							    log.info("Будут исключены средства поступившие в УК безналично");
							}
							final Boolean exclNoCash = isExclNoCash;
							
							// тип объекта в формуле платежки
							final String addrTp = t.getKo().getAddrTp().getCd();
							// тип доп.объекта в формуле платежки
							String extTp = "xxx";
							if (t.getKoExt() != null) {
								extTp = t.getKoExt().getAddrTp().getCd();
							}
							final String addrExtTp = extTp;
							
							List<PaymentDet> lstDet = lstDetPrep.stream()
									.filter(d -> 
										!addrTp.equals("РКЦ") || d.getPayment().getKart().getUk() 
										.getParent().getKo().equals(t.getKo())) // фильтр по РКЦ
									.filter(d -> 
										(!addrTp.equals("Организация") && !addrTp.equals("ЖЭО")) || d.getPayment()
										.getKart().getUk().getKo().equals(t.getKo())) // фильтр по УК
									.filter(d -> 
										!addrExtTp.equals("Дом") || d.getPayment().getKart().getKw().getHouse()
										.getKo().equals(t.getKoExt())) // фильтр по доп. объекту - Дому
									.filter(d -> !excl || !d.getPayment().getWp().getOrg().equals(
											d.getPayment().getKart().getUk())) // если указано исключить, то, кроме орг, которая собрала деньги по своему фонду
									.filter(d -> t.getServ() == null || d.getServ().equals(t.getServ()))
									.filter(d -> t.getOrg() == null || d.getOrg()!=null && d.getOrg().equals(t.getOrg()))
									.filter(d -> !exclNoCash ||  
											d.getPayment().getTp().getTpPayord().equals(1)) // включать или нет безнал? перенесли во вьюху тип платежа!
									.collect(Collectors.toList());
							
							
									//Double ssum2 = lstDet.stream().filter(d->d.getSumma() != null).mapToDouble(d -> d.getSumma()).sum();
									//Double pen2 = lstDetPrep.stream().filter(d->d.getPen() != null).mapToDouble(d -> d.getPen()).sum();
									//log.info("ssum2={}, pen2={}", ssum2, pen2);

							// собрать сумму по отчету оплаты, сгруппировать по Маркеру и УК
							lstDet.forEach(d -> {
										if (t.getVar().getCd().equals("PAYORD_SUM_PAY_EX") || t.getVar().getCd().equals("PAYORD_SUM_PAY") ||
											t.getVar().getCd().equals("PAYORD_SUM_PAY2")) {
											// Оплата
											BigDecimal sum1 = Utl.nvl(BigDecimal.valueOf(Utl.nvl(d.getSumma(), 0d)),
													BigDecimal.ZERO);
											BigDecimal sum2 = Utl.nvl(BigDecimal.valueOf(Utl.nvl(d.getPen(), 0d)),
													BigDecimal.ZERO);
											BigDecimal sum3 = sum1.subtract(sum2);
											//log.info("sum1={}, sum2={}, sum3={}", sum1, sum2, sum3);
											//amntSummByUk.add(new SummByUk(t.getMark(), houseMng.getUkByDt(d.getPayment()
											//		.getKart().getKw().getHouse(), genDt), sum3));
											amntSummByUk.add(new SummByUk(t.getMark(), d.getPayment()
													.getKart().getUk(), sum3));
										} else if (t.getVar().getCd().equals("PAYORD_SUM_PEN_EX") || t.getVar().getCd().equals("PAYORD_SUM_PEN") ||
												t.getVar().getCd().equals("PAYORD_SUM_PEN2")) {
											// Пеня
											//amntSummByUk.add(new SummByUk(t.getMark(), houseMng.getUkByDt(d.getPayment()
											//		.getKart().getKw().getHouse(), genDt),
											//		BigDecimal.valueOf(Utl.nvl(d.getPen(), 0d))));
											amntSummByUk.add(new SummByUk(t.getMark(), d.getPayment()
													.getKart().getUk(),
													BigDecimal.valueOf(Utl.nvl(d.getPen(), 0d))));
										}
									}
							);
						}		
				}


				log.info("Список сумм по маркерам:"); 
				amntSummByUk.amnt.stream().forEach(e-> {
					log.info("org.id={}, org.name={}, mark={}, summa={}", 
							e.getUk().getId(), e.getUk().getName(), e.getMark(), e.getSumma());
				});

				//ukLst.stream().forEach(uk -> {
				for (Org uk : ukLst) {
					
					// По каждой УК, за период:
					BigDecimal summa1 = null;
					BigDecimal summa2 = null;
					BigDecimal summa3 = null;
					BigDecimal summa4 = null;
					BigDecimal summa5 = null;
					AmntFlow amntFlow = null;
					PayordFlow salFlow = null;
					BigDecimal insal = null;
					
					try {
						// получить сборы по всем маркерам
						summa1 = calcMark(markLst, amntSummByUk, p, uk);
						// получить сумму перечислений
						amntFlow = calcFlow(p, uk, period, null, null, 2);
						summa2 = amntFlow.summa;
						// получить сумму корректировок сборов
						amntFlow = calcFlow(p, uk, period, null, null, 3);
						summa3 = amntFlow.summa;
						// получить сумму корректировок перечислений
						amntFlow = calcFlow(p, uk, period, null, null, 4);
						summa4 = amntFlow.summa;
						// получить сумму удержаний
						amntFlow = calcFlow(p, uk, period, null, null, 5);
						summa5 = amntFlow.summa;
						// получить вх. сальдо по Платежке + УК
						salFlow = getInsal(p, uk, period, 0);
					} catch (Exception e) {
						e.printStackTrace();
						log.error("Ошибка при формировании платежки: Payord.id={} uk.id={}", p.getId(), uk.getId());
						throw new WrongExpression("Ошибка при обработке формулы платежки!");
					}

					if (salFlow != null) {
						insal = BigDecimal.valueOf(salFlow.getSumma());
					} else {
						insal = BigDecimal.ZERO;
					}
					 
					// рассчитать сумму, рекомендованную к перечислению
					BigDecimal summa6 = insal.add(summa1).subtract(summa2).add(summa3).subtract(summa4)
							.subtract(summa5);
					if (summa1.compareTo(BigDecimal.ZERO) != 0 || summa2.compareTo(BigDecimal.ZERO) != 0
							|| summa3.compareTo(BigDecimal.ZERO) != 0 || summa4.compareTo(BigDecimal.ZERO) != 0
							|| summa5.compareTo(BigDecimal.ZERO) != 0 || summa6.compareTo(BigDecimal.ZERO) != 0) {
						log.info("По УК.id={} и по Платежке id={}", uk.getId(), p.getId());
						log.info("Контроль сумм вх.сальдо={}, сумма сборов={}, перечисл.={}, корр.сбор.={}, корр.перечисл.={}, удержано={}, Итого={}",
						       insal, summa1, summa2, summa3, summa4, summa5, summa6);
					}

					if (isEndMonth) {
						// Итоговое формирование по концу месяца
						
						// удалить уже сформир.сальдо
						List<PayordFlow> lst = p.getPayordFlow().stream()
								.filter(t -> t.getTp() == 0 && t.getPeriod().equals(periodNext)	
								&& t.getPayord().equals(p)	&& t.getUk().equals(uk))
								.collect(Collectors.toList());
						for (Iterator<PayordFlow> iterator = lst.iterator(); iterator.hasNext();) {
							p.getPayordFlow().remove(iterator.next());
						}
						
						// добавить сальдо, если изменилось, даже если новое - 0 (если отличается)
						if (!summa6.equals(insal)) {
							PayordFlow flow = new PayordFlow(p, uk, summa6.doubleValue(), null, null, null, null, null, null, null,
									0, periodNext, false, false, null, null, null);
							p.getPayordFlow().add(flow);
						}

						// получить вх. сальдо для бухг.
						salFlow = getInsal(p, uk, period, 1);
						if (salFlow != null) {
							insal = BigDecimal.valueOf(salFlow.getSumma());
						} else {
							insal = BigDecimal.ZERO;
						}

						// рассчитать сумму сальдо по бухгалтерии
						// получить сумму перечислений для сальдо по бухгалтерии (взять по фактическим
						// датам)
						amntFlow = calcFlow(p, uk, null, curDt1, curDt2, 2);
						summa2 = amntFlow.summa;
						summa6 = insal.add(summa1).subtract(summa2).add(summa3).subtract(summa4).subtract(summa5);
						
						if (insal.compareTo(BigDecimal.ZERO) != 0 || summa1.compareTo(BigDecimal.ZERO) != 0 || summa2.compareTo(BigDecimal.ZERO) != 0
								|| summa3.compareTo(BigDecimal.ZERO) != 0 || summa4.compareTo(BigDecimal.ZERO) != 0
								|| summa5.compareTo(BigDecimal.ZERO) != 0 || summa6.compareTo(BigDecimal.ZERO) != 0) {
							log.info("По УК.id={} и по Платежке id={}", uk.getId(), p.getId());
							log.info("Сальдо по бух insal={}, summa1={}, summa2={}, summa3={}, summa4={}, summa5={}, summa6={}", insal,
								summa1, summa2, summa3, summa4, summa5, summa6);
						}

						// удалить уже сформир.сальдо по бухг.
						lst = p.getPayordFlow().stream().filter(t -> t.getTp() == 1 && t.getPeriod().equals(periodNext)
								&& t.getPayord().equals(p)	&& t.getUk().equals(uk))
								.collect(Collectors.toList());
						for (Iterator<PayordFlow> iterator = lst.iterator(); iterator.hasNext();) {
							p.getPayordFlow().remove(iterator.next());
						}
							
						// добавить сальдо, если изменилось
						if (!summa6.equals(insal) && summa6.compareTo(BigDecimal.ZERO) != 0) {
							PayordFlow flow = new PayordFlow(p, uk, summa6.doubleValue(), null, null, null, null, null, null, null,
									1, periodNext, false, false, null, null, null);
							p.getPayordFlow().add(flow);
							//if (p.getId()==244 && uk.getId()==1832) {
								//log.info("Check p.id={}, uk.id={}, periodNext={}", p.getId(), uk.getId(), periodNext);
							//}
						}
					} else {
						// добавить платежку
						if (isFinal) {
							// получить сумму перечислений для сальдо по бухгалтерии (взять по фактическим
							// датам)
							//amntFlow = calcFlow(p, uk, null, curDt1, curDt2, 2);
							//summa2 = amntFlow.summa;
							//summa6 = insal.add(summa1).subtract(summa2).add(summa3).subtract(summa4).subtract(summa5); // TODO добавил 

						} else {
							// округлить, если не итоговая плат. по концу мес.
							summa6 = summa6.setScale(0, BigDecimal.ROUND_DOWN);
						}
						// занулить, если отрицательная или если информационная платежка (status=0)
						if (summa6.floatValue() < 0 || !isGen) {
							summa6 = BigDecimal.ZERO;
						}

						if (summa1.compareTo(BigDecimal.ZERO) != 0 || summa2.compareTo(BigDecimal.ZERO) != 0
								|| summa3.compareTo(BigDecimal.ZERO) != 0 || summa4.compareTo(BigDecimal.ZERO) != 0
								|| summa5.compareTo(BigDecimal.ZERO) != 0 || summa6.compareTo(BigDecimal.ZERO) != 0) {
							log.info("Платежка: сумма сборов={}, перечисл.={}, корр.сбор.={}, корр.перечисл.={}, удержано={}, Итого={}",
								       summa1, summa2, summa3, summa4, summa5, summa6);
							// создать движение по платежке, если не нулевой хоть один компонент
							PayordFlow flow = new PayordFlow(p, uk, summa6.doubleValue(), summa1.doubleValue(),
									summa2.doubleValue(), summa3.doubleValue(), summa4.doubleValue(),
									summa5.doubleValue(), summa6.doubleValue(), null, 2, period, false, isFinal, genDt, 
									null, (isGen ? 1: 0));
							p.getPayordFlow().add(flow);
						}
					}
				}
		}

		// добавить периоды
		if (!isEndMonth) {
			// период - дата формирования
			reportMng.addPeriodReport("RptPayDocList", null, genDt);
		} else {
			// период - текущий и следующий месяц
			reportMng.addPeriodReport("RptPayDocList", period, null);
			reportMng.addPeriodReport("RptPayDocList", periodNext, null);
		}
		
		long endTime1 = System.currentTimeMillis() - beginTime;
		log.info("TIMING: время формирования платежек: {}",	endTime1);
	}

	/**
	 * Формировать ли, базируясь на параметрах
	 * @param genDt - текущая дата
	 * @param days - выбранные дни
	 * @param periodTp - тип периода
	 * @return - формировать?
	 * @throws WrongDate
	 */
	private Boolean isGenByDt(Date genDt, String days, String periodTp) throws WrongDate {
		Boolean isGen = false;
		// Получить наименование текущего дня недели
		SimpleDateFormat formatter = new SimpleDateFormat("E");
		String dayOfWeek = Utl.convertDaysToEng(formatter.format(genDt));

		if (periodTp.equals("PAYORD_SELDAY")	|| periodTp.equals("PAYORD_SELDAY2")) {
			// Платежка в определённый день недели
			if (days == null) {
				throw new WrongDate("При формировании платежки в определенный день недели, не задан день формирования");
			} else {
				String selDays = Utl.convertDaysToEng(days);
				log.info("dayOfWeek, selDays = {},{}", dayOfWeek, selDays);

				if (selDays.contains(dayOfWeek)) {
					isGen = true;
				} else if (periodTp.equals("PAYORD_SELDAY2") && Utl.getLastDate(genDt).equals(genDt) ) {
					// для этого типа платёжек -  еще и по последнему дню месяца
					isGen = true;
				}
				
			}
		} else if (periodTp.equals("PAYORD_EVERYMONTH")) {
			// платежка в конце месяца
			if (Utl.getLastDate(genDt).equals(genDt)) {
				isGen = true;
			}
		} else if (periodTp.equals("PAYORD_EVERYDAY")) {
			// платежка каждый день
			isGen = true;
		}
		return isGen;
	}
	

	
}