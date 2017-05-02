package com.ric.bill;

/**
 * Норматив
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class Standart implements java.io.Serializable {
	public Double vol; //значение норматива
	public Double partVol; //доля норматива в контексте дня (умноженная на кол-во прожив)
}
