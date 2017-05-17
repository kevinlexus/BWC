package com.ric.bill.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.TaskParDAO;
import com.ric.bill.model.bs.Par;
import com.ric.bill.model.exs.Task;
import com.ric.bill.model.exs.TaskPar;


@Repository
public class TaskParDAOImpl implements TaskParDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Получить действие по ID и cd параметра
     * @param actionId - Id действия
     * @param parCd - Cd параметра
     */
	@Override
	public TaskPar getTask(Integer taskId, String parCd) {
		Query query =em.createQuery("select t from TaskPar t where t.task.id = :taskId and t.par.cd = :parCd");
		query.setParameter("taskId", taskId);
		query.setParameter("parCd", parCd);
		return (TaskPar) query.getSingleResult();
	}

}
