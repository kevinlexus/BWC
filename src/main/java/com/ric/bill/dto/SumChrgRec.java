package com.ric.bill.dto;

import java.math.BigDecimal;

import com.ric.bill.model.exs.Ulist;

/*
 * DTO для хранения записи начисления
 * @author - Lev
 * @ver 1.01
 */
public interface SumChrgRec {
	// Id услуги из ГИС 
	BigDecimal getUlistId(); 
	// сумма
	Double getSumma();
	// объем
	Double getVol();
	// цена
	Double getPrice();
	// услуга из справочника ГИС
	Ulist getUlist();
	// услуга из справочника ГИС
	void setUlist(Ulist ulist);
	
}