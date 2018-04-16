package com.ric.bill;

import java.util.ArrayList;
import java.util.List;

import com.ric.bill.model.ps.Pers;

import lombok.extern.slf4j.Slf4j;

/**
 * Хранение кол-ва проживающих, записи о их регистрациях
 * @author lev
 *
 */
@Slf4j
public class CntPers implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public int cnt; // кол-во проживающих для определения соцнормы/свыше
	public int cntVol; // кол-во проживающих для определения объема
	public int cntEmpt; // кол-во проживающих для анализа пустых квартир
	public int cntFact; // кол-во проживающих фактическое (без виртуальных +1 собственников, если никто не прописан)
	public int cntOwn; // кол-во собственников
	public List <Pers> persLst; // список проживающих, для расчета начисления по соцнормам (на базе cnt)
	
	public void setUp() {
		persLst = new ArrayList<Pers>(0);
		cnt = 0;
		cntVol = 0; 
		cntEmpt = 0; 
		cntFact = 0; 
		cntOwn = 0; 
	}
	
}
