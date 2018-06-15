package com.ric.bill.dto;

import java.math.BigDecimal;

/*
 * Projection для хранения записи сальдо, дебет, кредит
 * @author - Lev
 * @ver 1.00
 */
public interface SumSaldoRec {

	// вх.дебет
	BigDecimal getIndebet();
	// вх.кредит
	BigDecimal getInkredit();

	// исх.дебет
	BigDecimal getOutdebet();
	// исх.кредит
	BigDecimal getOutkredit();
	// оплата
	BigDecimal getPayment();
}
