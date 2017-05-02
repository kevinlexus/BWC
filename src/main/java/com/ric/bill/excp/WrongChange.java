package com.ric.bill.excp;

/**
 * Возникает в случае некорректного перерасчета
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class WrongChange extends Exception {
	
	public WrongChange(String message) {
        super(message);
    }
	
}
