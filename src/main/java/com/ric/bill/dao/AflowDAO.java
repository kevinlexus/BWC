package com.ric.bill.dao;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ric.bill.model.exs.Ulist;
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
/*	@Query(value = "select new com.ric.bill.dto.SumChrgRec(s.ulist, sum(t.summa), sum(t.n1), min(t.n2)) from Aflow t "
			+ "join ServGis s with t.usl.id=s.usl "
			+ "join Ulist u with s.ulist.id=u.id " 
			+ "join UlistTp tp with u.ulistTp.id=tp.id "
			+ "where t.kart.lsk = ?1 and t.mg = ?2 "
			+ "and NVL(tp.eolink.id, ?4) = ?4 "
			+ "and t.type = ?3 "
			+ "and t.usl.id < '073' "
			+ "group by s.ulist")*/
/*	@Query(value = "select new com.ric.bill.dto.SumChrgRec(s2.ulist, sum(s2.summa), sum(s2.vol), sum(s2.price)) from ( "
			+ "select s.ulist, sum(t.summa) as summa, sum(t.n1) as vol, min(t.n2) as price, t.grp from Aflow t "
			+ "join ServGis s with t.usl.id=s.usl "
			+ "join Ulist u with s.ulist.id=u.id " 
			+ "join UlistTp tp with u.ulistTp.id=tp.id "
			+ "where t.kart.lsk = ?1 and t.mg = ?2 "
			+ "and NVL(tp.eolink.id, ?4) = ?4 "
			+ "and t.type = ?3 "
		//	+ "and t.usl.id < '073' "
			+ "group by s.ulist, t.grp) s2 "
			+ "group by s2.ulist", nativeQuery = true)*/
	/*@Query(value = "select new com.ric.bill.dto.SumChrgRec(s.ulist, sum(t.summa), sum(t.n1), min(t.n2)) from Aflow t "
			+ "join ServGis s with t.usl.id=s.usl "
			+ "join Ulist u with s.ulist.id=u.id " 
			+ "join UlistTp tp with u.ulistTp.id=tp.id "
			+ "where t.kart.lsk = ?1 and t.mg = ?2 "
			+ "and NVL(tp.eolink.id, ?4) = ?4 "
			+ "and t.type = ?3 "
			+ "and t.usl.id < '073' "
			+ "group by s.ulist")
	  List<SumChrgRec> getGrp(String lsk, String mg, Integer type, Integer orgId);*/

	
	/*@Query(value = "select 'u.id' as ss from scott.a_flow@hp t "
	+ "join exs.servgis s on t.fk_usl=s.fk_usl "
	+ "join exs.u_list u on s.fk_list=u.id " 
	+ "join exs.u_listtp tp on u.fk_listtp=tp.id "
	+ "where t.lsk = ?1 and t.mg = ?2 "
	+ "and NVL(tp.fk_eolink, ?4) = ?4 "
	+ "and t.fk_type = ?3 "
	//+ "and t.usl.id < '073' "
	+ "group by u.id", nativeQuery = true)*/

//	@Query(value = "select u.id as \"id\", "
			//+ "sum(t.summa) as \"summa\", sum(t.n1) as \"vol\", min(t.n2) as \"price\" "

	@Query(value = "select t2.id as \"ulistId\", sum(t2.summa) as \"summa\", sum(t2.vol) as \"vol\", sum(price) as \"price\" from ( "
			+ "select u.id, s.grp, sum(t.summa) as summa, sum(t.n1) as vol, min(t.n2) as price "
		+ "from scott.a_flow@hp t "
		+ "join exs.servgis s on t.fk_usl=s.fk_usl "
		+ "join exs.u_list u on s.fk_list=u.id " 
		+ "join exs.u_listtp tp on u.fk_listtp=tp.id "
		+ "where t.lsk = ?1 and t.mg = ?2 "
		+ "and NVL(tp.fk_eolink, ?4) = ?4 "
		+ "and t.fk_type = ?3 "
		//+ "and t.usl.id < '073' "
		+ "group by u.id, s.grp) t2 "
		+ "group by t2.id", nativeQuery = true)
	Collection<SumChrgRec> getGrp3(String lsk, String mg, Integer type, Integer orgId);

	static interface SumChrgRec {
		// Id услуги из ГИС 
		BigDecimal getUlistId(); 
		// сумма
		Double getSumma();
		// объем
		Double getVol();
		// цена
		Double getPrice();
	}
	
}
