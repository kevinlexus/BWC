package com.ric.bill.mm.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.ric.bill.dao.TaskParDAO;
import com.ric.bill.excp.WrongGetMethod;
import com.ric.bill.mm.TaskParMng;
import com.ric.bill.mm.ParMng;
import com.ric.bill.model.bs.Par;
import com.ric.bill.model.exs.TaskPar;


@Service
@Slf4j
public class TaskParMngImpl implements TaskParMng {

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private ParMng parMng;
	@Autowired
	private TaskParDAO actionParDao;

	/**
	 * получить значение параметра типа Double действия по CD свойства
	 * @param actionId - ID действия
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod 
	 */
	public Double getDbl(Integer actionId, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("NM")) {
			TaskPar ap = actionParDao.getTask(actionId, parCd);
			if (ap!= null) {
				return ap.getN1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}

	/**
	 * получить значение параметра типа String действия по CD свойства
	 * @param actionId - ID действия
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod 
	 */
	public String getStr(Integer actionId, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("ST")) {
			TaskPar ap = actionParDao.getTask(actionId, parCd);
			if (ap!= null) {
				return ap.getS1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}

	/**
	 * получить значение параметра типа Date действия по CD свойства
	 * @param actionId - ID действия
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod 
	 */
	public Date getDate(Integer actionId, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("DT")) {
			TaskPar ap = actionParDao.getTask(actionId, parCd);
			if (ap!= null) {
				return ap.getD1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}
	
}