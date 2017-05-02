package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.cash.PaymentDet;


public interface PaymentDetDAO {

	public List<PaymentDet> getPaymentDetByPeriod(String period);
	
}
