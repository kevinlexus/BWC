package com.ric.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ric.bill.dto.SumChrgRec;
import com.ric.bill.model.hotora.scott.Aflow;

/**
 * Методы доступа к таблице начислений приложения типа appTp=0 (Старая разработка
 * @author Leo
 *
 */
@Repository
public interface AflowDAO extends JpaRepository<Aflow, Integer> {

	/**
	 * Получить сгруппированные записи начислений связанных с услугой из ГИС ЖКХ по лиц.счету и периоду
	 * @param lsk - лицевой счет
	 * @param mg - период
	 * @param type - тип записи 0 - начислено без учета льгот, 1 - начислено с учетом льгот
	 * @return
	 */
	@Query(value = "select new com.ric.bill.dto.SumChrgRec(s.ulist, sum(t.summa), sum(t.n1), min(t.n2)) from Aflow t "
			+ "join ServGis s with t.usl.id=s.usl "
			+ "join Ulist u with s.ulist.id=u.id " 
			+ "join UlistTp tp with u.ulistTp.id=tp.id "
			+ "where t.kart.lsk = ?1 and t.mg = ?2 "
			+ "and NVL(tp.eolink.id, ?4) = ?4 "
			+ "and t.type = ?3 "
			//+ "and t.usl.id < '073' "
			+ "group by s.ulist")
	  List<SumChrgRec> getGrp(String lsk, String mg, Integer type, Integer orgId);
	
}
