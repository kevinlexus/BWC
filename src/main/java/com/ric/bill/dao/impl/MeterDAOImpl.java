package com.ric.bill.dao.impl;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.ric.bill.dao.MeterDAO;
import com.ric.bill.dto.MeterDTO;
import com.ric.bill.model.ar.House;
import com.ric.bill.model.mt.Meter;
import com.ric.bill.model.mt.Vol;
import com.ric.bill.model.tr.Serv;


/**
 * DAO сущности Meter
 * @version 1.01
 * @author lev
 *
 */
@Repository
public class MeterDAOImpl implements MeterDAO {

	//EntityManager - EM нужен на каждый DAO или сервис свой!
    @PersistenceContext
    private EntityManager em;

	/* 
	 * Пример, как правильно выполнять запрос JPQL по связанным Enities
	 * 
	 * Получить все исправные физические счетчики по действующим лиц.счетам дома и по услуге
	 * по которым не были переданы показания в данном периоде 
	 * @param house - дом
	 * @param serv - услуга
	 * @param dt1 - начало периода
	 * @param dt2 - окончание периода
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MeterDTO> getAllWoVolMeterByHouseServ(House house, Serv serv, Date dt1, Date dt2) {
		Query query =em.createQuery("select new com.ric.bill.dto.MeterDTO(m, nvl(e.tp,0)) from Meter m "
			+ "join m.exs e with m.id=e.meter.id "
			+ "join m.meterLog g with m.meterLog.id=g.id "
			+ "join g.kart k with g.kart.id=k.id and :dt2 between k.dt1 and k.dt2 " // рабочие лиц.сч. (по последней дате периода)
			+ "join g.serv s with g.serv.id=s.id "
			+ "join k.kw kw with k.kw.id=kw.id "
			+ "join kw.house h with kw.house.id=h.id "
			+ "where s.id = :servId "
			+ "and kw.house.id = :houseId "
			+ "and :dt2 between e.dt1 and e.dt2 and nvl(e.tp,0) = 0 " // рабочие счетчики (по последней дате периода)
			+ "and not exists (select v from Vol v where v.met.id=m.id and v.vol1 > 0 "
			+ "and v.dt1 between :dt1 and :dt2 and v.dt2 between :dt1 and :dt2 "
			+ "and v.tp.cd='Фактический объем' and v.user.cd <> 'GEN') " // не передан объем, не учитывая автоначисление 
			);
		query.setParameter("servId", serv.getId());
		query.setParameter("houseId", house.getId());
		query.setParameter("dt1", dt1);
		query.setParameter("dt2", dt2);
		return query.getResultList();
	}

	
	/* 
	 * 
	 * Получить все физические счетчики по действующим лиц.счетам дома и по услуге
	 * которые неисправны (неповерены и т.п.) 
	 * @param house - дом
	 * @param serv - услуга
	 * @param dt - дата на которую выбрать
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MeterDTO> getAllBrokenMeterByHouseServ(House house, Serv serv, Date dt) {
		Query query =em.createQuery("select new com.ric.bill.dto.MeterDTO(m, nvl(e.tp,0)) from Meter m "
			+ "join m.exs e with m.id=e.meter.id "
			+ "join m.meterLog g with m.meterLog.id=g.id "
			+ "join g.kart k with g.kart.id=k.id and :dt between k.dt1 and k.dt2 " // рабочие лиц.сч. (по последней дате периода)
			+ "join g.serv s with g.serv.id=s.id "
			+ "join k.kw kw with k.kw.id=kw.id "
			+ "join kw.house h with kw.house.id=h.id "
			+ "where s.id = :servId "
			+ "and kw.house.id = :houseId "
			+ "and :dt between e.dt1 and e.dt2 and nvl(e.tp,0) in (2,3,4) " // не исправный, не поверенный и т.п.
			+ "");
		query.setParameter("servId", serv.getId());
		query.setParameter("houseId", house.getId());
		query.setParameter("dt", dt);
		return query.getResultList();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	// TODO удалить позже, для тестов!
	public List<Meter> getXxx() {
		Query query =em.createQuery("select m from com.ric.bill.model.mt.Meter m "
			+ "join com.ric.bill.model.mt.MeterLog g on m.meterLog.id=g.id "
			+ "join com.ric.bill.model.ar.Kart k on k.id=g.kart.id");
		return query.getResultList();
		
	}

	/** 
	 * Получить последнее переданное показание по физическому счетчику,
	 * в период его работоспособности, включая автоначисление
	 * @param meter - физ.счетчик
	 */
	@Override
	public Vol getLastVol(Meter meter) {
		Query query =em.createQuery("select v from Meter m join Vol v with m.id=v.met.id where v.id = "
				+ "(select max(o.id) from Meter t join Vol o "
				+ "with t.id=o.met.id "
				+ "join User u with o.user.id=u.id "
				+ "join t.exs e with t.id=e.meter.id "
				+ "where o.dt1 between e.dt1 and e.dt2 " // рабочие счетчики на дату отправки объема
				+ "and o.dt2 between e.dt1 and e.dt2 and nvl(e.tp,0) = 0 " // рабочие счетчики на дату отправки объема
				+ "and t.id = :meterId) "
				+ "and m.id = :meterId");
		query.setParameter("meterId", meter.getId());
		try {
			return (Vol) query.getSingleResult();
		} catch (NoResultException e) {
			  return null;
		} 
	}

	/** 
	 * Получить объем по физическому счетчику,
	 * в за период dt1-dt2, включая автоначисление. 
	 * Только положительные значения!!! > 0
	 * @param meter - физ.счетчик
	 * @param dt1 - начало периода
	 * @param dt2 - окончание периода
	 */
	@Override
	public Double getVolPeriod(Meter meter, Date dt1, Date dt2) {
		Query query =em.createQuery("select sum(v.vol1) from Meter m join m.vol v with m.id=v.met.id and v.vol1 > 0 " 
				+ "where v.dt1 between :dt1 and :dt2 "
				+ "and v.dt2 between :dt1 and :dt2  "
				+ "and m.id = :meterId");
		query.setParameter("meterId", meter.getId());
		query.setParameter("dt1", dt1);
		query.setParameter("dt2", dt2);
		try {
			return (Double) query.getSingleResult();
		} catch (NoResultException e) {
			  return null;
		} 
	}

	
}
