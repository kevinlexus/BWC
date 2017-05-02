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
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.mt.Meter;
import com.ric.bill.model.mt.Vol;
import com.ric.bill.model.tr.Serv;

/**
 * Заголовочная таблица - перерасчеты
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CHNG", schema="FN")
public class Chng implements java.io.Serializable, Simple {


	public Chng() {
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<Chrg> chrg = new ArrayList<Chrg>(0);

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<ChngLsk> chngLsk = new ArrayList<ChngLsk>(0);
	
	// период (х.з. какой период, уточнить) TODO
	@Column(name = "PERIOD")
	private String period;

	// документ-основание перерасчета
	@Column(name = "ACT_NAME")
	private String actName;

	// даты начала и окончания произведенного перерасчета
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;
	
	// Тип перерасчета (Корректировка показаний ИПУ, Недопоставка услуги, Изменение расценки (тарифа))
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG_TP", referencedColumnName="ID")
	private Lst tp; 
	
	// Организация по перерасчету (для замены)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
	private Org org; 
	
	// Главная услугу по перерасчету
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID")
	private Serv serv; 
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

	public List<Chrg> getChrg() {
		return chrg;
	}

	public void setChrg(List<Chrg> chrg) {
		this.chrg = chrg;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
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

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public List<ChngLsk> getChngLsk() {
		return chngLsk;
	}

	public void setChngLsk(List<ChngLsk> chngLsk) {
		this.chngLsk = chngLsk;
	}

	public Lst getTp() {
		return tp;
	}

	public void setTp(Lst tp) {
		this.tp = tp;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Serv getServ() {
		return serv;
	}

	public void setServ(Serv serv) {
		this.serv = serv;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Chng))
	        return false;

	    Chng other = (Chng)o;

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

