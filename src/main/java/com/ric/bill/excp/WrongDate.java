package com.ric.bill.excp;

/**
 * Exception возникающий если задана некорректная дата
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class WrongDate  extends Exception {

	public WrongDate(String message) {
        super(message);
    }
}
