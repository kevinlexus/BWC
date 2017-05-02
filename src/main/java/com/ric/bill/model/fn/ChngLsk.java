package com.ric.bill.model.fn;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.ric.bill.Simple;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.mt.Meter;
import com.ric.bill.model.mt.Vol;
import com.ric.bill.model.tr.Serv;

/**
 * Детали перерасчета - лицевые счета
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CHNG_LSK", schema="FN")
public class ChngLsk implements java.io.Serializable, Simple {


	public ChngLsk() {
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

    // лиц.счет
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false)
	private Kart kart;
	
	// заголовок перерасчета
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID", updatable = false)
	private Chng chng;
	
	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID", updatable = false)
	private Serv serv;
	
	// начисление 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHRG", referencedColumnName="ID", updatable = false)
	private Chrg chrg;

	// результат перерасчета, в объеме
	@Column(name = "VOL_CHNG")
	private Double volChng;
	
	// результат перерасчета, в денежном выражении
	@Column(name = "SUM_CHNG")
	private Double sumChng;

	// результат перерасчета, после корректировки
	@Column(name = "SUM_CORR")
	private Double sumCorr;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG_LSK", referencedColumnName="ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<ChngVal> chngVal = new ArrayList<ChngVal>(0);

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Kart getKart() {
		return kart;
	}

	public void setKart(Kart kart) {
		this.kart = kart;
	}

	public Chng getChng() {
		return chng;
	}

	public void setChng(Chng chng) {
		this.chng = chng;
	}

	public Serv getServ() {
		return serv;
	}

	public void setServ(Serv serv) {
		this.serv = serv;
	}

	public Chrg getChrg() {
		return chrg;
	}

	public void setChrg(Chrg chrg) {
		this.chrg = chrg;
	}

	public Double getVolChng() {
		return volChng;
	}

	public void setVolChng(Double volChng) {
		this.volChng = volChng;
	}

	public Double getSumChng() {
		return sumChng;
	}

	public void setSumChng(Double sumChng) {
		this.sumChng = sumChng;
	}

	public Double getSumCorr() {
		return sumCorr;
	}

	public void setSumCorr(Double sumCorr) {
		this.sumCorr = sumCorr;
	}

	public List<ChngVal> getChngVal() {
		return chngVal;
	}

	public void setChngVal(List<ChngVal> chngVal) {
		this.chngVal = chngVal;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof ChngLsk))
	        return false;

	    ChngLsk other = (ChngLsk)o;

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

