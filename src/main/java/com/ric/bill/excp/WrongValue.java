package com.ric.bill.excp;

/**
 * Exception возникающий если задана пустая услуга 
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class WrongValue  extends Exception {

	public WrongValue(String message) {
        super(message);
    }
}
