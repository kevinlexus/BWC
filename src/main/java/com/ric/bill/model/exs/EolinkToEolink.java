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


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXS")
	@SequenceGenerator(name="SEQ_EXS", sequenceName="EXS.SEQ_EOLXEOL", allocationSize=1)	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// Внешний объект
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private Eolink eolink;
	
	// Внешний объект, связанный с первым
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK2", referencedColumnName="ID")
	private Eolink lnkEolink;

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

	public Eolink getLnkEolink() {
		return lnkEolink;
	}

	public void setLnkEolink(Eolink lnkEolink) {
		this.lnkEolink = lnkEolink;
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

