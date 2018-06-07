package com.ric.bill.mm.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ric.bill.dao.NotifDAO;
import com.ric.bill.mm.NotifMng;
import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.exs.Notif;

import lombok.extern.slf4j.Slf4j;


@Service
@Slf4j
public class NotifMngImpl implements NotifMng {

	@Autowired
	private NotifDAO notifDao;

	/**
	 * Получить список незагруженных Извещений в ГИС по Дому, по всем помещениям
	 * отсортированно по номеру документа в биллинге
	 * @param house - дом
	 * @return
	 */
	@Override
	public List<Notif> getNotifForLoadByHouse(Eolink houseEol) {

		List<Notif> lst = notifDao.getForLoadByHouseWithEntry(houseEol.getId());

		lst.addAll(notifDao.getForLoadByHouseWOEntry(houseEol.getId()));

		return lst;
	}


}