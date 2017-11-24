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
 * Тип платежей
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "V_TYPE_PAY", schema="CASH")
public class TypePay implements java.io.Serializable, Simple {


	public TypePay() {
		
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)					
	private Integer id;

	// CD
    @Column(name = "CD", updatable = false, nullable = false)					
	private String cd;

	// Name
    @Column(name = "NAME", updatable = false, nullable = false)					
	private String name;

    // Тип для платежек (null,0 - не учитывать, 1 - учитывать)
    @Column(name = "TP_PAYORD", updatable = false)					
	private Integer tpPayord;
    
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getTpPayord() {
		return tpPayord;
	}

	public void setTpPayord(Integer tpPayord) {
		this.tpPayord = tpPayord;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof TypePay))
	        return false;

	    TypePay other = (TypePay)o;

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

