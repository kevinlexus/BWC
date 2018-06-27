package com.ric.bill.mm.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ric.bill.dao.TaskDAO;
import com.ric.bill.mm.TaskMng;
import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.exs.Task;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class TaskMngImpl implements TaskMng {

    @PersistenceContext
    private EntityManager em;
    @Autowired
    private TaskDAO taskDao;

    /**
     * Установить статус задания
     */
	@Override
    @Transactional
    public void setState(Task task, String state) {
    	Task foundTask = em.find(Task.class, task.getId());
		foundTask.setState(state);
	}

    /**
     * Установить результат задания
     */
	@Override
    @Transactional
    public void setResult(Task task, String result) {
    	Task foundTask = em.find(Task.class, task.getId());
		foundTask.setResult(result);
	}

    /**
     * Очистить результат в т.ч. дочерних заданий
     */
	@Override
    @Transactional
    public void clearAllResult(Task task) {
    	Task foundTask = em.find(Task.class, task.getId());
    	setResult(foundTask, null);
    	foundTask.getChild().stream().forEach(t-> {
    		setResult(t, null);
    	});
	}

	/**
	 * Установить идентификаторы объектов (если не заполненны)
	 * @param eolink - Объект
	 * @param guid - GUID, полученный от ГИС
	 * @param un - уникальный номер, полученный от ГИС
	 * @param status - статус
	 */
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void setEolinkIdf(Eolink eo, String guid, String un, Integer status) {
		if (eo.getGuid() == null) {
			eo.setGuid(guid);
		}
		if (eo.getUn() == null) {
			eo.setUn(un);
		}
		if (!eo.getStatus().equals(status) ) {
			eo.setStatus(status);
		}

	}

	/**
	 * Вернуть задание по ID родительского задания и транспортному GUID
	 * @param - task - родительское задание
	 * @param - tguid - транспортный GUID
	 */
	@Override
	public Task getByTguid(Task task, String tguid) {

		return taskDao.getByTguid(task, tguid);

	}

	/**
	 * Добавить в лог сообщение
	 * @param task - задание
	 * @param isStart - начало - окончание процесса
	 * @param isSucc - успешно / с ошибкой
	 */
	@Override
	public void logTask(Task task, boolean isStart, Boolean isSucc) {
		if (isSucc!=null) {
			log.info("******* Task.id={}, {}, {}, {}, {}",
					task.getId(), task.getAct().getName(), task.getState(),
					isStart?"Начало":"Окончание", isSucc?"Выполнено":"ОШИБКА");
		} else {
			log.info("******* Task.id={}, {}, {}",
					task.getId(), task.getAct().getName(), task.getState(),
					isStart?"Начало":"Окончание");
		}
	}


}