package com.ric.bill.model.hotora.scott;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.ric.bill.model.exs.Eolink;
import com.ric.bill.model.exs.ServGis;
import com.ric.bill.model.fn.TempLskId;
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;

/**
 * Справочник услуг из старой разработки
 * (обычно смотрит либо в синоним по DBLINK, либо через этот же синоним в таблицу в схеме SCOTT)
 * @author lev
 *
 */
@Entity
@Table(name = "USL")  // Схемы нет, потому что это Synonym! 
@Getter @Setter
public class Usl implements java.io.Serializable  {
	private static final long serialVersionUID = 1L;
	
	@Id 
	@GeneratedValue(generator = "uuid")
	@GenericGenerator(name = "uuid", strategy = "uuid2")
	@Column(name = "USL")
	private String id;

	// Наименование
	@Column(name = "NM")
	private String nm;

	@ManyToOne(fetch = FetchType.LAZY) // Не смог сделать OneToOne, так как валилось 
	// Provided id of the wrong type for class com.ric.bill.model.exs.ServGis. 
	// Expected: class java.lang.Integer, got class java.lang.String Видимо, в связи с использованием 
	// String в качестве @Id
	@JoinColumn(name="USL", referencedColumnName="FK_USL", updatable = false, insertable = false)
	private ServGis servGis;
	
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

