package com.ric.bill.dto;


import lombok.Getter;
import lombok.Setter;

/**
 * DTO для физ счетчика, с его статусом работы
 * @author lev
 *
 */
@Getter @Setter
public class MeterDTO {

	// ID счетчика
	private Integer meterId;
	// лиц.счет
	private Integer lsk;
	// статус работы счетчика
	private Integer tp;
	// для сортировки (1-неисправен и т.п., 0 -исправен, нет показаний)
	private Integer ord;

	public MeterDTO(Integer lsk, Integer meterId, Integer tp, Integer ord) {
		super();
		this.lsk = lsk;
		this.meterId = meterId;
		this.tp = tp;
		this.ord = ord;
	}

}

