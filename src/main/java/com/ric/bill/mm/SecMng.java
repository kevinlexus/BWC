package com.ric.bill.mm;

import java.util.List;

import com.ric.bill.dto.KoDTO;
import com.ric.bill.model.sec.Vsec;


public interface SecMng  {

	public List<Vsec> getPrivByUserRoleAct(String userCd, String roleCd, String actCd);

	List<KoDTO> getKoCurUser(String roleCd, String actCd);
}