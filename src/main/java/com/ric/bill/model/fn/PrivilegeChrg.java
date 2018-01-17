package com.ric.bill.model.fn;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;

/**
 * Возмещение по льготе
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PRIVILEGE_CHRG", schema="FN")
@Getter @Setter
public class PrivilegeChrg implements java.io.Serializable {

	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PRIV")
	@SequenceGenerator(name="SEQ_PRIV", sequenceName="FN.SEQ_PRIVILEGE_CHRG", allocationSize=10)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)			
	private Integer id;
	
    // лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false)
	private Kart kart;

	// статус, 0 - архивная запись, 1-текущее начисление
	@Column(name = "status", nullable = true)
	private Integer status;

	// льгота по проживающему
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PERSXPRIVILEGE", referencedColumnName="ID", updatable = false)
	private PersPrivilege persPrivilege;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID")
	private Serv serv; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
	private Org org; 

	// период
	@Column(name = "PERIOD")
	private String period;

	// сумма возмещения
	@Column(name = "SUMMA")
	private Double summa;
	
	// объем возмещения
	@Column(name = "VOL")
	private Double vol;

	// расценка
	@Column(name = "PRICE")
	private Double price;

	// перерасчет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID")
	private Chng chng; 
	
    // даты начала и окончания произведенного расчета
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;
	
    // default конструктор
    public PrivilegeChrg() {
    	
    }
    
	// конструктор
	public PrivilegeChrg(Kart kart, Serv serv, Org org, Integer status, PersPrivilege persPrivilege, String period, Double summa,
			Double vol, Chng chng, Double price, Date dt1, Date dt2) {
		super();
		this.kart = kart;
		this.serv = serv;
		this.org = org;
		this.status = status;
		this.persPrivilege = persPrivilege;
		this.period = period;
		this.summa = summa;
		this.vol = vol;
		this.chng = chng;
		this.price = price;
		this.dt1 = dt1;
		this.dt2 = dt2;
	}
	
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof PrivilegeChrg))
	        return false;

	    PrivilegeChrg other = (PrivilegeChrg)o;

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

