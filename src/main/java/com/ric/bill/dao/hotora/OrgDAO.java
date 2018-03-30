package com.ric.bill.dao.hotora;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ric.bill.model.hotora.oralv.Org;

/**
 * DAO сущности com.ric.bill.model.hotora.oralv.Org
 * @author Lev
 * @version 1.00
 *
 */
@Repository("OrgDAO_BWC_hotora")
public interface OrgDAO extends JpaRepository<Org, Integer> {

	/**
	 * Получить организацию коду REU
	 * @param reu - код REU
	 * @return
	 */
	@Query("select t from com.ric.bill.model.hotora.oralv.Org t "
			+ "where t.reu = ?1")
	  List<Org> getByReu(String reu);

	/**
	 * Получить организацию CD
	 * @param reu - код CD
	 * @return
	 */
	@Query("select t from com.ric.bill.model.hotora.oralv.Org t "
			+ "where t.cd = ?1")
	  Org getByCD(String cd);
	
}
