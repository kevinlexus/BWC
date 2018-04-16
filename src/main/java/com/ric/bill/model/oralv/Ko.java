package com.ric.bill.model.oralv;

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
import com.ric.bill.model.ar.House;
import com.ric.bill.model.bs.AddrTp;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.dc.Doc;

import lombok.Getter;
import lombok.Setter;

/**
 * Справочник всех объектов Klsk Objects - KO
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "K_LSK", schema="ORALV")
@Getter @Setter
public class Ko implements java.io.Serializable, Simple {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ADDRTP", referencedColumnName="ID")
	private AddrTp addrTp; 

	// Организация (На самом деле, здесь OneToOne, но не смог реализовать, оставил так)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID", referencedColumnName="FK_K_LSK", updatable = false, insertable = false)
	private Org org; 
	
	// Дом (На самом деле, здесь OneToOne, но не смог реализовать, оставил так)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID", referencedColumnName="FK_K_LSK", updatable = false, insertable = false)
	private House house; 

	// Документ (На самом деле, здесь OneToOne, но не смог реализовать, оставил так)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID", referencedColumnName="FK_K_LSK", updatable = false, insertable = false)
	private Doc doc; 

	// TODO можно добавить счетчик, и т.п.
	

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Ko))
	        return false;

	    Ko other = (Ko)o;

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

