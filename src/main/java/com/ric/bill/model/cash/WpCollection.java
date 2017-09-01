package com.ric.bill.model.cash;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ric.bill.Simple;

/**
 * Инкассация
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "COLLECTION", schema="CASH")
public class WpCollection implements java.io.Serializable, Simple {


	public WpCollection() {
		
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)					
	private Integer id;

	// Дата инкассации
	@Column(name = "DT_CLOSE", updatable = false, nullable = true)
    private Date dtClose;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDtClose() {
		return dtClose;
	}

	public void setDtClose(Date dtClose) {
		this.dtClose = dtClose;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof WpCollection))
	        return false;

	    WpCollection other = (WpCollection)o;

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

