package com.ric.bill.mm.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.ric.bill.Storable;
import com.ric.bill.Utl;
import com.ric.bill.dao.ParDAO;
import com.ric.bill.excp.EmptyServ;
import com.ric.bill.excp.EmptyStorable;
import com.ric.bill.excp.WrongGetMethod;
import com.ric.bill.excp.WrongSetMethod;
import com.ric.bill.mm.ParMng;
import com.ric.bill.model.bs.Dw;
import com.ric.bill.model.bs.Par;


@Service
@Slf4j
public class ParMngImpl implements ParMng {

	@Autowired
	private ApplicationContext ctx;

	@Autowired
	private ParDAO pDao;
	
	//получить параметр по его CD
	public Par getByCD(int rqn, String cd) {
		return pDao.getByCd(rqn, cd);
	}

	/**
	 * Узнать существует ли параметр по его CD
	 */
	@Cacheable(cacheNames="ParMngImpl.isExByCd", key="{#rqn, #cd }")
	public boolean isExByCd(int rqn, String cd) {
		Par p = getByCD(rqn, cd);
		if (p != null) {
			return true;
		} else {
			return false;
		}
	}


	@Cacheable(cacheNames="ParMngImpl.getBool1", key="{#rqn, #st.getKo().getId(), #cd, #genDt }")
	public Boolean getBool(int rqn, Storable st, String cd, Date genDt) throws EmptyStorable {
		if (st == null) {
			throw new EmptyStorable("Параметр st = null");
		}
		Par par = getByCD(rqn, cd);
		try {
			for (Dw d: st.getDw()) {
    			//по соотв.периоду
    			if (Utl.between(genDt, d.getDt1(), d.getDt2())) {
       				if (d.getPar().equals(par)) {
						if (d.getPar().getTp().equals("BL")) {
							if (d.getPar().getDataTp().equals("SI")) {
								if (d.getN1() == null) {
									return null;
								} else if (d.getN1()==1) {
									return true;
								} else {
									return false;
								}
							} else {
									throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом данного SI завершилась ошибкой");
							}
						} else {
							throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом BL завершилась ошибкой");
						}
					}
    			}
			}
			//если не найдено, то проверить, существует ли вообще этот параметр, в базе данных
			if (!isExByCd(rqn, cd)) {
				throw new WrongGetMethod("Параметр "+cd+" не существует в базе данных");
			};
		} catch (WrongGetMethod e) {
			e.printStackTrace();
		}
		return null;
	}

	@Cacheable(cacheNames="ParMngImpl.getBool2", key="{#rqn, #st.getKo().getId(), #cd}")
	public Boolean getBool(int rqn, Storable st, String cd) throws EmptyStorable {
		if (st == null) {
			throw new EmptyStorable("Параметр st = null");
		}
		Par par = getByCD(rqn, cd);
		try {
			for (Dw d: st.getDw()) {
    			//по соотв.периоду
       				if (d.getPar().equals(par)) {
						if (d.getPar().getTp().equals("BL")) {
							if (d.getPar().getDataTp().equals("SI")) {
								if (d.getN1() == null) {
									return null;
								} else if (d.getN1()==1) {
									return true;
								} else {
									return false;
								}
							} else {
									throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом данного SI завершилась ошибкой");
							}
						} else {
							throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом BL завершилась ошибкой");
						}
					}
			}
			//если не найдено, то проверить, существует ли вообще этот параметр, в базе данных
			if (!isExByCd(rqn, cd)) {
				throw new WrongGetMethod("Параметр "+cd+" не существует в базе данных");
			};
		} catch (WrongGetMethod e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * получить значение параметра типа Double объекта по CD свойства
	 * внимание! дату важно передавать, а не получать из Calc.getGenDt(), так как она влияет на кэш!
	 */
	@Cacheable(cacheNames="ParMngImpl.getDbl1", key="{#rqn, #st.getKo().getId(), #cd, #genDt }")
	public/* synchronized*/ Double getDbl(int rqn, Storable st, String cd, Date genDt) throws EmptyStorable {
		if (st == null) {
			throw new EmptyStorable("Параметр st = null");
		}
		Par par = getByCD(rqn, cd);
		try {
			for (Dw d: st.getDw()) {
    			//по соотв.периоду
    			if (Utl.between(genDt, d.getDt1(), d.getDt2())) {
					//проверка, что соответствует CD и Number (NM), Единичное (SI) - убрал - тормозит
    				/*if (checkPar(d.getFkHfp(), cd, "SI")) {
						return d.getN1();
    				}*/
    				//if (d.getPar().getCd().equals(cd)) {
       				if (d.getPar().equals(par)) {
						if (d.getPar().getTp().equals("NM")) {
							if (d.getPar().getDataTp().equals("SI")) {
								return d.getN1();
							} else {
									throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом данного SI завершилась ошибкой");
							}
						} else {
							throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом NM завершилась ошибкой");
						}
					}
    			}
			}
			//если не найдено, то проверить, существует ли вообще этот параметр, в базе данных
			if (!isExByCd(rqn, cd)) {
				throw new WrongGetMethod("Параметр "+cd+" не существует в базе данных");
			};
		} catch (WrongGetMethod e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * получить значение параметра типа Double объекта по CD свойства, без указания даты
	 * @throws EmptyServ 
	 */
	@Cacheable(cacheNames="ParMngImpl.getDbl2", key="{#rqn, #st.getKo().getId(), #cd }")
	public /*synchronized*/ Double getDbl(int rqn, Storable st, String cd) throws EmptyStorable {
		if (st == null) {
			throw new EmptyStorable("Параметр st = null");
		}
		Par par = getByCD(rqn, cd);
		try {
			for (Dw d: st.getDw()) {
				if (d.getPar().equals(par)) {
					if (d.getPar().getTp().equals("NM")) {
						if (d.getPar().getDataTp().equals("SI")) {
							return d.getN1();
						} else {
								throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом данного SI завершилась ошибкой");
						}
					} else {
						throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом NM завершилась ошибкой");
					}
				}
			}
			//если не найдено, то проверить, существует ли вообще этот параметр, в базе данных
			if (!isExByCd(rqn, cd)) {
				throw new WrongGetMethod("Параметр "+cd+" не существует в базе данных");
			};
		} catch (WrongGetMethod e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * получить значение параметра типа Double объекта по CD свойства, без указания даты
	 */
	@Cacheable(cacheNames="ParMngImpl.getDate", key="{#rqn, #st.getKo().getId(), #cd }")
	public /*synchronized*/ Date getDate(int rqn, Storable st, String cd) throws EmptyStorable {
		if (st == null) {
			throw new EmptyStorable("Параметр st = null");
		}
		Par par = getByCD(rqn, cd);
		try {
			for (Dw d: st.getDw()) {
				log.trace("ParMngImpl.getDate="+d.getPar().getCd());
				if (d.getPar().equals(par)) {
					if (d.getPar().getTp().equals("DT")) {
						if (d.getPar().getDataTp().equals("SI")) {
							return d.getDts1();
						} else {
								throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом данного SI завершилась ошибкой");
						}
					} else {
						throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом DT завершилась ошибкой");
					}
				}
			}
			//если не найдено, то проверить, существует ли вообще этот параметр, в базе данных
			if (!isExByCd(rqn, cd)) {
				throw new WrongGetMethod("Параметр "+cd+" не существует в базе данных");
			};
		} catch (WrongGetMethod e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Сохранить значение параметра - дату
	 * @param rqn - текущий RQN
	 * @param st - объект
	 * @param cd - код параметра
	 * @param dt - значение параметра
	 * @throws EmptyStorable 
	 * @throws WrongSetMethod 
	 */
	public void setDate(int rqn, Storable st, String cd, Date dt) throws EmptyStorable, WrongSetMethod {
		Boolean isSet=false;
		
		if (st == null) {
			throw new EmptyStorable("Параметр st = null");
		}
		Par par = getByCD(rqn, cd);
		for (Dw d: st.getDw()) {
			log.trace("ParMngImpl.getDate="+d.getPar().getCd());
			if (d.getPar().equals(par)) {
				if (d.getPar().getTp().equals("DT")) {
					if (d.getPar().getDataTp().equals("SI")) {
						d.setDts1(dt);
						isSet=true;
					} else {
							throw new WrongSetMethod("Попытка сохранить параметр "+cd+" не являющийся типом данного SI завершилась ошибкой");
					}
				} else {
					throw new WrongSetMethod("Попытка сохранить параметр "+cd+" не являющийся типом DT завершилась ошибкой");
				}
			}
		}
		
		if (!isSet) {
			throw new WrongSetMethod("Параметр "+cd+" не был установлен");
		}
	}

	/**
	 * получить значение параметра типа String объекта по CD свойства
	 * @throws EmptyStorable 
	 */
	@Cacheable(cacheNames="ParMngImpl.getStr1", key="{ #rqn, #st.getKo().getId(), #cd, #genDt }")
	public /*synchronized*/ String getStr(int rqn, Storable st, String cd, Date genDt) throws EmptyStorable {
		if (st == null) {
			throw new EmptyStorable("Параметр st = null");
		}
		Par par = getByCD(rqn, cd);
		try {
			for (Dw d: st.getDw()) {
    			//по соотв.периоду
    			if (Utl.between(genDt, d.getDt1(), d.getDt2())) {
					//проверка, что соответствует CD и Number (NM), Единичное (SI)
    				if (d.getPar().equals(par)) {
						if (d.getPar().getTp().equals("ST")) {
							if (d.getPar().getDataTp().equals("SI") || d.getPar().getDataTp().equals("CD")) {
								return d.getS1();
							} else {
									throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом данного SI или CD завершилась ошибкой");
							}
						} else {
							throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом ST завершилась ошибкой");
						}
					}
    			}
			}
			//если не найдено, то проверить, существует ли вообще этот параметр, в базе данных
			if (!isExByCd(rqn, cd)) {
				throw new WrongGetMethod("Параметр "+cd+" не существует в базе данных");
			};
		} catch (WrongGetMethod e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * получить значение параметра типа String объекта по CD свойства, без указания даты
	 * @throws EmptyStorable 
	 */
	@Cacheable(cacheNames="ParMngImpl.getStr2", key="{ #rqn, #st.getKo().getId(), #cd }")
	public /*synchronized*/ String getStr(int rqn, Storable st, String cd) throws EmptyStorable {
		if (st == null) {
			throw new EmptyStorable("Параметр st = null");
		}
		Par par = getByCD(rqn, cd);
		try {
			for (Dw d: st.getDw()) {
					//проверка, что соответствует CD и Number (NM), Единичное (SI)
				if (d.getPar().equals(par)) {
						if (d.getPar().getTp().equals("ST")) {
							if (d.getPar().getDataTp().equals("SI") || d.getPar().getDataTp().equals("CD")) {
								return d.getS1();
							} else {
									throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом данного SI или CD завершилась ошибкой");
							}
						} else {
							throw new WrongGetMethod("Попытка получить параметр "+cd+" не являющийся типом ST завершилась ошибкой");
						}
    			}
			}
			//если не найдено, то проверить, существует ли вообще этот параметр, в базе данных
			if (!isExByCd(rqn, cd)) {
				throw new WrongGetMethod("Параметр "+cd+" не существует в базе данных");
			};
		} catch (WrongGetMethod e) {
			e.printStackTrace();
		}
		return null;
	}
}