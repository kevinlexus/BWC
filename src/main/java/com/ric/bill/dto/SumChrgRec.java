package com.ric.bill.dto;

import com.ric.bill.model.exs.Ulist;

/*
 * Projection для хранения записи начисления
 * @author - Lev
 * @ver 1.01
 */
public interface SumChrgRec {
	// Id услуги из ГИС
	Integer getUlistId();
	// сумма
	Double getSumma();
	// объем
	Double getVol();
	// цена
	Double getPrice();
	// вспомогательный коэффициент (например, чтобы перевести м2 в гигаКаллорию для отопления - для старой разработки (МП РИЦ))
	//Double getCoeff();
	// общая площадь (работает совместно с вспомогательным коэффициентом)
	Double getSqr();
	// услуга из справочника ГИС
	Ulist getUlist();
	// услуга из справочника ГИС
	void setUlist(Ulist ulist);

}