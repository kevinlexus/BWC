package com.ric.bill.model.mt;


import java.math.BigDecimal;
import java.util.Date;

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
import com.ric.bill.model.bs.Lst;


/**
 * Связи счетчика
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "METER_LOG_GRAPH", schema="MT")
public class MeterLogGraph implements java.io.Serializable, Simple {

	public MeterLogGraph (){
		
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_TP", referencedColumnName="ID")
	private Lst tp; 

	//Узел - источник
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="NOD_SRC", referencedColumnName="ID")
	private MeterLog src; 

	//Узел - назначение
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="NOD_DST", referencedColumnName="ID")
	private MeterLog dst; 

    @Column(name = "PERCENT", updatable = false, nullable = true)
	private Double prc; 
	
	//даты начала и окончания действия
    @Column(name = "DT1", updatable = false, nullable = true)
	private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
	private Date dt2;
	
    public Integer getId() {
		return this.id;
	}
	
    public void setId(Integer id) {
		this.id = id;
	}

	public Lst getTp() {
		return tp;
	}

	public void setTp(Lst tp) {
		this.tp = tp;
	}

    public MeterLog getSrc() {
		return src;
	}

	public Double getPrc() {
		return prc;
	}
	
	public void setPrc(Double prc) {
		this.prc = prc;
	}

	public void setSrc(MeterLog src) {
		this.src = src;
	}

	public void setDst(MeterLog dst) {
		this.dst = dst;
	}

	public MLogs getDst() {
		return dst;
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

	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof MeterLogGraph))
	        return false;

	    MeterLogGraph other = (MeterLogGraph)o;

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

