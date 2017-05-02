package com.ric.bill.mm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ric.bill.dao.VolDAO;
import com.ric.bill.mm.VolMng;

@Service
public class VolMngImpl implements VolMng {

	@Autowired
	private VolDAO vDao;


}