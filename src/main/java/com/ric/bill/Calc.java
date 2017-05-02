package com.ric.bill;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import com.ric.bill.model.ar.Area;
import com.ric.bill.model.ar.House;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.tr.Serv;

/**
 * Хранилище всех необходимых параметров для расчета
 * @author lev
 *
 */
public final class Calc {
	// рассчитываемая дата
	private Date genDt;
	// тип обработки расчёта
	private int calcTp;
    
	// текущий город
	private Area area; 
	// текущий дом (распределяемый, начисляемый)
	private House house;
	// текущий лиц.счет (распределяемый, начисляемый)
	private Kart kart; 
	// текущая услуга (распределяемая, начисляемая)
	private Serv serv; 

	// параметры из запроса
	private RequestConfig reqConfig;
	
	public Calc(RequestConfig reqConfig) {
		this.reqConfig = reqConfig; 
	}

	public Serv getServ() {
		return this.serv;
	}

	public void setServ(Serv serv) {
		this.serv = serv;
	}

	public int getCalcTp() {
		return this.calcTp;
	}

	public void setCalcTp(int calcTp) {
		this.calcTp = calcTp;
	}
	
	public Area getArea() {
		return this.area;
	}

	private void setArea(Area area) { //private!!!
		this.area = area;
	}

	public House getHouse() {
		return this.house;
	}

	public void setHouse(House house) {
		this.house = house;
		setArea(house.getStreet().getArea());
	}
	
	public void setKart(Kart kart) {
		this.kart = kart;
	}

	public Kart getKart() {
		return kart;
	}

	public void setGenDt(Date genDt) {
		this.genDt = genDt;
	}

	public Date getGenDt() {
		return this.genDt;
	}

	public RequestConfig getReqConfig() {
		return reqConfig;
	}

	public void setReqConfig(RequestConfig reqConfig) {
		this.reqConfig = reqConfig;
	}

}
