package com.ric.bill.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import com.ric.bill.dao.TaskDAO;
import com.ric.bill.model.exs.Task;

import lombok.extern.slf4j.Slf4j;



/**
 * DAO сущности Task
 * @author lev
 * @version 1.00
 *
 */
@Slf4j
@Repository
public class TaskDAOImpl implements TaskDAO {

	@PersistenceContext
    private EntityManager em;

    //конструктор
    public TaskDAOImpl() {

    }

    /**
     * Вернуть список необработанных заданий, отсортированных по приоритету
     */
    @Override
	public List<Task> getAllUnprocessed() {
			Query query =em.createQuery("select t from Task t left join t.master d "
					+ "where t.state in ('INS','ACK','RPT') and t.parent is null "
					+ "and (t.master is null or t.master.state in ('ACP')) order by nvl(t.priority,0) desc, t.id");
			return query.getResultList();
	}

    /**
     * Вернуть список заданий определенного типа
     */
    @Override
	public List<Task> getByTp(String tp) {
			Query query =em.createQuery("select t from Task t where t.act.cd=:tp order by t.id");
			query.setParameter("tp", tp);
			return query.getResultList();
	}

    /**
     * Вернуть задание по CD
     */
    @Override
    @Cacheable(cacheNames="TaskDAOImpl.getByCd", key="{#cd }", unless = "#result == null")
    public Task getByCd(String cd) {
			Query query =em.createQuery("select t from Task t where t.cd=:cd");
			query.setParameter("cd", cd);
			try {
				return (Task) query.getSingleResult();
			} catch (javax.persistence.NoResultException e) {
				// не найден результат
				log.error("Не найдено задание по CD={}", cd);
				return null;
			}
	}

    /**
     * Вернуть список дочерних заданий по родительскому заданию, по определённому типу объектов
     * @param task - родительское задание
     * @param addrTp - уточняющий тип объекта
     * @param addrTp - тип объекта
     */
    @Override
	public List<Task> getByTaskAddrTp(Task task, String addrTp, String addrTpx, Integer appTp) {
    	Query query = null;
    	if (appTp.equals(0)) {
    		// "Квартплата"
    		if (addrTpx != null) {
    			// заполнен уточняющий тип
    			if (addrTp.equals("Документ")) {
        			query = em.createQuery("from Task t where t.parent.id = :parentId and t.eolink.objTp.cd = :addrTp and t.eolink.objTpx.cd = :addrTpx");
        			query.setParameter("parentId", task.getId());
        			query.setParameter("addrTp", addrTp);
        			query.setParameter("addrTpx", addrTpx);
    			} else {
    				// TODO: Прочие реализации
    			}
    		} else {
    			// не заполнен уточняющий тип
    			query =em.createQuery("from Task t where t.parent.id = :parentId and t.eolink.objTp.cd = :addrTp");
    			query.setParameter("parentId", task.getId());
    			query.setParameter("addrTp", addrTp);
    		}
    	} else {
    		// Новая разработка
    		if (addrTpx != null) {
    			// заполнен уточняющий тип
    			if (addrTp.equals("Документ")) {
    				// TODO: Не проверял запрос! возможно не будет работать, доделать его!
        			query =em.createQuery("select t from Task t join t.eolink e join e.koObj ko "
        					+ "where t.parent.id = :parentId and ko.addrTp.cd = :addrTp"
        					+ "and ko.doc.tp=:addrTpx");
        			query.setParameter("parentId", task.getId());
        			query.setParameter("addrTp", addrTp);
        			query.setParameter("addrTpx", addrTpx);
    			} else {
    				// TODO: Прочие реализации
    			}

    		} else {
    			// не заполнен уточняющий тип
    			query =em.createQuery("from Task t where t.parent.id = :parentId and t.eolink.objTp.cd = :addrTp");
    			query.setParameter("parentId", task.getId());
    			query.setParameter("addrTp", addrTp);
//    			log.info("parentId={}, addrTp={}", task.getId(), addrTp);
/*    			query =em.createQuery("select t from Task t join t.eolink e join e.koObj ko "
    					+ "where t.parent.id = :parentId and ko.addrTp.cd = :addrTp");
    			query.setParameter("parentId", task.getId());
    			query.setParameter("addrTp", addrTp);
*/    		}
    	}

		List<Task> lst;
		try {
			lst = query.getResultList();
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			// не найден результат
			return null;
		}

		return lst;
	}

	/**
	 * Вернуть задание по ID родительского задания и транспортному GUID
	 * @param - task - родительское задание
	 * @param - tguid - транспортный GUID
	 */
	@Override
	public Task getByTguid(Task task, String tguid) {
		Query query =em.createQuery("from Task t where (t.parent.id = :parentId or t.id = :parentId) and t.tguid = :tguid");
		query.setParameter("parentId", task.getId());
		query.setParameter("tguid", tguid);

		try {
			return (Task) query.getSingleResult();
		} catch (javax.persistence.NoResultException e) {
			// не найден результат
			log.error("Не найдено задание по TGUID={}", tguid);
			return null;
		}
	}

	/**
	 * Вернуть наличие ошибки или не выполнения в любом дочернем задании
	 * @param task - родительское задание
	 * @return - наличие ошибки
	 */
	@Override
	public Boolean getChildAnyErr(Task task) {
		Query query =em.createQuery("from Task t where t.parent.id = :parentId and t.state in ('ERR','INS') ");
		query.setParameter("parentId", task.getId());
		List<Task> lst;
		try {
			lst = query.getResultList();
			if (lst.size() !=0) {
				return true;
			} else {
				return false;
			}
		} catch (org.springframework.dao.EmptyResultDataAccessException e) {
			// не найден результат
			return false;
		}
	}


	/**
	 * Вернуть все дочерние задания по заданному
	 * @param task - родительское задание
	 * @return - дочерние задания
	 */
/*	public List<Task> getChildTask(Task task) {
		Query query =em.createQuery("from Task t where t.parent.id = :parentId");
		query.setParameter("parentId", task.getId());
		return query.getResultList();
	}*/
}
