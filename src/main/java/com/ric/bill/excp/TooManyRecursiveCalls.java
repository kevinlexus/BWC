package com.ric.bill.excp;

/**
 * Exception возникающий при рекурсивном вызове слишком много раз процедуры BillServ.distNode 
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class TooManyRecursiveCalls  extends Exception {

	public TooManyRecursiveCalls(String message) {
        super(message);
    }
}
