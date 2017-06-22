package com.ric.bill.dao.impl;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.TaskParDAO;
import com.ric.bill.model.exs.Task;
import com.ric.bill.model.exs.TaskPar;


@Repository
public class TaskParDAOImpl implements TaskParDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;
    
    /**
     * Получить параметр по ID задания и cd параметра
     * @param taskId - Id задания
     * @param parCd - Cd параметра
     */
	@Override
	public TaskPar getTaskPar(Task task, String parCd) {
		Query query =em.createQuery("select t from TaskPar t where t.task.id = :taskId and t.par.cd = :parCd");
		query.setParameter("taskId", task.getId());
		query.setParameter("parCd", parCd);
		try {
			return (TaskPar) query.getSingleResult();
		} catch (NoResultException e) {
		  return null;
		} 
	}

}
