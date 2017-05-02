package com.ric.bill.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * DTO - Движение по платежкам
 * @author lev
 *
 */
public class PayordFlowDTO {

	// Id
	private Integer id;
	// тип записи
	private Integer tp;
	// Fk на платежку
	private Integer payordFk;
	// Fk на УК
	private Integer ukFk;
	// Перечислено по факту (платежка)
	private Double summa;
	// Сумма сборов, факт на день формир. (платежка)
	private Double summa1;
	// Сумма перечислений, факт на день формир. (платежка)
	private Double summa2;
	// Сумма корректировок сборов, факт на день формир. (платежка)
	private Double summa3;
	// Сумма корректировок перечисл, факт на день формир. (платежка)
	private Double summa4;
	// Сумма удержаний, факт на день формир. (платежка)
	private Double summa5;
	// Сумма рекомендованная к перечислению (платежка)
	private Double summa6;

	// № п.п.
	private Integer npp;
	// Подпись (платежка)
	private Boolean signed;
	// Дата события, Дата платежного поручения (платежка)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Krasnoyarsk")
	private Date dt;
	// Период
	private String period;

	// Здесь важен default конструктор (иначе не примет обратно данные, методом POST)
	public PayordFlowDTO() {
		
	}

	// конструктор
	public PayordFlowDTO(Integer id, Integer tp, Integer payordFk,
			Integer ukFk, Double summa, Double summa1, Double summa2,
			Double summa3, Double summa4, Double summa5, Double summa6, Integer npp, Boolean signed,
			Date dt, String period) {
		super();
		this.id = id;
		this.tp = tp;
		this.payordFk = payordFk;
		this.ukFk = ukFk;
		this.summa = summa;
		this.summa1 = summa1;
		this.summa2 = summa2;
		this.summa3 = summa3;
		this.summa4 = summa4;
		this.summa5 = summa5;
		this.summa6 = summa6;
		this.npp = npp;
		this.signed = signed;
		this.dt = dt;
		this.period = period;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTp() {
		return tp;
	}

	public void setTp(Integer tp) {
		this.tp = tp;
	}

	public Integer getPayordFk() {
		return payordFk;
	}

	public void setPayordFk(Integer payordFk) {
		this.payordFk = payordFk;
	}

	public Integer getUkFk() {
		return ukFk;
	}

	public void setUkFk(Integer ukFk) {
		this.ukFk = ukFk;
	}

	public Double getSumma() {
		return summa;
	}

	public void setSumma(Double summa) {
		this.summa = summa;
	}

	public Double getSumma1() {
		return summa1;
	}

	public void setSumma1(Double summa1) {
		this.summa1 = summa1;
	}

	public Double getSumma2() {
		return summa2;
	}

	public void setSumma2(Double summa2) {
		this.summa2 = summa2;
	}

	public Double getSumma3() {
		return summa3;
	}

	public void setSumma3(Double summa3) {
		this.summa3 = summa3;
	}

	public Double getSumma4() {
		return summa4;
	}

	public void setSumma4(Double summa4) {
		this.summa4 = summa4;
	}

	public Double getSumma5() {
		return summa5;
	}

	public void setSumma5(Double summa5) {
		this.summa5 = summa5;
	}

	public Double getSumma6() {
		return summa6;
	}

	public void setSumma6(Double summa6) {
		this.summa6 = summa6;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Integer getNpp() {
		return npp;
	}

	public void setNpp(Integer npp) {
		this.npp = npp;
	}

	public Boolean getSigned() {
		return signed;
	}

	public void setSigned(Boolean signed) {
		this.signed = signed;
	}
	
	
}
