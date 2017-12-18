package com.ric.bill.model.fn;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.model.ps.Pers;

import lombok.Getter;
import lombok.Setter;

/**
 * Льгота проживающего
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PERSXPRIVILEGE", schema="FN")
@Getter @Setter
public class PersPrivilege implements java.io.Serializable {


	public PersPrivilege() {
		
	}

	// ID
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// Проживающий
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PERS", referencedColumnName="ID", updatable = false)
	private Pers pers;

	// Привилегия
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PRIVILEGE", referencedColumnName="ID", updatable = false)
	private Privilege privilege;
	
	// Документ - основание льготы
	@Column(name = "DOC_NAME")
	private String doc;

	// Даты начала и окончания льготы
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof PersPrivilege))
	        return false;

	    PersPrivilege other = (PersPrivilege)o;

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

