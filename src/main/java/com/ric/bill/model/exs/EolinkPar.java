package com.ric.bill.model.exs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.ric.bill.model.bs.Par;


/**
 * Параметры по связанному объекту
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EOLXPAR", schema="EXS")
public class EolinkPar implements java.io.Serializable  {

	// Конструктор
	public EolinkPar() {
	}

	// Конструктор
	public EolinkPar(Eolink eolink, Par par, Double n1, String s1, Date d1) {
		super();
		this.eolink = eolink;
		this.par = par;
		this.n1 = n1;
		this.s1 = s1;
		this.d1 = d1;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EOLINKPAR")
	@SequenceGenerator(name="SEQ_EOLINKPAR", sequenceName="EXS.SEQ_EOLXPAR", allocationSize=1)	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// Внешний объект
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private Eolink eolink;
	
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
	private Date d1;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public Eolink getEolink() {
		return eolink;
	}

	public void setEolink(Eolink eolink) {
		this.eolink = eolink;
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

	public Date getD1() {
		return d1;
	}

	public void setD1(Date d1) {
		this.d1 = d1;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof EolinkPar))
	        return false;

	    EolinkPar other = (EolinkPar)o;

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

