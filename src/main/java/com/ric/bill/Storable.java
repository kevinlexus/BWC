package com.ric.bill;

import java.util.List;
import com.ric.bill.model.bs.Dw;
import com.ric.bill.model.oralv.Ko;

/**
 * Интерфейс - объект квартплаты
 * @author lev
 *
 */
public interface Storable  {
	//klsk объекта (в каждом подклассе переписать методы!)
	//Integer getKlskId();
	//void setKlskId(Integer klsk);
	Ko getKo();
	void setKo(Ko ko);
	public List<Dw> getDw();
	public void setDw(List<Dw> dw);
	
}
