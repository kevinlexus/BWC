package com.ric.bill.model.mt;


import java.util.Date;

import javax.persistence.CascadeType;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.Simple;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.fn.Chng;

/**
 * Объемы счетчика 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "METER_VOL", schema="MT")
public class Vol implements java.io.Serializable, Simple {

	public Vol (){
		
	}

	public Vol (MeterLog ml, Lst tp, Double vol1, Double vol2, Date date, Date date2, Integer status, Integer statusVol){
		setMLog(ml);
		setTp(tp);
		setVol1(vol1);
		setVol2(vol2);
		setDt1(date);
		setDt2(date2);
		setStatus(status);
		setStatus(statusVol);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VOL")
	@SequenceGenerator(name="SEQ_VOL", sequenceName="MT.SEQ_METER_VOL", allocationSize=10) //делал allocationSize=100 тогда надо increment by делать 100, работает быстрее, на 10%.. но тогда гэп большой от других инсертов 	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)					
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK_TP", referencedColumnName="ID")
	private Lst tp; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_METER_LOG", referencedColumnName="ID")
	private MeterLog mLog; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_METER", referencedColumnName="ID")
	private Meter met; 

	@Column(name = "VOL1", updatable = true, nullable = true)
	private Double vol1; 

    @Column(name = "VOL2", updatable = true, nullable = true)
	private Double vol2; 

    // даты начала и окончания произведенного объема
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;

	// Статус, 0 - текущий расчет, 1 - перерасчет
	@Column(name = "STATUS", nullable = true)
	private Integer status;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID")
	private Chng chng; 

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getDt1() {
		return dt1;
	}

	public void setDt1(Date dt1) {
		this.dt1 = dt1;
	}

	public Date getDt2() {
		return dt2;
	}

	public void setDt2(Date dt2) {
		this.dt2 = dt2;
	}
	
	public Lst getTp() {
		return tp;
	}

	public void setTp(Lst tp) {
		this.tp = tp;
	}
	
	public Double getVol1() {
		return vol1;
	}
	
	public void setVol1(Double vol1) {
		this.vol1 = vol1;
	}
	public Double getVol2() {
		return vol2;
	}
	public void setVol2(Double vol2) {
		this.vol2 = vol2;
	}

	public MLogs getMLog() {
		return mLog;
	}

	public void setMLog(MeterLog ml) {
		this.mLog = ml;
	}

	public Meter getMet() {
		return met;
	}

	public void setMet(Meter met) {
		this.met = met;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Vol))
	        return false;

	    Vol other = (Vol)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

	
}

