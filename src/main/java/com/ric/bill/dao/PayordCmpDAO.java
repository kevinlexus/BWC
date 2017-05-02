package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.fn.PayordCmp;


public interface PayordCmpDAO {

	List<PayordCmp> getPayordCmpByPayordId(Integer payordId);

	
}
