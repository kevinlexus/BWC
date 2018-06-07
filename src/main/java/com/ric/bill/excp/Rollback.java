package com.ric.bill.excp;

/**
 * Exception возникающий при откате транзакции
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class Rollback  extends Exception {

	public Rollback(String message) {
        super(message);
    }
}
