package com.ric.bill.model.hotora.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

/**
 * Справочник услуг из старой разработки
 * (обычно смотрит либо в синоним по DBLINK, либо через этот же синоним в таблицу в схеме SCOTT)
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "USL")  // TODO СХЕМА КАКАЯ, ЕСЛИ ЭТО Синоним, да еще и по DBLINK? 
@Getter @Setter
public class Usl implements java.io.Serializable  {

	public Usl() {
	}
	
	@Id
    @Column(name = "USL", unique=true, updatable = false, nullable = false)
	private String id;

	// Наименование
	@Column(name = "NM")
	private String nm;

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Usl))
	        return false;

	    Usl other = (Usl)o;

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

