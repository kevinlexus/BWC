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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.model.ar.House;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.bs.Base;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;


/**
 * Логический счетчик
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="rrr1")
@Table(name = "METER_LOG", schema="MT")
@Getter @Setter
public class MeterLog extends Base implements java.io.Serializable, MLogs {

	public MeterLog () {
		
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	protected Integer id; //id записи

	// физический счетчик
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_METER_LOG", referencedColumnName="ID")
	@BatchSize(size = 50)
	//@Fetch(FetchMode.SUBSELECT) // убрал subselect, так как внезапно начало тормозить  
	private List<Meter> meter = new ArrayList<Meter>(0);

	/* TODO
	 * Иногда (!!) происходит увеличение в десятки раз записей Vol, в случае использования совместно EAGER и @BatchSize(size = 50)
	 * Именно иногда, повторить проблему сложно!
	 * 
	 * ВНИМАНИЕ! НЕ ИСПОЛЬЗОВАТЬ EAGER СОВМЕСТНО С @BatchSize
	 * ВНИМАНИЕ! НЕ ИСПОЛЬЗОВАТЬ EAGER СОВМЕСТНО С @BatchSize  РАЗОБРАТЬСЯ С ПРОБЛЕМОЙ!!!
	 * ВНИМАНИЕ! НЕ ИСПОЛЬЗОВАТЬ EAGER СОВМЕСТНО С @BatchSize
	 * ВНИМАНИЕ! НЕ ИСПОЛЬЗОВАТЬ EAGER СОВМЕСТНО С @BatchSize
	 * 
	 */
	
	// объем счетчика
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_METER_LOG", referencedColumnName="ID", updatable = false) //внимание! если здесь убрать updatable = false то будет update meter_vol fk_meter_log!
	//@BatchSize(size = 50)
	@Fetch(FetchMode.SUBSELECT) // убрал subselect, так как внезапно начало тормозить  
	private List<Vol> vol = new ArrayList<Vol>(0);

	// связь счетчика, направленная от него
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="NOD_SRC", referencedColumnName="ID")
	//@BatchSize(size = 50)
	@Fetch(FetchMode.SUBSELECT) // убрал subselect, так как внезапно начало тормозить
	private List<MeterLogGraph> outside = new ArrayList<MeterLogGraph>(0);

	// связь счетчика, направленная к нему
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="NOD_DST", referencedColumnName="ID")
	//@BatchSize(size = 50)
	@Fetch(FetchMode.SUBSELECT) // убрал subselect, так как внезапно начало тормозить
	private List<MeterLogGraph> inside = new ArrayList<MeterLogGraph>(0);
	
	// тип лог.счетчика
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_TP", referencedColumnName="ID")
	private Lst tp; 
	
	// услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID")
	private Serv serv; 

	// привязка к лиц.счету
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="FK_KLSK_OBJ", updatable = false, insertable = false)
	private Kart kart; 

	// дом
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="FK_K_LSK", updatable = false, insertable = false)
	private House house; 

	// klsk объекта, к которому принадлежит данный счетчик
    @Column(name = "FK_KLSK_OBJ", updatable = false, nullable = true)
	private Integer klskObj;

	// № ввода
    @Column(name = "ENTRY", updatable = false, nullable = true)
	private Integer entry;
    
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof MeterLog))
	        return false;

	    MeterLog other = (MeterLog)o;

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

