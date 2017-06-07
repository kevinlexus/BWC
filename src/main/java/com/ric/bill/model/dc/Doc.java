package com.ric.bill.model.dc;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ric.bill.Storable;
import com.ric.bill.model.bs.Base;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.oralv.Ko;

/**
 * Документ
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ORG", schema="DC")
public class Doc extends Base implements java.io.Serializable, Storable {

	// Id
	@Id
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// CD
	@Column(name = "CD", updatable = false, nullable = false)
	private String cd; 

	// Наименование
    @Column(name = "NAME")
	private String name;  
    
    // Тип документа
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_DOCTP", referencedColumnName="ID")
	private Lst tp; 

	public Integer getId() {
		return this.id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
    public String getCd() {
		return this.cd;
	}
	public void setCd(String cd) {
		this.cd = cd;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Doc))
	        return false;

	    Doc other = (Doc)o;

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

