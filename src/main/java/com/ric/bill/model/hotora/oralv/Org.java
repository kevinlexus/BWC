package com.ric.bill.model.hotora.oralv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ric.bill.model.ar.House;

import lombok.Getter;
import lombok.Setter;

/**
 * Организация из HOTORA
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_ORG@HP", schema="ORALV")
@Getter @Setter
public class Org implements java.io.Serializable {

	public Org() {
	}
	
	@Id
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// CD
	@Column(name = "CD", updatable = false, nullable = false)
	private String cd; 

	// наименование
    @Column(name = "NAME")
	private String name; 

	// код REU
    @Column(name = "REU")
	private String reu; 

	// код TREST
    @Column(name = "TREST")
	private String trest; 

    // контрагент Lev:30.03.2018 - неудается сделать OneToOne, сделал пока так 
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ID", referencedColumnName="FK_ORG", insertable = false, updatable = false, nullable = false)
	private SchetContragent schetContr; 
    
    @Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Org))
	        return false;

	    Org other = (Org)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	@Override
	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

}

