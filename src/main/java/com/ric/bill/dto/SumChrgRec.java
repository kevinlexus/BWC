package com.ric.bill.dto;


import com.ric.bill.model.exs.ServGis;

import lombok.Getter;
import lombok.Setter;

/**
 * Строка значений сумм начислений, услуги из ГИС, summa
 * @author lev
 *
 */
@Getter @Setter
public class SumChrgRec {

	// отношение услуги к услуги из ГИС 
	private ServGis servGis; 

	// сумма
	private Double summa;

	// объем
	private Double vol;

	// цена
	private Double price;

	// конструктор
	public SumChrgRec(ServGis servGis, Double summa, Double vol, Double price) {
		super();
		this.servGis = servGis;
		this.summa = summa;
		this.vol = vol;
		this.price = price;
	}
	
}

