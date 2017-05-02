package com.ric.bill.excp;

@SuppressWarnings("serial")
public class WrongGetMethod extends Exception {

	public WrongGetMethod(String message) {
        super(message);
    }
}
