package com.ric.bill.dto;


import java.math.BigDecimal;
import java.util.Date;

import com.ric.bill.model.bs.Org;
import com.ric.bill.model.fn.Privilege;
import com.ric.bill.model.ps.Pers;
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;

/**
 * Строка начисления, детализированная
 * @author lev
 *
 */
@Getter @Setter
public class ChrgRecDet {

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
	private Pers pers;
	private Privilege priv;
	private BigDecimal discount;
	
    /**
     * конструктор 
     * @param sum - сумма
     * @param vol - объем
     * @param price - расценка
     * @param pricePriv - расценка по льготе (null по нельготной услуге)
     * @param discount - дисконт по расценке
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
	public ChrgRecDet(BigDecimal vol, BigDecimal price, BigDecimal pricePriv, BigDecimal discount, BigDecimal stdt, Integer cntFact, BigDecimal area, 
				   Serv serv, Org org, Integer met, Integer entry, Date dt1, Date dt2, Integer cntOwn, Pers pers, Privilege priv) {
		setVol(vol);
		setPrice(price);
		setPricePriv(pricePriv);
		setDiscount(discount);
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
		setPers(pers);
		setPriv(priv);
	}
	
}

