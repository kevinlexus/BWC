package com.ric.bill.excp;

/**
 * Exception возникающий если не найдена услуга верхнего уровня 
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class NotFoundUpperLevel extends Exception {

	public NotFoundUpperLevel(String message) {
        super(message);
    }
}
