package com.ric.bill.model.sec;

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
import com.ric.bill.model.bs.AddrTp;
import com.ric.bill.model.bs.Lst;

/**
 * Роль
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ROLE", schema="SEC")
public class Act implements java.io.Serializable, Simple {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; // id

    @Column(name = "NAME")
	private String name; // Наименование 

    @Column(name = "CD")
	private String cd; // CD  

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_TP", referencedColumnName="ID")
	private Lst tp;  // тип роли
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_OBJTP_CD", referencedColumnName="CD")
	private AddrTp addrTp;  // тип адреса
    
	public Act() {
		super();
	}

	
	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public String getCd() {
		return cd;
	}



	public void setCd(String cd) {
		this.cd = cd;
	}



	public Lst getTp() {
		return tp;
	}



	public void setTp(Lst tp) {
		this.tp = tp;
	}



	public AddrTp getAddrTp() {
		return addrTp;
	}



	public void setAddrTp(AddrTp addrTp) {
		this.addrTp = addrTp;
	}


	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Act))
	        return false;

	    Act other = (Act)o;

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

