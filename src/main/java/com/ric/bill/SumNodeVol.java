package com.ric.bill;


/**
 * Объем по связанному узлу за период
 * @author lev
 *
 */
@SuppressWarnings("serial")
public class SumNodeVol implements java.io.Serializable {

	//значения за расчетный период:
	// Объем
	private Double vol; 
	// Площадь
	private Double area; 
	// Проживающие
	private Double pers; 
	// Лимит ОДН (где есть)
	private Double limit; 
	// Номер ввода
	private Integer entry; 
	
	public SumNodeVol(){
		setVol(new Double(0));
		setArea(new Double(0));
		setPers(new Double(0));
		setLimit(new Double(0));
		setEntry(null);
	}

	public Integer getEntry() {
		return entry;
	}

	public void setEntry(Integer entry) {
		this.entry = entry;
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
