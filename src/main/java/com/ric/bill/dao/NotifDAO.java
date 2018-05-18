package com.ric.bill.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ric.bill.model.exs.Notif;

/**
 * DAO сущности Извещение об оплате - Notif
 * @author lev
 * @version 1.00
 *
 */
@Repository
public interface NotifDAO extends JpaRepository<Notif, Integer> {

	/**
	 * Получить список незагруженных Извещений в ГИС по Дому, по помещениям в подъездах
	 * отсортированно по номеру документа в биллинге
	 * @param house - дом
	 * @return
	 */
	@Query("select n from Pdoc p join p.notif n join p.eolink s " // лиц.счет
			+ "join s.parent k " // квартира
			+ "join k.parent e " // подъезд
			+ "join e.parent h on h.id=:eolinkId "  // дом
			+ "where (n.status=0 and n.v=1 or n.status=1 and n.v=0) " // либо добавленные, но не загруженные, либо отмененные и не отмененные в ГИС
			+ "order by p.cd")
	  List<Notif> getForLoadByHouseWithEntry(@Param("eolinkId") Integer eolinkId);

	/**
	 * Получить список незагруженных Извещений в ГИС по Дому, по помещениям без подъездов
	 * отсортированно по номеру документа в биллинге
	 * @param house - дом
	 * @return
	 */
	@Query("select n from Pdoc p join p.notif n join p.eolink s " // лиц.счет
			+ "join s.parent h on h.id=:eolinkId "  // дом
			+ "where (n.status=0 and n.v=1 or n.status=1 and n.v=0) " // либо добавленные, но не загруженные, либо отмененные и не отмененные в ГИС
			+ "order by p.cd")
	  List<Notif> getForLoadByHouseWOEntry(@Param("eolinkId") Integer eolinkId);

	/**
	 * Найти Извещение по TGUID
	 * @param tguid - транспортный GUID
	 * @return
	 */
	@Query("select p from Notif p "
			+ "where p.tguid = :tguid ")
	  Notif getByTGUID(@Param("tguid") String tguid);
}
