package com.ric.bill.excp;

/**
 * Exception возникающий если задан пустой объект хранения
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class EmptyStorable  extends Exception {

	public EmptyStorable(String message) {
        super(message);
    }
}
