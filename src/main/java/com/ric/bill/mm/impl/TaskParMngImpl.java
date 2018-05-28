package com.ric.bill.mm.impl;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ric.bill.dao.TaskParDAO;
import com.ric.bill.mm.ParMng;
import com.ric.bill.mm.TaskParMng;
import com.ric.bill.model.bs.Par;
import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.exs.EolinkPar;
import com.ric.bill.model.exs.Task;
import com.ric.bill.model.exs.TaskPar;
import com.ric.cmn.excp.WrongGetMethod;


@Service
@Slf4j
public class TaskParMngImpl implements TaskParMng {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private ParMng parMng;
	@Autowired
	private TaskParDAO taskParDao;

	/**
	 * получить значение параметра типа Boolean связанного объекта по CD свойства
	 * @param eolink - связанный объект
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod 
	 */
	public Boolean getBool(Task task, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("BL") && par.getDataTp().equals("SI")) {
			TaskPar ap = taskParDao.getTaskPar(task, parCd);
			if (ap!= null) {
				if (ap.getN1() != null) {
					if (ap.getN1() == 1) {
						return true;
					} else {
						return false;
					}
				} else {
					return null;
				}
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}
	
	/**
	 * установить значение параметра типа Boolean связанного объекта по CD свойства
	 * в случае отсутствия - создать
	 * @param eolink - связанный объект
	 * @param parCd - CD параметра
	 * @param val - значение
	 * @throws WrongGetMethod 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setBool(Task task, String parCd, Boolean val) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("BL") && par.getDataTp().equals("SI")) {
			TaskPar ap = taskParDao.getTaskPar(task, parCd);
			Double val1 = null;
			if (val != null) {
				if (val) {
					val1 = 1D;
				} else {
					val1 = 0D;
				}
			}
			
			if (ap!= null) {
				// сохранить значение
				ap.setN1(val1);
			} else {
				// создать значение
				ap = new TaskPar(task, par, val1, null, null);
				em.persist(ap);
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
	}

	
	/**
	 * получить значение параметра типа Double задания по CD свойства
	 * @param task - задание
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod 
	 */
	public Double getDbl(Task task, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("NM") && par.getDataTp().equals("SI")) {
			TaskPar ap = taskParDao.getTaskPar(task, parCd);
			if (ap!= null) {
				return ap.getN1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}

	/**
	 * установить значение параметра типа Double задания по CD свойства
	 * @param task - задание
	 * @param parCd - CD параметра
	 * @param val - значение параметра
	 * @throws WrongGetMethod 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Double setDbl(Task task, String parCd, Double val) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("NM") && par.getDataTp().equals("SI")) {
			TaskPar ap = taskParDao.getTaskPar(task, parCd);
			if (ap!= null) {
				// сохранить значение
				ap.setN1(val);
			} else {
				// создать значение
				ap = new TaskPar(task, par, val, null, null);
				em.persist(ap);
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}

	/**
	 * получить значение параметра типа String задания по CD свойства
	 * @param task - задание
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod 
	 */
	public String getStr(Task task, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("ST") && par.getDataTp().equals("SI")) {
			TaskPar ap = taskParDao.getTaskPar(task, parCd);
			if (ap!= null) {
				return ap.getS1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}

	/**
	 * установить значение параметра типа String задания по CD свойства
	 * @param task - задание
	 * @param parCd - CD параметра
	 * @param val - значение параметра
	 * @throws WrongGetMethod 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Double setStr(Task task, String parCd, String val) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("ST") && par.getDataTp().equals("SI")) {
			TaskPar ap = taskParDao.getTaskPar(task, parCd);
			if (ap!= null) {
				// сохранить значение
				ap.setS1(val);
			} else {
				// создать значение
				ap = new TaskPar(task, par, null, val, null);
				em.persist(ap);
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}
	
	/**
	 * получить значение параметра типа Date задания по CD свойства
	 * @param task - задание
	 * @param parCd - CD параметра
	 * @throws WrongGetMethod 
	 */
	public Date getDate(Task task, String parCd) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("DT") && par.getDataTp().equals("SI")) {
			TaskPar ap = taskParDao.getTaskPar(task, parCd);
			if (ap!= null) {
				return ap.getD1();
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}

	/**
	 * установить значение параметра типа Date задания по CD свойства
	 * @param task - задание
	 * @param parCd - CD параметра
	 * @param val - значение параметра
	 * @throws WrongGetMethod 
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public Double setDate(Task task, String parCd, Date val) throws WrongGetMethod {
		Par par = parMng.getByCD(-1, parCd);
		if (par == null){
			throw new WrongGetMethod("Параметр "+parCd+" не существует в базе данных");
		} else if (par.getTp().equals("DT") && par.getDataTp().equals("SI")) {
			TaskPar ap = taskParDao.getTaskPar(task, parCd);
			if (ap!= null) {
				// сохранить значение
				ap.setD1(val);
			} else {
				// создать значение
				ap = new TaskPar(task, par, null, null, val);
				em.persist(ap);
			}
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип или тип данного");
		}
		return null;
	}
	
}