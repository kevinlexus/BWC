package com.ric.bill.mm.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.ric.bill.dao.EolinkParDAO;
import com.ric.bill.excp.WrongGetMethod;
import com.ric.bill.mm.EolinkParMng;
import com.ric.bill.mm.ParMng;
import com.ric.bill.model.bs.Par;
import com.ric.bill.model.exs.EolinkPar;


@Service
@Slf4j
public class EolinkParMngImpl implements EolinkParMng {

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private ParMng parMng;
	@Autowired
	private EolinkParDAO eolinkParDao;

	/**
	 * получить значение параметра типа Double связанного объекта по CD свойства
	 * @param eolinkId - ID связанного объекта
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod 
	 */
	public Double getDbl(Integer eolinkId, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("NM")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolinkId, parCd);
			if (ap!= null) {
				return ap.getN1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}

	/**
	 * получить значение параметра типа String связанного объекта по CD свойства
	 * @param eolinkId - ID связанного объекта
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod 
	 */
	public String getStr(Integer eolinkId, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("ST")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolinkId, parCd);
			if (ap!= null) {
				return ap.getS1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}

	/**
	 * получить значение параметра типа Date связанного объекта по CD свойства
	 * @param eolinkId - ID связанного объекта
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod 
	 */
	public Date getDate(Integer eolinkId, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("DT")) {
			EolinkPar ap = eolinkParDao.getEolinkPar(eolinkId, parCd);
			if (ap!= null) {
				return ap.getD1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}
	
}