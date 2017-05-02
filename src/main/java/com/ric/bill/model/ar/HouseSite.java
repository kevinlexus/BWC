package com.ric.bill.model.ar;

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
import com.ric.bill.model.bs.Org;

/**
 * Принадлежность Дома к УК
 * 
 * @author lev
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "HOUSEXSITE", schema="AR")
public class HouseSite implements java.io.Serializable, Simple {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	// УК
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_UK", referencedColumnName="ID")
	private Org uk; 

	// УК
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
	private Org reu; 
	
	// Дом
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_HOUSE", referencedColumnName="ID")
	private House house; 
	
    // Даты начала и окончания присутствия Дома в УК
    @Column(name = "DT1", updatable = false, nullable = false)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = false)
    private Date dt2;
    
	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Org getUk() {
		return uk;
	}
	public void setUk(Org uk) {
		this.uk = uk;
	}
	public Org getReu() {
		return reu;
	}
	public void setReu(Org reu) {
		this.reu = reu;
	}
	public House getHouse() {
		return house;
	}
	public void setHouse(House house) {
		this.house = house;
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
	    if (o == null || !(o instanceof HouseSite))
	        return false;

	    HouseSite other = (HouseSite)o;

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

