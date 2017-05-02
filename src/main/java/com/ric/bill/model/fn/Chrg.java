package com.ric.bill.model.fn;


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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ric.bill.Simple;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.mt.MeterLog;
import com.ric.bill.model.tr.Serv;

/**
 * Результат начисления
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CHRG", schema="FN")
public class Chrg implements java.io.Serializable, Simple {


	public Chrg() {
		
	}
			
	// конструктор для окончательно рассчитанных данных (умноженная расценка на объем)
	public Chrg(Kart kart, Serv serv, Org org, int status, String period,
			BigDecimal sumFull, BigDecimal sumAmnt, BigDecimal vol,
			BigDecimal price, BigDecimal stdt, Integer cntPers, BigDecimal area, Lst tp, 
			Chng chng, Integer met, Date dt1, Date dt2) {
		
		setKart(kart);
		setOrg(org);
		setServ(serv);
		setStatus(status);
		setPeriod(period);
		setSumFull(sumFull.doubleValue());
		setSumAmnt(sumAmnt.doubleValue());
		setVol(vol.doubleValue());
		setPrice(price.doubleValue());
		if (stdt != null) {
			setStdt(stdt.doubleValue());
		}
		setCntPers(cntPers);
		setTp(tp);
		if (area != null) {
			setArea(area.doubleValue());
		}
		setDt1(dt1);
		setDt2(dt2);
		setChng(chng);
		setMet(met);
	}

	// конструктор для подготовительных данных, рассчитанных в потоке
	public Chrg(Kart kart, Serv serv, Org org, int status, String period,
			Double sumFull, Double sumAmnt, Double vol,
			Double price, Double stdt, Integer cntPers, Double area, Lst tp, Date dt1, Date dt2, Integer met, Chng chng) {
		
		setKart(kart);
		setOrg(org);
		setServ(serv);
		setStatus(status);
		setPeriod(period);
		setSumFull(sumFull);
		setSumAmnt(sumAmnt);
		setVol(vol);
		setPrice(price);
		setStdt(stdt);
		setCntPers(cntPers);
		setTp(tp);
		setArea(area);
		setDt1(dt1);
		setDt2(dt2);
		setChng(chng);
		setMet(met);
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHRG")
	@SequenceGenerator(name="SEQ_CHRG", sequenceName="FN.SEQ_CHRG", allocationSize=10)	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)			
	private Integer id;
	
    //даты начала и окончания произведенного начисления
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false)
	private Kart kart;

	//Статус, 0 - архивная запись, 1-текущее начисление, 2 - подготовка к архиву
	@Column(name = "status", nullable = true)
	private Integer status;

	@Column(name = "LSK", updatable = false, insertable = false)
	private Integer lsk;

	@Column(name = "PERIOD")
	private String period;

	@Column(name = "SUM_FULL")
	private Double sumFull;
	
	@Column(name = "SUM_AMNT")
	private Double sumAmnt;

	@Column(name = "VOL")
	private Double vol;
	
	@Column(name = "PRICE")
	private Double price;

	@Column(name = "STDT")
	private Double stdt;
	
	// Кол-во проживающих на дату расчета
	@Column(name = "CNTPERS")
	private Integer cntPers; 

	// Площадь на дату расчета
	@Column(name = "AREA")
	private Double area;

	// Наличие счетчика на дату расчета: 1 - имеется, null, 0 - нет
	@Column(name = "MET")
	private Integer met;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHRG_TP", referencedColumnName="ID")
	private Lst tp; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID")
	private Serv serv; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
	private Org org; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID")
	private Chng chng; 
	
	public Integer getId() {
		return this.id;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getLsk() {
		return lsk;
	}

	public void setLsk(Integer lsk) {
		this.lsk = lsk;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Double getSumFull() {
		return sumFull;
	}

	public void setSumFull(Double sumFull) {
		this.sumFull = sumFull;
	}

	public Double getSumAmnt() {
		return sumAmnt;
	}

	public void setSumAmnt(Double sumAmnt) {
		this.sumAmnt = sumAmnt;
	}

	public Double getVol() {
		return vol;
	}

	public void setVol(Double vol) {
		this.vol = vol;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
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

	public Serv getServ() {
		return serv;
	}

	public void setServ(Serv serv) {
		this.serv = serv;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Double getStdt() {
		return stdt;
	}

	public void setStdt(Double stdt) {
		this.stdt = stdt;
	}

	public Integer getCntPers() {
		return cntPers;
	}

	public void setCntPers(Integer cntPers) {
		this.cntPers = cntPers;
	}

	public Chng getChng() {
		return chng;
	}

	public void setChng(Chng chng) {
		this.chng = chng;
	}

	public Double getArea() {
		return area;
	}

	public void setArea(Double area) {
		this.area = area;
	}

	public Integer getMet() {
		return met;
	}

	public void setMet(Integer met) {
		this.met = met;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Chrg))
	        return false;

	    Chrg other = (Chrg)o;

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

