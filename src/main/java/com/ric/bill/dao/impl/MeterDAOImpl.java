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
import com.ric.bill.model.oralv.Ko;
import com.ric.bill.model.sec.User;
import com.ric.bill.model.tr.Serv;
import com.ric.cmn.Utl;


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
	 * Получить все исправные физические счетчики по действующим лиц.счетам дома (или всего фонда) и по услуге
	 * по которым не были переданы показания в данном периоде
	 * @param house - дом
	 * @param serv - услуга
	 * @param dt1 - начало периода
	 * @param dt2 - окончание периода
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MeterDTO> getAllWoVolMeterByHouseServ(House house, Serv serv, Date dt1, Date dt2) {
		Query query =em.createQuery("select new com.ric.bill.dto.MeterDTO(k.lsk, m.id, nvl(e.tp,0), 0) from Meter m "
			+ "join m.exs e "
			+ "join m.meterLog g "
			+ "join g.kart k on :dt2 between k.dt1 and k.dt2 " // рабочие лиц.сч. (по последней дате периода)
			+ "join g.serv s "
			+ "join k.kw kw "
			+ "join kw.house h "
			+ "where s.id = :servId "
			+ "and kw.house.id = decode(:houseId, -1, kw.house.id, :houseId) "
			+ "and :dt2 between e.dt1 and e.dt2 and nvl(e.tp,0) = 0 " // рабочие счетчики (по последней дате периода)
			+ "and not exists (select v from Vol v where v.met.id=m.id and v.vol1 > 0 "
			+ "and v.dt1 between :dt1 and :dt2 and v.dt2 between :dt1 and :dt2 "
			+ "and v.tp.cd='Фактический объем' and v.user.cd <> 'GEN') " // не передан объем, не учитывая автоначисление
			);
		query.setParameter("servId", serv.getId());
		if (house != null) {
			query.setParameter("houseId", house.getId());
		} else {
			query.setParameter("houseId", -1);
		}
		query.setParameter("dt1", dt1);
		query.setParameter("dt2", dt2);
		return query.getResultList();
	}


	/*
	 *
	 * Получить все физические счетчики по действующим лиц.счетам дома (или всего фонда) и по услуге
	 * которые неисправны (неповерены и т.п.)
	 * @param house - дом
	 * @param serv - услуга
	 * @param dt - дата на которую выбрать
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<MeterDTO> getAllBrokenMeterByHouseServ(House house, Serv serv, Date dt) {
		Query query =em.createQuery("select new com.ric.bill.dto.MeterDTO(k.lsk, m.id, nvl(e.tp,0), 1) from Meter m "
			+ "join m.exs e  "
			+ "join m.meterLog g "
			+ "join g.kart k on :dt between k.dt1 and k.dt2 " // рабочие лиц.сч. (по последней дате периода)
			+ "join g.serv s "
			+ "join k.kw kw "
			+ "join kw.house h "
			+ "where s.id = :servId "
			+ "and kw.house.id = decode(:houseId, -1, kw.house.id, :houseId) "
			+ "and :dt between e.dt1 and e.dt2 and nvl(e.tp,0) in (2,3,4) " // не исправный, не поверенный и т.п.
			+ "");
		query.setParameter("servId", serv.getId());
		if (house != null) {
			query.setParameter("houseId", house.getId());
		} else {
			query.setParameter("houseId", -1);
		}
		query.setParameter("dt", dt);
		return query.getResultList();
	}



/*	@SuppressWarnings("unchecked")
	@Override
	// TODO удалить позже, для тестов!
	public List<Meter> getXxx() {
		Query query =em.createQuery("select m from com.ric.bill.model.mt.Meter m "
			+ "join com.ric.bill.model.mt.MeterLog g on m.meterLog.id=g.id "
			+ "join com.ric.bill.model.ar.Kart k on k.id=g.kart.id");
		return query.getResultList();

	}

*/	/**
	 * Получить последнее переданное показание по физическому счетчику,
	 * в период его работоспособности, включая автоначисление, не включая текущий период
	 * @param meter - физ.счетчик
	 * @param dt1 - дата начала текущего периода
	 */
	@Override
	public Vol getLastVol(Meter meter, Date dt1) {
		Query query =em.createQuery("select v from Meter m join Vol v with m.id=v.met.id where v.id = "
				+ "(select max(o.id) from Meter t join Vol o "
				+ "with t.id=o.met.id "
				+ "join User u with o.user.id=u.id "
				+ "join t.exs e with t.id=e.meter.id "
				+ "where o.dt1 between e.dt1 and e.dt2 " // рабочие счетчики на дату отправки объема
				+ "and o.dt2 between e.dt1 and e.dt2 and nvl(e.tp,0) = 0 " // рабочие счетчики на дату отправки объема
				+ "and o.dt2 < :dt1 " // не включая текущий период
				+ "and t.id = :meterId) "
				+ "and m.id = :meterId");
		query.setParameter("dt1", dt1);
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
/*	@Override
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
*/
	/**
	 * Получить объем по физическому счетчику,
	 * включая автоначисление.
	 * Только положительные значения!!! > 0
	 * @param meter - физ.счетчик
	 * @param dt1 - начало периода
	 * @param dt2 - окончание периода
	 */
	@Override
	public Double getVolPeriod(Meter meter, Date dt1, Date dt2) {
		Double vol = meter.getVol().stream().filter(t-> Utl.between(t.getDt1(), dt1, dt2) && Utl.between(t.getDt2(), dt1, dt2))
							   .filter(t-> t.getVol1() > 0D).mapToDouble(t -> t.getVol1()).sum();

		return vol;
/*  Lev: Пока не удалять запрос, может понадобиться в будущем, если начнет тормозить на stream()
 * Query query =em.createQuery("select sum(v.vol1) from Meter m join m.vol v with m.id=v.met.id and v.vol1 > 0 "
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
		}*/
	}

	/**
	 * Получить объемы по физическим счетчикам,
	 * внесенные пользователем за период
	 * по дому (или всему фонду, если не заполнено)
	 * по услуге (или по всем услугам, если не заполнено)
	 * @param house - дом
	 * @param serv - услуга
	 * @param user - пользователь
	 * @param dt1 - начало периода
	 * @param dt2 - окончание периода
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<Vol> getVolPeriodByHouse(House house, Serv serv, User user, Date dt1, Date dt2) {
		Query query =em.createQuery("select v from Meter m "
				+ "join m.meterLog g "
				+ "join m.vol v on v.dt1 between :dt1 and :dt2 and v.dt2 between :dt1 and :dt2 "
				+ "join v.user u on u.cd = :userCd "
				+ "join g.kart k on :dt2 between k.dt1 and k.dt2 " // рабочие лиц.сч. (по последней дате периода)
				+ "join g.serv s on s.id = decode(:servId, -1, s.id, :servId) "
				+ "join k.kw kw "
				+ "join kw.house h "
				+ "where kw.house.id = decode(:houseId, -1, kw.house.id, :houseId) ");
			if (serv != null) {
				query.setParameter("servId", serv.getId());
			} else {
				query.setParameter("servId", -1);
			}
			query.setParameter("userCd", user.getCd());
			if (house != null) {
				query.setParameter("houseId", house.getId());
			} else {
				query.setParameter("houseId", -1);
			}
			query.setParameter("dt1", dt1);
			query.setParameter("dt2", dt2);
			return query.getResultList();
	}

	/**
     * Удалить объемы по физическим счетчикам принадлежащим лиц.счетам дома (фонда),
     * внесенные пользователем, по определенной услуге
     * @param house - дом
     * @param serv - услуга
     * @param user - пользователь
	 * @param dt1 - начало периода
	 * @param dt2 - окончание периода
     */
    @Override
    public void delHouseMeterVol(House house, Serv serv, User user, Date dt1, Date dt2) {

    	Query query = em.createNativeQuery("delete from mt.meter_vol t "+
    	    	"where exists "+
    	    	"(select * from "+
    	    	" mt.meter m join mt.meter_log g on g.id=m.fk_meter_log and g.fk_serv=:servId "+
    	    	" join ar.kart k on g.fk_klsk_obj=k.fk_klsk_obj "+
    	    	" join ar.kw kw on k.fk_kw=kw.id and kw.fk_house=decode(:houseId, -1, kw.fk_house, :houseId) "+
    	    	" where m.id=t.fk_meter and "+
    	    	" t.fk_user=:userId "+
    	    	") "+
    	    	"and t.dt1 between :dt1 and :dt2 "+
    	    	"and t.dt2 between :dt1 and :dt2"
    			);
		query.setParameter("dt1", dt1);
		query.setParameter("dt2", dt2);
		if (house != null) {
			query.setParameter("houseId", house.getId());
		} else {
			query.setParameter("houseId", -1);
		}
		query.setParameter("servId", serv.getServMet());
		query.setParameter("userId", user.getId());
    	query.executeUpdate();
	}

    /**
     * Получить список Ko счетчиков по Ko лиц.счета, услуге и номеру счетчика
     * список - потому что неизвестно кол-во счетчиков с одним номером, по услуге и лиц.счету
     * @param koLsk - Ко лиц.счета
     * @param serv - услуга
     * @param num - номер счетчика
     */
	@SuppressWarnings("unchecked")
	@Override
	public List<Ko> getKoByLskNum(Ko koLsk, Serv serv, String num) {
		Query query =em.createQuery("select m.ko from Meter m "
				+ "join m.meterLog g "
				+ "join g.kart k "
				+ "join g.serv s "
				+ "where k.ko.id=:klskId and s.id=:servId and m.factoryNum=:num");
		query.setParameter("klskId", koLsk.getId());
		query.setParameter("servId", serv.getId());
		query.setParameter("num", num);
		return query.getResultList();
	}


    /*	@Override
    public List<User> testTransactDao() {
		Query query =em.createQuery("select t from User t ");
		return query.getResultList();
	}
*/
}
