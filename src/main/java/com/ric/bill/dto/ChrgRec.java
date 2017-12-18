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
 * Строка начисления, для сохранения в таблицу CHRG
 * @author lev
 *
 */
@Getter @Setter
public class ChrgRec {

	// начисление полное 
	private BigDecimal sumFull;
	// начисление льготы
	private BigDecimal sumPriv;
	// начисление итоговое, с учётом льготы
	private BigDecimal sumAmnt;
	// цена полная 
	private BigDecimal price;
	// услуга
	private Serv serv;
	// организация
	private Org org;
	// дата начала, окончания
    private Date dt1, dt2;
    // норматив по соцнорме
	private BigDecimal stdt;
	// кол-во проживающих
	private Integer cntFact;
	// кол-во собственников
	private Integer cntOwn;
	// дополнительный объемный показатель
	private BigDecimal area;
	// начисляется по счетчику - 1, нет - 0, null
	private Integer met;
	// номер ввода
	private Integer entry;
	// объем 
	private BigDecimal vol;

	// конструктор
	public ChrgRec(BigDecimal sumFull, BigDecimal sumPriv, BigDecimal sumAmnt, BigDecimal price, 
			Serv serv, Org org, Date dt1, Date dt2, BigDecimal stdt, Integer cntFact, Integer cntOwn, BigDecimal area,
			Integer met, Integer entry, BigDecimal vol) {
		super();
		this.sumFull = sumFull;
		this.sumPriv = sumPriv;
		this.sumAmnt = sumAmnt;
		this.price = price;
		this.serv = serv;
		this.org = org;
		this.dt1 = dt1;
		this.dt2 = dt2;
		this.stdt = stdt;
		this.cntFact = cntFact;
		this.cntOwn = cntOwn;
		this.area = area;
		this.met = met;
		this.entry = entry;
		this.vol = vol;
	}

	
	
}

