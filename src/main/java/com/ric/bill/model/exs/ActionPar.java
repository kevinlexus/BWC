package com.ric.bill.model.exs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import com.ric.bill.model.bs.Par;


/**
 * Параметры по действию 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ACTXPAR", schema="EXS")
public class ActionPar implements java.io.Serializable  {

	public ActionPar() {
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// Действие
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ACTION", referencedColumnName="ID")
	private Action action;
	
	// Параметр, ассоциированный с ГИС ЖКХ
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PAR", referencedColumnName="ID")
	private Par par;

	// Параметр типа Number
	@Column(name = "N1", updatable = true, nullable = true)
	private Double n1;
	
	// Параметр типа Varchar2
	@Column(name = "S1", updatable = true, nullable = true)
	private String s1;

	// Параметр типа Date
	@Column(name = "D1", updatable = true, nullable = true)
	private String d1;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}

	public Par getPar() {
		return par;
	}

	public void setPar(Par par) {
		this.par = par;
	}

	public Double getN1() {
		return n1;
	}

	public void setN1(Double n1) {
		this.n1 = n1;
	}

	public String getS1() {
		return s1;
	}

	public void setS1(String s1) {
		this.s1 = s1;
	}

	public String getD1() {
		return d1;
	}

	public void setD1(String d1) {
		this.d1 = d1;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof ActionPar))
	        return false;

	    ActionPar other = (ActionPar)o;

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

