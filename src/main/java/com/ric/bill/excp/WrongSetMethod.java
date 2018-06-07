package com.ric.bill.excp;

@SuppressWarnings("serial")
public class WrongSetMethod extends Exception {

	public WrongSetMethod(String message) {
        super(message);
    }
}
