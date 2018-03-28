package com.ric.bill.model.hotora.oralv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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

    // услуга из Новой разработки
    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
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

