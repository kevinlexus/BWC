package com.ric.bill.dto;


import com.ric.bill.model.mt.Meter;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO для физ счетчика, с его статусом работы 
 * @author lev
 *
 */
@Getter @Setter
public class MeterDTO {

	// счетчик
	private Meter meter;
	// статус работы счетчика
	private Double tp;
	
	public MeterDTO(Meter meter, Double tp) {
		super();
		this.meter = meter;
		this.tp = tp;
	}
	
}

