package com.ric.bill;

import java.util.List;
import java.util.Set;

import com.ric.bill.model.bs.Dw;

/**
 * Интерфейс - объект квартплаты
 * @author lev
 *
 */
public interface Storable  {
	//klsk объекта (в каждом подклассе переписать методы!)
	Integer getKlskId();
	void setKlskId(Integer klsk);
	public List<Dw> getDw();
	public void setDw(List<Dw> dw);
	
}
