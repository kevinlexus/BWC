package com.ric.bill.model.hotora.scott;

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
@Table(name = "A_FLOW@HP", schema="SCOTT")
@IdClass(AflowId.class) // суррогатный первичный ключ
@Getter @Setter
public class Aflow implements java.io.Serializable  {

	public Aflow() {
	}

    // лицевой счет
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false)
	private Kart kart;

	 // код.услуги
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USL", referencedColumnName="USL", updatable = false)
	private Usl usl;

	// период
	@Id
	@Column(name = "MG", updatable = false)
	private String mg;

	// Сумма; (METER)Объем общий; (CENA_USL_LSK)Расценка; (NORMATIV_USL_LSK)Норма
	@Column(name = "SUMMA", updatable = false)
	private Double summa;

	// (CHRG)Месячный расход сч.; (VOLUME)Показание ХВС; (VOL_NORM)Норма ХВС; (METER)ID гр/сч.(кварп.)
	@Column(name = "N1", updatable = false)
	private Double n1;
	
	// (CHRG)Цена; (VOLUME)Показание ГВС; (VOL_NORM)Норма ГВС; (METER)Группа гр/сч.
	@Column(name = "N2", updatable = false)
	private Double n2;
	
	// тип записи: 0 - Начисление (полное, для насел.), 1 - Перерасчеты
	@Id
	@Column(name = "FK_TYPE", updatable = false)
	private Integer type;
	
}

