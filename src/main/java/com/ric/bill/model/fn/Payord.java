package com.ric.bill.model.fn;


import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.Simple;
import com.ric.bill.model.bs.Lst;

/**
 * Платежное поручение
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PAYORD", schema="FN")
public class Payord implements java.io.Serializable, Simple {


	public Payord() {
		
	}

	public Payord(String name, String selDays, String formula,
			PayordGrp payordGrp, Lst periodTp) {
		super();
		this.name = name;
		this.selDays = selDays;
		this.formula = formula;
		this.payordGrp = payordGrp;
		this.periodTp = periodTp;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PAYORD")
	@SequenceGenerator(name="SEQ_PAYORD", sequenceName="FN.SEQ_PAYORD", allocationSize=1) 	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)					
	private Integer id;

	// Наименование
	@Column(name = "NAME")
	private String name;

	// Выбранные дни
	@Column(name = "SEL_DAYS")
	private String selDays;

	// Пользователь
	@Column(name = "USERNAME", insertable = false, updatable = false)
	private String username;
	
	// Дата создания
	@Column(name = "DTF", insertable = false, updatable = false)
	private Date dtf;

	// Формула
	@Column(name = "FORMULA")
	private String formula;

	// Группа платежки
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PAYORD_GRP", referencedColumnName="ID")
	private PayordGrp payordGrp; 

	// Тип периода
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PERIODTP", referencedColumnName="ID")
	private Lst periodTp; 

	// Формулы платежки
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PAYORD", referencedColumnName="ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<PayordCmp> payordCmp = new ArrayList<PayordCmp>(0);

	
//	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
//	@JoinColumn(name="FK_METER_LOG", referencedColumnName="ID", updatable = false) //внимание! если здесь убрать updatable = false то будет update kmp_meter_vol fk_meter_log!
	
	// Движение по платежке
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_PAYORD", referencedColumnName="ID", updatable = false)
	//@Fetch(FetchMode.SUBSELECT)
	private List<PayordFlow> payordFlow = new ArrayList<PayordFlow>(0);
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Lst getPeriodTp() {
		return periodTp;
	}

	public void setPeriodTp(Lst periodTp) {
		this.periodTp = periodTp;
	}

	public String getSelDays() {
		return selDays;
	}

	public void setSelDays(String selDays) {
		this.selDays = selDays;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getDtf() {
		return dtf;
	}

	public void setDtf(Date dtf) {
		this.dtf = dtf;
	}


	public PayordGrp getPayordGrp() {
		return payordGrp;
	}

	public void setPayordGrp(PayordGrp payordGrp) {
		this.payordGrp = payordGrp;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public List<PayordCmp> getPayordCmp() {
		return payordCmp;
	}

	public void setPayordCmp(List<PayordCmp> payordCmp) {
		this.payordCmp = payordCmp;
	}

	
	public List<PayordFlow> getPayordFlow() {
		return payordFlow;
	}

	public void setPayordFlow(List<PayordFlow> payordFlow) {
		this.payordFlow = payordFlow;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Payord))
	        return false;

	    Payord other = (Payord)o;

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

