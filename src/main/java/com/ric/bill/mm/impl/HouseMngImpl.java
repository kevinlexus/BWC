package com.ric.bill.mm.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ric.bill.dao.HouseDAO;
import com.ric.bill.dao.KwDAO;
import com.ric.bill.mm.HouseMng;
import com.ric.bill.model.ar.House;
import com.ric.bill.model.ar.HouseSite;
import com.ric.bill.model.ar.Kw;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.oralv.Ko;
//import com.dic.bill.dao.KartDAO;
import com.ric.cmn.Utl;

/**
 * Сервис обслуживания Дома
 * @author Leo
 * @version 1.01
 *
 */
@Service
public class HouseMngImpl implements HouseMng {


    @Autowired
	private HouseDAO hDao;
    @Autowired
	private KwDAO kwDao;
    //@Autowired
	//private KartDAO kartDao;

    /**
     * Получить дома по параметрам
     * @param houseId - Id дома
     * @param areaId - Id города
     * @param dt1 - дата начала периода
     * @param dt1 - дата окончания периода
     */
    @Override
    public synchronized List<House> findAll2(Integer houseId, Integer areaId, Integer tempLskId, Date dt1, Date dt2) {
		return hDao.findAll2(houseId, areaId, tempLskId, dt1, dt2);
	}

    /**
     * Получить обслуживающую УК по дому и дате
     */
    @Override
    public Org getUkByDt(House house, Date dt) {

    	Optional<HouseSite> site = house.getHouseSite().stream().filter(d -> Utl.between(dt, d.getDt1(), d.getDt2()))
		   .findFirst();
		if (site.isPresent()) {
			return site.get().getUk();
		} else {
			return null;
		}
    }

    /**
     * Получить Ko объект помещения, по Klsk дома и номеру помещения
     * @author Lev
     * @param houseKlsk - klsk дома
     * @param num - номер помещения
     */
	@Override
	public Ko getKoByKwNum(Integer houseKlsk, String num) {
		Kw kw = kwDao.getByNum(houseKlsk, num);

		if (kw != null) {
			return kw.getKo();
		} else {
			return null;
		}
	}

	/**
	 * Получить Ko объект помещения
	 * @param houseEol
	 * @param num
	 * @return
	 */
	@Override
	public Ko getPremisKo(Eolink houseEol, String num) {
		Ko premisKo = null;
		// получить Ko помещения
		if (houseEol.getKoObj()!=null) {
			premisKo = getKoByKwNum(houseEol.getKoObj().getId(), num);
		}
		return premisKo;
	}


}