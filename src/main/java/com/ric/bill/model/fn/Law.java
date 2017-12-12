package com.ric.bill.model.fn;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Закон по льготе
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "LAW", schema="FN")
@Getter @Setter
public class Law implements java.io.Serializable {


	public Law() {
		
	}

	// Id
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// Наименование
	@Column(name = "NAME")
	private String name;

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Law))
	        return false;

	    Law other = (Law)o;

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

