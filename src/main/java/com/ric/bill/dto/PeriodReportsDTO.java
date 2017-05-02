package com.ric.bill.dto;

/**
 * DTO - Периоды отчета для отображения в элементах форм Web-интерфейса
 * @author lev
 *
 */
public class PeriodReportsDTO {

	// конструктор
	public PeriodReportsDTO(Integer id, String period) {
		this.id = id;
		this.period = period;
	}
	
	// Id
	private Integer id;
	
	// Период
	private String period;

	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getPeriod() {
		return period;
	}
	
	public void setPeriod(String period) {
		this.period = period;
	}
	
	
}
