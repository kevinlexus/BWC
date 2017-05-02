package com.ric.bill.mm.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ric.bill.Config;
import com.ric.bill.dao.AreaDAO;
import com.ric.bill.dao.KoDAO;
import com.ric.bill.dao.OrgDAO;
import com.ric.bill.dao.VsecDAO;
import com.ric.bill.dto.DTOBuilder;
import com.ric.bill.dto.KoDTO;
import com.ric.bill.mm.SecMng;
import com.ric.bill.model.sec.Vsec;

@Service
@Slf4j
public class SecMngImpl implements SecMng {

    @Autowired
	private VsecDAO vDao;
    @Autowired
	private OrgDAO orgDao;
    @Autowired
	private AreaDAO areaDao;
    @Autowired
	private KoDAO klskDao;
    @Autowired
	private Config config;
    @Autowired
    private DTOBuilder dtoBuilder;
    
	/**
	 * Получить список объектов по которым доступна привилегия по параметрам
	 * @param userCd - пользователь 
	 * @param roleCd - роль
	 * @param actCd - действие
	 */
	@Override
	public List<Vsec> getPrivByUserRoleAct(String userCd, String roleCd, String actCd) {
		
		return vDao.getPrivByUserRoleAct(userCd, roleCd, actCd);
	}

	
	/**
	 * Получить список объектов типа KoDTO, доступных текущему пользователю по определенной роли, действию
	 * @param roleCd - роль
	 * @param actCd - действие
	 */
	@Override
	public List<KoDTO> getKoCurUser(String roleCd, String actCd) {
		String userCd = config.getCurUserCd();
		List<KoDTO> lst = new ArrayList <KoDTO>(); 
		vDao.getPrivByUserRoleAct(userCd, roleCd, actCd).stream().forEach(t -> 
				{
					KoDTO ko = dtoBuilder.getKoByKlsk(t.getKlsk());
					if (ko != null) {
						lst.add(ko);
					}
				}
				);
		return lst;
	}
	

	
}