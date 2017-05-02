package com.ric.bill.dto;


/**
 * DTO - KlskObj - любой объект, хранимый в  klsk
 * Этот класс введён для того чтобы можно было получать в методах проверки безопасности, список универсальных объектов с Id, Cd, Name
 * (например Org, Serv и т.п.) и использовать его в Web - интерфейсе
 * @author lev
 *
 */
public class KoDTO extends BaseDTO {

	private String addrTpCd; // Cd типа адреса 
	
	// конструктор
	public KoDTO(Integer id, String cd, String name, String addrTpCd) {
		super(id, cd, name);
		this.addrTpCd = addrTpCd;
	}

	public String getAddrTpCD() {
		return addrTpCd;
	}
	public void setAddrTpCD(String addrTpCd) {
		this.addrTpCd = addrTpCd;
	}
	
	
}
