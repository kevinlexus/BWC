package com.ric.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ric.bill.model.ar.Kw;

/**
 * DAO для Помещения
 * @author Lev
 * @version 1.00
 *
 */
@Repository
public interface KwDAO extends JpaRepository<Kw, Integer> {

	/**
	 * Найти Помещение по номеру и по klsk дома
	 * @param klskId - Klsk дома
	 * @param kw - номер помещения
	 * @return
	 */
	@Query("select t from Kw t where " 
			+ "t.house.klskId = ?1 and t.num=?2 ")
	 Kw getByNum(Integer klskId, String num);
	
}
