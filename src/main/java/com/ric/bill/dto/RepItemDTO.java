package com.ric.bill.dto;

import java.math.BigDecimal;

/**
 * DTO - предназначен для заполнения отчета данными
 * @author lev
 *
 */
public class RepItemDTO {
	Integer id;
	String payordGrpName;
	String payordName;
	String ukName;
	BigDecimal insal;
	BigDecimal summa;
	BigDecimal summa1;
	BigDecimal summa2;
	BigDecimal summa3;
	BigDecimal summa4;
	BigDecimal summa5;
	BigDecimal summa6;
	
	public RepItemDTO(Integer id, String payordGrpName, String payordName,
			String ukName, BigDecimal insal, BigDecimal summa, BigDecimal summa1, BigDecimal summa2,
			BigDecimal summa3, BigDecimal summa4, BigDecimal summa5, BigDecimal summa6) {
		super();
		this.id = id;
		this.payordGrpName = payordGrpName;
		this.payordName = payordName;
		this.ukName = ukName;
		this.summa = summa;
		this.summa1 = summa1;
		this.summa2 = summa2;
		this.summa3 = summa3;
		this.summa4 = summa4;
		this.summa5 = summa5;
		this.summa6 = summa6;
		this.insal = insal;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPayordGrpName() {
		return payordGrpName;
	}
	public void setPayordGrpName(String payordGrpName) {
		this.payordGrpName = payordGrpName;
	}
	public String getPayordName() {
		return payordName;
	}
	public void setPayordName(String payordName) {
		this.payordName = payordName;
	}
	public String getUkName() {
		return ukName;
	}
	public void setUkName(String ukName) {
		this.ukName = ukName;
	}
	public BigDecimal getSumma() {
		return summa;
	}
	public void setSumma(BigDecimal summa) {
		this.summa = summa;
	}
	public BigDecimal getSumma1() {
		return summa1;
	}
	public void setSumma1(BigDecimal summa1) {
		this.summa1 = summa1;
	}
	public BigDecimal getSumma2() {
		return summa2;
	}
	public void setSumma2(BigDecimal summa2) {
		this.summa2 = summa2;
	}
	public BigDecimal getSumma3() {
		return summa3;
	}
	public void setSumma3(BigDecimal summa3) {
		this.summa3 = summa3;
	}
	public BigDecimal getSumma4() {
		return summa4;
	}
	public void setSumma4(BigDecimal summa4) {
		this.summa4 = summa4;
	}
	public BigDecimal getSumma5() {
		return summa5;
	}
	public void setSumma5(BigDecimal summa5) {
		this.summa5 = summa5;
	}
	public BigDecimal getSumma6() {
		return summa6;
	}
	public void setSumma6(BigDecimal summa6) {
		this.summa6 = summa6;
	}

	public BigDecimal getInsal() {
		return insal;
	}

	public void setInsal(BigDecimal insal) {
		this.insal = insal;
	}

		
}
