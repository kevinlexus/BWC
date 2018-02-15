package com.ric.bill.dto;


import com.ric.bill.model.exs.ServGis;
import com.ric.bill.model.exs.Ulist;

import lombok.Getter;
import lombok.Setter;

/**
 * Строка значений сумм начислений, услуги из ГИС, summa
 * @author lev
 *
 */
@Getter @Setter
public class SumChrgRec {

	// услуга из ГИС 
	private Ulist ulist; 

	// сумма
	private Double summa;

	// объем
	private Double vol;

	// цена
	private Double price;

	// конструктор
	public SumChrgRec(Ulist ulist, Double summa, Double vol, Double price) {
		super();
		this.ulist = ulist;
		this.summa = summa;
		this.vol = vol;
		this.price = price;
	}
	
}

