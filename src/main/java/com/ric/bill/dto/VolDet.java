package com.ric.bill.dto;


import java.math.BigDecimal;
import java.util.Date;

import com.ric.bill.model.bs.Org;
import com.ric.bill.model.fn.PersPrivilege;
import com.ric.bill.model.fn.Privilege;
import com.ric.bill.model.ps.Pers;
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;

/**
 * Строка объемов, детализированная
 * @author lev
 *
 */
@Getter @Setter
public class VolDet {

	private BigDecimal vol;
	private BigDecimal price;
	private BigDecimal pricePriv;
	private Serv serv;
	private Org org;
    private Date dt1, dt2;
	private BigDecimal stdt;
	private Integer cntFact;
	private Integer cntOwn;
	private BigDecimal area;
	private Integer met;
	private Integer entry;
	private PersPrivilege persPriv;
	private Integer tp;
	
    /**
     * конструктор 
     * @param sum - сумма
     * @param vol - объем
     * @param price - расценка
     * @param pricePriv - расценка по льготе (null по нельготной услуге)
     * @param tp - вариант расчета льготы
     * @param stdt - норматив
     * @param cntFact - кол-во прожив по факту (без собственников)
     * @param serv - услуга
     * @param org - организация
     * @param met - наличие счетчика
     * @param entry - номер ввода
     * @param dt1 - дата начала
     * @param dt2 - дата окончания
     * @param cntOwn - кол-во собственников
     * @param pers - проживающий
     * @param priv - привилегия
	 */
	public VolDet(BigDecimal vol, BigDecimal price, BigDecimal pricePriv, Integer tp, BigDecimal stdt, Integer cntFact, BigDecimal area, 
				   Serv serv, Org org, Integer met, Integer entry, Date dt1, Date dt2, Integer cntOwn, PersPrivilege persPriv) {
		setVol(vol);
		setPrice(price);
		setPricePriv(pricePriv);
		setTp(tp);
		setStdt(stdt);
		setArea(area);
		setServ(serv);
		setOrg(org);
		setMet(met);
		setEntry(entry);
		setDt1(dt1);
		setDt2(dt2);
		setCntFact(cntFact);
		setCntOwn(cntOwn);
		setPersPriv(persPriv);
	}
	
}

