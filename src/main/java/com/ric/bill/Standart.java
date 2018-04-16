package com.ric.bill;

/**
 * Норматив
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class Standart implements java.io.Serializable {
	
	public Double vol; // значение соцнормы
	public Double partVol; // доля соцнормы в контексте дня (умноженная на кол-во прожив)
	
}
