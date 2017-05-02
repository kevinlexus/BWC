package com.ric.bill.model.fn;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.ric.bill.Simple;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.tr.Serv;

/**
 * Движение по платежке
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PAYORD_FLOW", schema="FN")
public class PayordFlow implements java.io.Serializable, Simple {


	public PayordFlow() {
		
	}

	public PayordFlow(Payord payord, Org uk, Double summa, Double summa1,
			Double summa2, Double summa3, Double summa4, Double summa5, 
			Double summa6, Integer npp, Integer tp, String period, 
			Boolean signed, Boolean isFinal, Date dt) {
		super();
		this.payord = payord;
		this.uk = uk;
		this.summa = summa;
		this.summa1 = summa1;
		this.summa2 = summa2;
		this.summa3 = summa3;
		this.summa4 = summa4;
		this.summa5 = summa5;
		this.summa6 = summa6;
		this.tp = tp;
		this.period = period;
		this.npp = npp;
		this.signed = signed;
		this.isFinal = isFinal;
		this.dt = dt;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAYORD_FLOW")
	@SequenceGenerator(name="SEQ_PAYORD_FLOW", sequenceName="FN.SEQ_PAYORD_FLOW", allocationSize=1) 	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)					
	private Integer id;

	// Платежка
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PAYORD", referencedColumnName="ID")
	private Payord payord; 
	
	// УК
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_UK", referencedColumnName="ID")
	private Org uk; 
	
	// Перечислено по факту (платежка)
	@Column(name = "SUMMA")
	private Double summa;

	// Сумма сборов, факт на день формир. (платежка)
	@Column(name = "SUMMA1")
	private Double summa1;

	// Сумма перечислений, факт на день формир. (платежка)
	@Column(name = "SUMMA2")
	private Double summa2;

	// Сумма корректировок, факт на день формир. (платежка)
	@Column(name = "SUMMA3")
	private Double summa3;

	// Сумма корректировок перечисл, факт на день формир. (платежка)
	@Column(name = "SUMMA4")
	private Double summa4;

	// Сумма удержаний, факт на день формир. (платежка)
	@Column(name = "SUMMA5")
	private Double summa5;

	// Сумма рекомендованная к перечислению (платежка)
	@Column(name = "SUMMA6")
	private Double summa6;

	// № п.п.
	@Column(name = "npp")
	private Integer npp;

	// Тип записи:  0-вх.сал., 1-вх.сал.Бух, 2-платежка, 3-корр.сборов, 4-корр.перечисл., 5- удержания
	@Column(name = "TP")
	private Integer tp;

	// Период
	@Column(name = "PERIOD")
	private String period;

	// Подпись платежки
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "SIGNED", nullable = true)
	private Boolean signed;

	// Итоговая ли платежка?
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "isFinal", nullable = true)
	private Boolean isFinal;
	
	//дата операции, Дата платежного поручения (платежка)
    @Column(name = "DT", updatable = false, nullable = true)
    private Date dt;
	
	public Double getSumma1() {
		return summa1;
	}

	public void setSumma1(Double summa1) {
		this.summa1 = summa1;
	}

	public Double getSumma2() {
		return summa2;
	}

	public void setSumma2(Double summa2) {
		this.summa2 = summa2;
	}

	public Double getSumma3() {
		return summa3;
	}

	public void setSumma3(Double summa3) {
		this.summa3 = summa3;
	}

	public Double getSumma4() {
		return summa4;
	}

	public void setSumma4(Double summa4) {
		this.summa4 = summa4;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Payord getPayord() {
		return payord;
	}

	public void setPayord(Payord payord) {
		this.payord = payord;
	}

	public Org getUk() {
		return uk;
	}

	public void setUk(Org uk) {
		this.uk = uk;
	}

	public Double getSumma() {
		return summa;
	}

	public void setSumma(Double summa) {
		this.summa = summa;
	}

	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public Integer getTp() {
		return tp;
	}

	public void setTp(Integer tp) {
		this.tp = tp;
	}
	
	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}


	public Double getSumma5() {
		return summa5;
	}

	public void setSumma5(Double summa5) {
		this.summa5 = summa5;
	}

	public Double getSumma6() {
		return summa6;
	}

	public void setSumma6(Double summa6) {
		this.summa6 = summa6;
	}

	public Integer getNpp() {
		return npp;
	}

	public void setNpp(Integer npp) {
		this.npp = npp;
	}

	public Boolean getSigned() {
		if (signed==null) {
			return false;
		} else {
			return signed;
		}
	}

	public void setSigned(Boolean signed) {
		this.signed = signed;
	}

	public Boolean getIsFinal() {
		if (isFinal==null) {
			return false;
		} else {
			return isFinal;
		}
	}

	public void setIsFinal(Boolean isFinal) {
		this.isFinal = isFinal;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof PayordFlow))
	        return false;

	    PayordFlow other = (PayordFlow)o;

	    if (getId() == other.getId()) return true;
	    if (getId() == null) return false;

	    // equivalence by id
	    return getId().equals(other.getId());
	}

	public int hashCode() {
	    if (getId() != null) {
	        return getId().hashCode();
	    } else {
	        return super.hashCode();
	    }
	}
	
}

