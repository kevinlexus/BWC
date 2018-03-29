package com.ric.bill.model.fn;


import java.util.ArrayList;
import java.util.List;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.Simple;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;

/**
 * Детали перерасчета - лицевые счета
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CHNG_LSK", schema="FN")
@Getter @Setter
public class ChngLsk implements java.io.Serializable, Simple {

	public ChngLsk() {
		
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;

    // лиц.счет
    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false)
	private Kart kart;
	
	// заголовок перерасчета
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID", updatable = false)
	private Chng chng;
	
	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID", updatable = false)
	private Serv serv;
	
	// начисление 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHRG", referencedColumnName="ID", updatable = false)
	private Chrg chrg;

	// результат перерасчета, в объеме
	@Column(name = "VOL_CHNG")
	private Double volChng;
	
	// результат перерасчета, в денежном выражении
	@Column(name = "SUM_CHNG")
	private Double sumChng;

	// результат перерасчета, после корректировки
	@Column(name = "SUM_CORR")
	private Double sumCorr;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG_LSK", referencedColumnName="ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<ChngVal> chngVal = new ArrayList<ChngVal>(0);

	// родительская запись
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID", updatable = false)
	private ChngLsk parent;
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof ChngLsk))
	        return false;

	    ChngLsk other = (ChngLsk)o;

	    if (getId() == other.getId()) return true;
	    if (getId() == null) return false;

	    // equivalence by id
	    return getId().equals(other.getId());
	}


	public int hashCode() {
	    if (getId() != null) {
	        return getId().hashCode();
	    } else {
	        return super.hashCode();
	    }
	}
	
}

