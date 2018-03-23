package com.ric.bill.model.exs;

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

import com.ric.bill.model.bs.Lst;


/**
 * Связь внешних объектов друг с другом
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EOLXEOL", schema="EXS")
public class EolinkToEolink implements java.io.Serializable  {

	// Конструктор
	public EolinkToEolink() {
	}
	
	public EolinkToEolink(Eolink parent, Eolink child, Lst tp) {
		super();
		this.parent = parent;
		this.child = child;
		this.tp = tp;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EOLINKTOEOLINK")
	@SequenceGenerator(name="SEQ_EOLINKTOEOLINK", sequenceName="EXS.SEQ_EOLXEOL", allocationSize=1)	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// Родительский Внешний объект 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PARENT", referencedColumnName="ID")
	private Eolink parent;
	
	// Дочерний Внешний объект
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHILD", referencedColumnName="ID")
	private Eolink child;

	// Тип связи
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_TP", referencedColumnName="ID")
	private Lst tp; 
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Eolink getParent() {
		return parent;
	}

	public void setParent(Eolink parent) {
		this.parent = parent;
	}

	public Eolink getChild() {
		return child;
	}

	public void setChild(Eolink child) {
		this.child = child;
	}

	public Lst getTp() {
		return tp;
	}

	public void setTp(Lst tp) {
		this.tp = tp;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof EolinkToEolink))
	        return false;

	    EolinkToEolink other = (EolinkToEolink)o;

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

