package com.ric.bill.model.cash;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ric.bill.Simple;
import com.ric.bill.model.bs.Org;

/**
 * Рабочие места
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "WP", schema="CASH")
public class Wp implements java.io.Serializable, Simple {


	public Wp() {
		
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)					
	private Integer id;

	// Организация осуществившая сбор
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_UK", referencedColumnName="ID")
	private Org org; 

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Wp))
	        return false;

	    Wp other = (Wp)o;

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

