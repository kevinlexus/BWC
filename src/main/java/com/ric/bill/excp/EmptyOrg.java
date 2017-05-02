package com.ric.bill.excp;

/**
 * Exception возникающий если задана пустая услуга 
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class EmptyOrg  extends Exception {

	public EmptyOrg(String message) {
        super(message);
    }
}
