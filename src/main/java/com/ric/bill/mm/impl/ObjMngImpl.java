package com.ric.bill.mm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ric.bill.dao.ObjDAO;
import com.ric.bill.mm.ObjMng;
import com.ric.bill.model.bs.Obj;

@Service
public class ObjMngImpl implements ObjMng {


    @Autowired
	private ObjDAO oDao;

    public synchronized Obj getByCD(int rqn, String cd) {
		return oDao.getByCD(rqn, cd);
	}


}