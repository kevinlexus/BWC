package com.ric.bill.excp;

/**
 * Exception возникающий если необходимый параметр - пуст 
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class EmptyPar  extends Exception {

	public EmptyPar(String message) {
        super(message);
    }
}
