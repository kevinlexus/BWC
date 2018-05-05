package com.ric.bill.model.mt;


import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ric.bill.Simple;

import lombok.Getter;
import lombok.Setter;

/**
 * Статусы работы физического счетчика, процент поставки услуги 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "METER_EXS", schema="MT")
@Getter @Setter
public class MeterExs implements java.io.Serializable, Simple {

	public MeterExs (){
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id
	
	// NOT USE поставка объема услуги в этот период. 1- полная поставка,  и т.д. на убывание. 1 = 100 % Временно вернул 24.04.2018 TODO! Убрать!!!
    @Column(name = "PERCENT", updatable = false, nullable = true)
	private Double prc; 

    // статус счетчика 0- рабочий, 1- отключен,	2- неисправный, 3- не прошел поверку, 4- отказ допуска
    @Column(name = "TP", updatable = false, nullable = true)
	private Double tp; 

    // даты начала и окончания действия
    @Column(name = "DT1", updatable = false, nullable = true)
	private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
	private Date dt2;

    // физ.счетчик
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_METER", referencedColumnName="ID")
	private Meter meter ; 
    
}

