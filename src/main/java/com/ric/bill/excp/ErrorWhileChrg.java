package com.ric.bill.excp;

/**
 * Exception возникающий если произошла ошибка при выполнении начисления
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class ErrorWhileChrg  extends Exception {

	public ErrorWhileChrg(String message) {
        super(message);
    }
}
