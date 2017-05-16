package com.ric.bill.mm.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.ric.bill.Storable;
import com.ric.bill.Utl;
import com.ric.bill.dao.ParDAO;
import com.ric.bill.excp.EmptyServ;
import com.ric.bill.excp.EmptyStorable;
import com.ric.bill.excp.WrongGetMethod;
import com.ric.bill.excp.WrongSetMethod;
import com.ric.bill.mm.ActionParMng;
import com.ric.bill.mm.ParMng;
import com.ric.bill.model.bs.Dw;
import com.ric.bill.model.bs.Par;
import com.ric.bill.model.exs.Action;


@Service
@Slf4j
public class ActionParMngImpl implements ActionParMng {

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private ParMng parMng;

	/**
	 * получить значение параметра типа Double действия по CD свойства
	 */
	public Double getDbl(Action action, String cd) {
		Par par = parMng.getByCD(-1, cd);
		
		// TODO сделать реализацию!
		return null;
	}

	@Override
	public String getStr(Action act, String cd) {

		// TODO сделать реализацию!
		return null;
	}

	@Override
	public Date getDate(Action act, String cd) {

		// TODO сделать реализацию!
		return null;
	}
	
}