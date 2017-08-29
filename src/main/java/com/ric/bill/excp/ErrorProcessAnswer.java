package com.ric.bill.excp;

/**
 * Exception возникающий при ошибке обработки ответа от ГИС ЖКХ
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class ErrorProcessAnswer  extends Exception {

	public ErrorProcessAnswer(String message) {
        super(message);
    }
}
