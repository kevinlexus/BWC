package com.ric.bill.dto;

/*
 * DTO для хранения записи долга
 * @author - Lev
 * @ver 1.00
 */
public interface SumDebRec {
	// долг
	Double getSumma();
	// пеня
	Double getPen();
	// период в формате YYYYMM 
	String getDopl(); 
	
}