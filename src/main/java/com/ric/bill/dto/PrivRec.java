package com.ric.bill.dto;


import java.math.BigDecimal;
import java.util.Date;

import com.ric.bill.model.bs.Org;
import com.ric.bill.model.fn.PersPrivilege;
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;

/**
 * Строка начисления льготы, для сохранения в таблицу FN.PRIVILEGE_CHRG
 * @author lev
 *
 */
@Getter @Setter
public class PrivRec {

	// услуга
	private Serv serv;
	// организация
	private Org org;
	// начисление льготы 
	private BigDecimal summa;
	// объем 
	private BigDecimal vol;
	// привиления проживающего
	private PersPrivilege persPriv;
	// расценка с учетом льготы 
	private BigDecimal price;
	// дата начала, окончания
    private Date dt1, dt2;

    // конструктор
	public PrivRec(Serv serv, Org org, BigDecimal summa, BigDecimal vol, PersPrivilege persPriv, BigDecimal price, Date dt1,
			Date dt2) {
		super();
		this.serv = serv;
		this.org = org;
		this.summa = summa;
		this.vol = vol;
		this.price = price;
		this.persPriv = persPriv;
		this.dt1 = dt1;
		this.dt2 = dt2;
	}
	
}

