package com.ric.bill.excp;

/**
 * Exception возникающий если задана пустая услуга 
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class EmptyStorable  extends Exception {

	public EmptyStorable(String message) {
        super(message);
    }
}
