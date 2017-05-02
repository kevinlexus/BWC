package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.sec.Vsec;


public interface VsecDAO {

	public List<Vsec> getPrivByUserRoleAct(String userCd, String roleCd, String actCd);
}
