package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.ar.House;
import com.ric.bill.model.bs.Par;


public interface ParDAO {


	public Par getByCd(int rqn, String cd);

	public boolean checkPar(int rqn, int id, String cd, String dataTp);
}
