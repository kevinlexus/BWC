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

import com.ric.bill.dao.EolinkParDAO;
import com.ric.bill.dao.TaskParDAO;
import com.ric.bill.excp.WrongGetMethod;
import com.ric.bill.mm.ParMng;
import com.ric.bill.mm.TaskEolinkParMng;
import com.ric.bill.mm.TaskParMng;
import com.ric.bill.model.bs.Par;
import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.exs.EolinkPar;
import com.ric.bill.model.exs.Task;
import com.ric.bill.model.exs.TaskPar;


/**
 * Сервис совмещенного получения параметра. Поиска сперва в eolinkxpar, потом в taskxpar
 * @author lev
 *
 */
@Service
@Slf4j
public class TaskEolinkParMngImpl implements TaskEolinkParMng {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	@Autowired
	private ApplicationContext ctx;
	@Autowired
	private ParMng parMng;
	@Autowired
	private TaskParDAO taskParDao;
	@Autowired
	private EolinkParDAO eolinkParDao;

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
		} else if (par.getTp().equals("NM")) {
			TaskPar tpar = taskParDao.getTaskPar(task, parCd);
			if (tpar==null) {
				// не найдено в taskParDao, искать в eolinkParDao 
				EolinkPar epar = eolinkParDao.getEolinkPar(task.getEolink(), parCd);
				if (epar!= null) {
					return epar.getN1();
				}
			} else {
				// найдено в taskParDao
				return tpar.getN1();
			}
			
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
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
		} else if (par.getTp().equals("ST")) {
			TaskPar tpar = taskParDao.getTaskPar(task, parCd);
			if (tpar==null) {
				// не найдено в taskParDao, искать в eolinkParDao 
				EolinkPar epar = eolinkParDao.getEolinkPar(task.getEolink(), parCd);
				if (epar!= null) {
					return epar.getS1();
				}
			} else {
				// найдено в taskParDao
				return tpar.getS1();
			}
			
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
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
		} else if (par.getTp().equals("DT")) {
			TaskPar tpar = taskParDao.getTaskPar(task, parCd);
			if (tpar==null) {
				// не найдено в taskParDao, искать в eolinkParDao 
				EolinkPar epar = eolinkParDao.getEolinkPar(task.getEolink(), parCd);
				if (epar!= null) {
					return epar.getD1();
				}
			} else {
				// найдено в taskParDao
				return tpar.getD1();
			}
			
		} else {
			throw new WrongGetMethod("Параметр "+parCd+" имеет другой тип");
		}
		return null;
	}


	/**
	 * Переписать значения параметров из Task в Eolink, по завершению отправки в ГИС
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void acceptPar(Task task) {
		log.info("Перемещение параметров по task.id={}", task.getId());
		task.getTaskPar().stream().forEach(t-> {
			EolinkPar ep = task.getEolink().getEolinkPar().stream().filter(e-> e.getPar().equals(t.getPar())).findAny().orElse(null);
			if (ep==null) {
				//Параметра нет, создать
				ep = new EolinkPar(task.getEolink(), t.getPar(), t.getN1(), t.getS1(), t.getD1());
				em.persist(ep);
			} else {
				//Параметр есть, обновить
				if (ep.getPar().getTp().equals("NM")) {
					ep.setN1(t.getN1());
				} else if (ep.getPar().getTp().equals("ST")) {
					ep.setS1(t.getS1());
				} else if (ep.getPar().getTp().equals("DT")) {
					ep.setD1(t.getD1());
				}
			}
			
		});
		
	}

}