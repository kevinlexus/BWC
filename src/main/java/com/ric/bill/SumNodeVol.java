package com.ric.bill;


/**
 * Объем по связанному узлу за период
 * @author lev
 *
 */
public class SumNodeVol implements java.io.Serializable {

	//значения за расчетный период:
	private Double vol; // объем
	private Double area; // площадь
	private Double pers; // проживающие
	private Double limit; // лимит ОДН (где есть)
	
	public SumNodeVol(){
		setVol(new Double(0));
		setArea(new Double(0));
		setPers(new Double(0));
		setLimit(new Double(0));
	}

	public Double getVol() {
		return vol;
	}

	public void setVol(Double vol) {
		this.vol = vol;
	}

	//добавить объем
	public void addVol(Double vol) {
		this.vol=this.vol+Utl.nvl(vol, 0d); 
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	//добавить площадь
	public void addArea(Double area) {
		this.area=this.area+Utl.nvl(area, 0d); 
	}

	public Double getPers() {
		return pers;
	}

	public void setPers(Double pers) {
		this.pers = pers;
	}
	
	//добавить кол-во проживающих
	public void addPers(Double pers) {
		this.pers=this.pers+Utl.nvl(pers, 0d); 
	}

	public Double getLimit() {
		return limit;
	}

	public void setLimit(Double limit) {
		this.limit = limit;
	}

}
