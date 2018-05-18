package com.ric.bill.mm.impl;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ric.bill.dao.EolinkDAO;
import com.ric.bill.dao.EolinkToEolinkDAO;
import com.ric.bill.dao.PdocDAO;
import com.ric.bill.mm.EolinkMng;
import com.ric.bill.mm.PdocMng;
import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.exs.Pdoc;


@Service
@Slf4j
public class PdocMngImpl implements PdocMng {

	@Autowired
	private PdocDAO pdocDao;

	/**
	 * Получить список незагруженных ПД в ГИС по Дому, по всем помещениям
	 * отсортированно по номеру документа в биллинге
	 * @param house - дом
	 * @return 
	 */
	@Override
	public List<Pdoc> getPdocForLoadByHouse(Eolink houseEol) {
		
		List<Pdoc> lst = pdocDao.getForLoadByHouseWithEntry(houseEol.getId());
		
		lst.addAll(pdocDao.getForLoadByHouseWOEntry(houseEol.getId()));
		
		return lst;
	}
	
	
}