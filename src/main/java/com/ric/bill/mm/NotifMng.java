package com.ric.bill.mm;

import java.util.List;

import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.exs.Notif;

public interface NotifMng {

	public List<Notif> getNotifForLoadByHouse(Eolink houseEol);

}