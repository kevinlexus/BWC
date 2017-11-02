package com.ric.bill.excp;

/**
 * Возникает в случае некорректного выражения, которое надо преобразовать
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class WrongExpression extends Exception {
	
	public WrongExpression(String message) {
        super(message);
    }
	
}
