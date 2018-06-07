package com.ric.bill.excp;

/**
 * Exception возникающий если счетчики (возможно) зациклены в графе
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class CyclicMeter  extends Exception {

	public CyclicMeter(String message) {
        super(message);
    }
}
