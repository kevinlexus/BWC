package com.ric.bill;

/**
 * простейший интерфейс, геттеры и сеттеры
 * @author lev
 *
 */
public interface Simple {
	Integer getId();
	void setId(Integer id); //убрал отсюда ID из за проблем с торможением в классе kart, где ID - (lsk) - тип String //и опять вернул!))

}
