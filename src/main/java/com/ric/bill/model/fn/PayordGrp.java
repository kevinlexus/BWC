package com.ric.bill.model.fn;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.Simple;

/**
 * Группа платежного поручения
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PAYORD_GRP", schema="FN")
public class PayordGrp implements java.io.Serializable, Simple {


	public PayordGrp() {
		
	}

	public PayordGrp(String name) {
		super();
		this.name = name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAYORD_GRP")
	@SequenceGenerator(name="SEQ_PAYORD_GRP", sequenceName="FN.SEQ_PAYORD_GRP", allocationSize=1) 	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)					
	private Integer id;

	// Наименование
	@Column(name = "NAME")
	private String name;

	// Пользователь
	@Column(name = "USERNAME", insertable = false, updatable = false)
	private String username;
	
	// Дата создания
	@Column(name = "DTF", insertable = false, updatable = false)
	private Date dtf;
	
	// Платежки
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_PAYORD_GRP", referencedColumnName="ID", updatable = false)
	@Fetch(FetchMode.SUBSELECT)
	private List<Payord> payord = new ArrayList<Payord>(0);
	
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

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDtf() {
		return dtf;
	}

	public void setDtf(Date dtf) {
		this.dtf = dtf;
	}

	public List<Payord> getPayord() {
		return payord;
	}

	public void setPayord(List<Payord> payord) {
		this.payord = payord;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof PayordGrp))
	        return false;

	    PayordGrp other = (PayordGrp)o;

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

