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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.Simple;
import com.ric.bill.model.ar.Area;

import lombok.Getter;
import lombok.Setter;

/**
 * Привилегия
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PRIVILEGE", schema="FN")
@Getter @Setter
public class Privilege implements java.io.Serializable, Simple {


	public Privilege() {
		
	}

	// ID
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// Закон
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_LAW", referencedColumnName="ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<Law> law = new ArrayList<Law>(0);

	// Город, область
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_AREA", referencedColumnName="ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<Area> area = new ArrayList<Area>(0);
	
	// Наименование
	@Column(name = "name")
	private String name;

	// Даты начала и окончания действия
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Privilege))
	        return false;

	    Privilege other = (Privilege)o;

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

