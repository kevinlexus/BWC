package com.ric.bill.model.tr;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.ric.bill.Simple;
import com.ric.bill.model.fn.Chng;
import com.ric.bill.model.mt.MeterLog;


/**
 * Тариф по объекту
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TARIFXKLSK", schema="TR")
public class TarifKlsk implements java.io.Serializable, Simple {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_TARIF", referencedColumnName="FK_TARIF")
	//@Fetch(FetchMode.SUBSELECT)
	private List<TarifServProp> tarprop = new ArrayList<TarifServProp>(0);

    @Column(name = "FK_TARIF", updatable = false, nullable = false)
	private Integer fktarif;

    // Перерасчет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID", updatable = false, nullable = true)
	private Chng chng; 
    
    @Column(name = "ACT")
	private Integer act;

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

	public List<TarifServProp> getTarprop() {
		return tarprop;
	}

	public void setTarprop(List<TarifServProp> tarprop) {
		this.tarprop = tarprop;
	}

	public Integer getFktarif() {
		return fktarif;
	}

	public void setFktarif(Integer fktarif) {
		this.fktarif = fktarif;
	}

	public Chng getChng() {
		return chng;
	}

	public void setChng(Chng chng) {
		this.chng = chng;
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

	public Integer getAct() {
		return act;
	}

	public void setAct(Integer act) {
		this.act = act;
	}
	
}

