package com.ric.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ric.bill.dto.SumChrgRec;
import com.ric.bill.model.fn.Chrg;

/**
 * DAO сущности Chrg - начисление
 * @author Lev
 * @version 1.00
 *
 */
@Repository
public interface ChrgDAO extends JpaRepository<Chrg, Integer> {

	/**
	 * Получить сгруппированные записи начислений (полного начисления, без учета льгот), 
	 * связанных с услугой из ГИС ЖКХ по лиц.счету и периоду
	 * @param lsk - лицевой счет
	 * @param mg - период
	 * @return
	 */
	@Query(value = "select t2.id as \"ulistId\", sum(t2.summa) as \"summa\", sum(t2.vol) as \"vol\", sum(price) as \"price\" from ( "
			+ "select u.id, s.grp, sum(t.sum_full) as summa, sum(t.vol) as vol, min(t.price) as price "
		+ "from fn.chrg t "
		+ "join ar.kart k on k.lsk=t.lsk and k.fk_klsk_obj = ?1 "
		+ "join exs.servgis s on t.fk_serv=s.fk_serv "
		+ "join exs.u_list u on s.fk_list=u.id " 
		+ "join exs.u_listtp tp on u.fk_listtp=tp.id "
		+ "where t.lsk = ?1 and t.period = ?2 "
		+ "and NVL(tp.fk_eolink, ?3) = ?3 "
		+ "and t.status = 1 "
		+ "group by u.id, s.grp) t2 "
		+ "group by t2.id", nativeQuery = true)
	List<SumChrgRec> getChrgGrp(Integer klskId, Integer period, Integer orgId);

	
}
