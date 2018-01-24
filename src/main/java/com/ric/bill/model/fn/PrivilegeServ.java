package com.ric.bill.model.fn;


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
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;

/**
 * Привилегия по услуге
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PRIVILEGEXSERV", schema="FN")
@Getter @Setter
public class PrivilegeServ implements java.io.Serializable, Simple {

	public PrivilegeServ() {
		
	}

	// ID
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// Дисконт (коэфф.)
	@Column(name = "DISCOUNT", updatable = true, nullable = true)
	private Double discount; 
	
	// Тип расчета
	@Column(name = "TP", updatable = true, nullable = true)
	private Integer tp; 

	// Увеличение соцнормы (объем)
	@Column(name = "EXT_SOC", updatable = true, nullable = true)
	private Double extSoc; 

	// Привилегия
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PRIVILEGE", referencedColumnName="ID")
	private Privilege privilege; 
	
	// Услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID")
	private Serv serv; 

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof PrivilegeServ))
	        return false;

	    PrivilegeServ other = (PrivilegeServ)o;

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

