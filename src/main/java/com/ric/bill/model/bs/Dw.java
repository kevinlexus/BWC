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
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.Simple;
import com.ric.bill.model.ar.House;

/*
 * Значение параметра объекта
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "U_HFPXKLSK", schema="ORALV")
public class Dw implements java.io.Serializable, Simple {

	public Dw() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id;
	
    //значение - строка
    @Column(name = "S1", updatable = true)
    private String s1;

    //значение - число
    @Column(name = "NS1", updatable = true)
    private Double n1;

    //значение - дата
    @Column(name = "DTS1", updatable = true)
	private Date dts1;
    
    // Заменил LAZY на EAGER - стало быстрее... может не правильно тестил? 10.09.2016
    @ManyToOne(fetch = FetchType.LAZY) //если LAZY заменить EAGER, то будут джойниться все параметры и экстрактиться тоже!(замедление) 
	@JoinColumn(name="FK_HFP", referencedColumnName="ID")
	@BatchSize(size = 50)
	private Par par; 

    //даты начала и окончания произведенного объема
    @Column(name = "DT1", updatable = false, nullable = true)
	private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
	private Date dt2;

    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getS1() {
		return s1;
	}
	public void setS1(String s1) {
		this.s1 = s1;
	}

	public Double getN1() {
		return n1;
	}

	public void setN1(Double n1) {
		this.n1 = n1;
	}	

	public Par getPar() {
		return par;
	}

	public void setPar(Par par) {
		this.par = par;
	}
	
	public Date getDt1() {
		return dt1;
	}

	public void setDt1(Date dt1) {
		this.dt1 = dt1;
	}

	public Date getDt2() {
		return dt2;
	}

	public void setDt2(Date dt2) {
		this.dt2 = dt2;
	}
	public Date getDts1() {
		return dts1;
	}
	
	public void setDts1(Date dts1) {
		this.dts1 = dts1;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Dw))
	        return false;

	    Dw other = (Dw)o;

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
