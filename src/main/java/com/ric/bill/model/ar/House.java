package com.ric.bill.model.ar;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import com.ric.bill.MeterContains;
import com.ric.bill.TarifContains;
import com.ric.bill.model.bs.Base;
import com.ric.bill.model.mt.MeterLog;
import com.ric.bill.model.tr.TarifKlsk;

import lombok.Getter;
import lombok.Setter;

/**
 * Дом
 * @author lev
 * @version 1.00
 *
 */
@SqlResultSetMapping(name="HouseMapping",
classes = {
    @ConstructorResult(
            targetClass = House.class,
            columns = { 
                @ColumnResult(name = "id", type = Long.class)
            })
})
@SuppressWarnings("serial")
@Entity
@Table(name = "HOUSE", schema="AR")
@Getter @Setter
public class House extends Base implements java.io.Serializable, MeterContains, TarifContains {

	public House() { 
	}

	public House(Integer id) {
		this.id = id;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	protected Integer id; //id записи

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_HOUSE", referencedColumnName="ID")
	//@BatchSize(size = 500)
	@Fetch(FetchMode.SUBSELECT)
	private List<Kw> kw = new ArrayList<Kw>(0);

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="FK_K_LSK")
	@BatchSize(size = 20)
	private List<MeterLog> mlog = new ArrayList<MeterLog>(0);

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="FK_K_LSK")
	@BatchSize(size = 50)
	private List<TarifKlsk> tarifklsk = new ArrayList<TarifKlsk>(0);

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_STREET", referencedColumnName="ID", updatable = false)
	private Street street;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_HOUSE", referencedColumnName="ID")
	@BatchSize(size = 20)
	private List<HouseSite> houseSite = new ArrayList<HouseSite>(0);

	// Номер дома
	@Column(name = "ND")
	private String nd;  
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof House))
	        return false;

	    House other = (House)o;

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
