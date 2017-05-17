package com.ric.bill.model.exs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.bs.Par;


/**
 * Параметры по действию 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PARXDEP", schema="EXS")
public class ParDep implements java.io.Serializable  {

	public ParDep() {
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// Действие из справочника
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ACT", referencedColumnName="ID")
	private Lst act;
	
	// Параметр, ассоциированный с ГИС ЖКХ
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PAR", referencedColumnName="ID")
	private Par par;

	// Взято из источника
	@Column(name = "PLACEFROM", updatable = true, nullable = true)
	private String placeFrom;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Par getPar() {
		return par;
	}

	public void setPar(Par par) {
		this.par = par;
	}

	public Lst getAct() {
		return act;
	}

	public void setAct(Lst act) {
		this.act = act;
	}

	public String getPlaceFrom() {
		return placeFrom;
	}

	public void setPlaceFrom(String placeFrom) {
		this.placeFrom = placeFrom;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof ParDep))
	        return false;

	    ParDep other = (ParDep)o;

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

