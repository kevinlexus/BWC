package com.ric.bill.dao;

import com.ric.bill.model.bs.Obj;


public interface ObjDAO {

	public Obj getByCD(int rqn, String cd);
	
}
