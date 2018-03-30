package com.ric.bill.model.hotora.oralv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Контрагент
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SCHET_CONTRAGENT@HP", schema="SCOTT")
@Getter @Setter
public class SchetContragent implements java.io.Serializable {

	@Id
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

	// ИНН
    @Column(name = "INN")
	private String inn;
    
	// БИК
    @Column(name = "BIK")
	private String bik;
    
	// расчетный счет
    @Column(name = "RASCHET_SCHET")
	private String operAcc;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof SchetContragent))
	        return false;

	    SchetContragent other = (SchetContragent)o;

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

