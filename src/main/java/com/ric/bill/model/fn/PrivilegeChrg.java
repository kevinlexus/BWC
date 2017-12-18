package com.ric.bill.model.fn;


import java.math.BigDecimal;
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

import com.ric.bill.Simple;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.ps.Pers;
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
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="rrr1")
@Table(name = "PRIVILEGE_CHRG", schema="FN")
@Getter @Setter
public class PrivilegeChrg implements java.io.Serializable {

	// ID
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHRG")
	@SequenceGenerator(name="SEQ_CHRG", sequenceName="FN.SEQ_CHRG", allocationSize=10)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)			
	private Integer id;
	
    // Лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false)
	private Kart kart;

	//Статус, 0 - архивная запись, 1-текущее начисление
	@Column(name = "status", nullable = true)
	private Integer status;

	// Льгота по проживающему
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PERSXPRIVILEGE", referencedColumnName="ID", updatable = false)
	private PersPrivilege persPrivilege;
	
	// Период
	@Column(name = "PERIOD")
	private String period;

	// Сумма возмещения
	@Column(name = "SUMMA")
	private Double summa;
	
	// Объем возмещения
	@Column(name = "VOL")
	private Double vol;

    // Даты начала и окончания произведенного расчета
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;
	
	// Дата обновления
	@Column(name = "DTF", updatable = false, nullable = true)
    private Date dtf;

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

