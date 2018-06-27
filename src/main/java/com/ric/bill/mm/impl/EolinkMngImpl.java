package com.ric.bill.mm.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ric.bill.dao.EolinkDAO;
import com.ric.bill.mm.EolinkMng;
import com.ric.bill.model.exs.Eolink;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class EolinkMngImpl implements EolinkMng {

	@Autowired
	private EolinkDAO eolinkDao;

	/**
     * Получить Внешний объект по reu,kul,nd
     * @param reu - REU из Квартплаты
     * @param kul - KUL из Квартплаты
     * @param nd -  ND из Квартплаты
     * @param kw -  KW из Квартплаты
     * @param entry -  ENTRY из Квартплаты
     * @param tp -  тип объекта
     */
	@Override
	public Eolink getEolinkByReuKulNdTp(String reu, String kul, String nd,
			String kw, String entry, String tp) {
		return eolinkDao.getEolinkByReuKulNdTp(reu, kul, nd, kw, entry, tp);
	}


	/* Поменять статус "актив" всех дочерних объектов по типу
	 * @param - eolink - объект
	 * @param - tp - тип объекта
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor=Exception.class) //rollbackFor=Exception.class - означает, что все исключения, выбрасываемые данным методом, должны приводить к откату транзакции.
	public void setChildActive(Eolink eolink, String tp, Integer status) {
		eolink.getChild().stream().forEach(t-> {
			t.setStatus(status);
		});
	}


}