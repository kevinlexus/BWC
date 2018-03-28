package com.ric.bill.model.bs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Type;

import com.ric.bill.Storable;
import com.ric.bill.model.ar.Street;

import lombok.Getter;
import lombok.Setter;

/**
 * Организация
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ORG", schema="BS")
@Getter @Setter
public class Org extends Base implements java.io.Serializable, Storable {

	@Id
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// CD
	@Column(name = "CD", updatable = false, nullable = false)
	private String cd; 

	// наименование
    @Column(name = "NAME")
	private String name; 

	// ИНН
    @Column(name = "INN")
	private String inn;
    
	// БИК
    @Column(name = "BIK")
	private String bik;
    
	// расчетный счет
    @Column(name = "R_SCH")
	private String operAcc;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
	@BatchSize(size = 50)
	private List<OrgTp> orgTp = new ArrayList<OrgTp>(0);
    
	// Взято на обслуживание в новую программу
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "MNT", nullable = true)
	private Boolean isMnt;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID", updatable = false)
	private Org parent;
	
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

