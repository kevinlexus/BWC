package com.ric.bill.model.bs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Периоды отчётов
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PERIOD_REPORTS", schema="BS")
public class PeriodReports implements java.io.Serializable {
	public PeriodReports() {
		
	}
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;
	
    // дата отчёта
    @Column(name = "DT", updatable = false, nullable = true)
	private Date dt;
	
    // период отчёта
    @Column(name = "MG", updatable = false, nullable = true)
	private String mg;
    
    @ManyToOne(fetch = FetchType.LAZY) 
	@JoinColumn(name="FK_REPORT", referencedColumnName="ID")
	private Obj report; 
    
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Date getDt() {
		return dt;
	}

	public void setDt(Date dt) {
		this.dt = dt;
	}

	public String getMg() {
		return mg;
	}

	public void setMg(String mg) {
		this.mg = mg;
	}

	public Obj getReport() {
		return report;
	}

	public void setReport(Obj report) {
		this.report = report;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Dw))
	        return false;

	    PeriodReports other = (PeriodReports)o;

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
