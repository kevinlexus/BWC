package com.ric.bill.model.hotora.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Справочник лицевых счетов из HP@SCOTT.KART
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_HOUSES@HP", schema="SCOTT")
@Getter @Setter
public class Kart implements java.io.Serializable  {

	public Kart() {
	}

	@Id
    @Column(name = "lsk", unique=true, updatable = false, nullable = false)
	private String lsk;

	// REU
	@Column(name = "REU", updatable = false, nullable = false)
	private String reu;
	
	// KUL
	@Column(name = "KUL", updatable = false, nullable = false)
	private String kul;

	// ND
	@Column(name = "ND", updatable = false, nullable = false)
	private String nd;
	
	// C_LSK_ID
	@Column(name = "C_LSK_ID", updatable = false, nullable = true)
	private Integer cLskId;
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Kart))
	        return false;

	    Kart other = (Kart)o;

	    if (lsk == other.getLsk()) return true;
	    if (lsk == null) return false;

	    // equivalence by lsk
	    return lsk.equals(other.getLsk());
	}

	public int hashCode() {
	    if (lsk != null) {
	        return lsk.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

}

