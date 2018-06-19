package com.ric.bill.model.mt;


import java.util.ArrayList;
import java.util.List;

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

import com.ric.bill.Storable;
import com.ric.bill.model.bs.Base;

import lombok.Getter;
import lombok.Setter;


/**
 * Физический счетчик
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="rrr1")
@Table(name = "METER", schema="MT")
@Getter @Setter
public class Meter extends Base implements java.io.Serializable, Storable {

	public Meter (){

	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	protected Integer id; //id записи

	// лог.счетчик
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_METER_LOG", referencedColumnName="ID")
	private MeterLog meterLog ;

	// объем
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_METER", referencedColumnName="ID")
	@BatchSize(size = 50)
	private List<Vol> vol = new ArrayList<Vol>(0);

	// периоды существования
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_METER", referencedColumnName="ID")
	@BatchSize(size = 50)
	private List<MeterExs> exs = new ArrayList<MeterExs>(0);

	// передаточное отношение
	@Column(name = "TRANS_RATIO", updatable = true, nullable = true)
	private Double trRatio;

	// заводской номер
	@Column(name = "FACTORY_NUM", updatable = true, nullable = true)
	private String factoryNum;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Meter))
	        return false;

	    Meter other = (Meter)o;

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

