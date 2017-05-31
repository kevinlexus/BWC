package com.ric.bill;


/**
 * Хранение кол-ва проживающих, записи о их регистрациях
 * @author lev
 *
 */
public class CntPers implements java.io.Serializable {
	
	public int cnt; // кол-во проживающих для определения соцнормы/свыше
	public int cntVol; // кол-во проживающих для определения объема
	public int cntEmpt; // кол-во проживающих для анализа пустых квартир
	public int cntFact; // кол-во проживающих фактическое (без виртуальных +1 собственников, если никто не прописан)
	public int cntOwn; // кол-во собственников
}
