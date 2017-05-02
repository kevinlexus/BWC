package com.ric.bill.model.sec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Immutable;

/**
 * Представление с правами пользователей
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Immutable
@Table(name = "V_SEC", schema="SEC")
@IdClass(VsecId.class)
public class Vsec implements java.io.Serializable {

    @Id
	@Column(name = "USERCD")
	private String userCd; // CD пользователя 

    @Id
    @Column(name = "KLSK")
	private Integer klsk; // Klsk объекта
    
    @Id
    @Column(name = "ROLECD")
	private String roleCd; // CD роли 
    
    @Id
    @Column(name = "ACTCD")
	private String actCd; // CD действия 

	public String getUserCd() {
		return userCd;
	}

	public void setUserCd(String userCd) {
		this.userCd = userCd;
	}

	public Integer getKlsk() {
		return klsk;
	}

	public void setKlsk(Integer klsk) {
		this.klsk = klsk;
	}

	public String getRoleCd() {
		return roleCd;
	}

	public void setRoleCd(String roleCd) {
		this.roleCd = roleCd;
	}

	public String getActCd() {
		return actCd;
	}

	public void setActCd(String actCd) {
		this.actCd = actCd;
	}

    
}

