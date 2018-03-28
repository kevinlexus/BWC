package com.ric.bill.model.hotora.scott;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ric.bill.model.fn.TempLskId;

import lombok.Getter;
import lombok.Setter;

/**
 * Таблица фактов A_FLOW, которая содержит начисления, перерасчеты, объемы и т.п. за определённый период
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SALDO@HP", schema="SCOTT")
@IdClass(SaldoId.class) // суррогатный первичный ключ
@Getter @Setter
public class Saldo implements java.io.Serializable  {

	public Saldo() {
	}

    // лицевой счет
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false)
	private Kart kart;

	 // код.услуги из USLM
	@Id
	@Column(name = "USLM", updatable = false)
	private String uslm;

	 // код.организации из SPRORG
	@Id
	@Column(name = "ORG", updatable = false)
	private Integer org;

	// период
	@Id
	@Column(name = "MG", updatable = false)
	private String mg;

	// Сумма
	@Column(name = "SUMMA", updatable = false)
	private Double summa;

}

