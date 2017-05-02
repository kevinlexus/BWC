package com.ric.bill.model.ar;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.Storable;
import com.ric.bill.model.bs.Base;



/**
 * Квартира
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KW", schema="AR")
@AttributeOverride(name = "klsk", column = @Column(name = "FK_K_LSK"))
public class Kw extends Base implements java.io.Serializable, Storable {


	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	protected Integer id; //id записи

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_HOUSE", referencedColumnName="ID", updatable = false, insertable = false)
	private House house;

	@OneToMany(fetch = FetchType.LAZY)  //сделал LAZY!
	@JoinColumn(name="FK_KW", referencedColumnName="ID")
	//@BatchSize(size = 500)
	@Fetch(FetchMode.SUBSELECT)
	private List<Kart> kart = new ArrayList<Kart>(0);

	@Column(name = "FK_HOUSE", nullable = true)
	private Integer fkHouse;

	public House getHouse() {
		return house;
	}

	public void setHouse(House house) {
		this.house = house;
	}
	
	public List<Kart> getLsk() {
		return kart;
	}
	public void setLsk(List<Kart> kart) {
		this.kart = kart;
	}

	public Integer getFkHouse() {
		return fkHouse;
	}

	public void setFkHouse(Integer fkHouse) {
		this.fkHouse = fkHouse;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Kw))
	        return false;

	    Kw other = (Kw)o;

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

