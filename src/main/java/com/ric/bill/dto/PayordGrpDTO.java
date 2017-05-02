package com.ric.bill.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.extern.slf4j.Slf4j;

/**
 * DTO - Группа платежных документов в элементах форм Web-интерфейса
 * @author lev
 *
 */
@Slf4j
public class PayordGrpDTO {

	// Id
	private Integer id;
	// Наименование
	private String name;
	// Пользователь
	private String username;
	// Дата создания
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "Asia/Krasnoyarsk")
	private Date dtf;
	

	// конструктор
	public PayordGrpDTO(Integer id, String name, String username, Date dtf) {
		super();
		this.id = id;
		this.name = name;
		this.username = username;
		this.dtf = dtf;
	}

	// Здесь важен default конструктор (иначе не примет обратно данные, методом POST)
	public PayordGrpDTO() {
		
	}
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDtf() {
		return dtf;
	}

	public void setDtf(Date dtf) {
		this.dtf = dtf;
	}

	
	
}
