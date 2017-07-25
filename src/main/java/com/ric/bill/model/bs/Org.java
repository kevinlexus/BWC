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

import com.ric.bill.Storable;
import com.ric.bill.model.mt.Vol;
import com.ric.bill.model.oralv.Ko;

/**
 * Организация
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "ORG", schema="BS")
public class Org extends Base implements java.io.Serializable, Storable {

	@Id
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// CD
	@Column(name = "CD", updatable = false, nullable = false)
	private String cd; 

	// Наименование
    @Column(name = "NAME")
	private String name; 

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
	@BatchSize(size = 50)
	private List<OrgTp> orgTp = new ArrayList<OrgTp>(0);
    
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

	public List<OrgTp> getOrgTp() {
		return orgTp;
	}
	public void setOrgTp(List<OrgTp> orgTp) {
		this.orgTp = orgTp;
	}
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

