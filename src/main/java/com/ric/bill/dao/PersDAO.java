package com.ric.bill.dao;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ric.bill.model.fn.PersPrivilege;
import com.ric.bill.model.fn.PrivilegeServ;
import com.ric.bill.model.ps.Pers;

@Repository
public interface PersDAO extends JpaRepository<Pers, Integer> {

	/**
	 * Получить список льгот по проживающему и услуге на дату
	 * отсортированно по наличию увеличения соц.нормы и по дисконту льготы
	 * @param persId - Id проживающего
	 * @param servId - Id услуги
	 * @param genDt - дата формирования
	 * @return
	 */
	@Query("select p from PersPrivilege p join PrivilegeServ s with p.privilege.id = s.privilege.id "
			+ "and p.pers.id = ?1 and ?3 between p.privilege.dt1 and p.privilege.dt2 "
			+ "where s.serv.id = ?2 and ?3 between p.dt1 and p.dt2 "
			+ "order by nvl(s.extSoc,0) desc, nvl(s.discount,0) desc")
	  List<PersPrivilege> getPersPrivilege(Integer persId, Integer servId, Date genDt);

	/**
	 * Получить Отношение привилегии к услуге
	 * @param privId - Id привилегии
	 * @param servId - Id услуги
	 * @param genDt - дата формирования
	 * @return
	 */
	@Query("select t from PrivilegeServ t where " 
			+ "t.privilege.id = ?1 and t.serv.id = ?2 ")
	 PrivilegeServ getPrivilegeServ(Integer privId, Integer servId);
	
}
