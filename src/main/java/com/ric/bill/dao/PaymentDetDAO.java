package com.ric.bill.dao;

import java.util.Date;
import java.util.List;

import com.ric.bill.model.cash.PaymentDet;


public interface PaymentDetDAO {

	public List<PaymentDet> getPaymentDetByPeriod(String period, Date curDt1, Date curDt2, Date trimDt);
	
}
