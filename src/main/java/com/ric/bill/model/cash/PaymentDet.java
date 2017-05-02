package com.ric.bill.model.cash;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.ric.bill.Simple;
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.tr.Serv;

/**
 * Платёж
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PAYMENT_DET", schema="CASH")
public class PaymentDet implements java.io.Serializable, Simple {


	public PaymentDet() {
		
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)					
	private Integer id;

	// Заголовок платежа
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PAYMENT", referencedColumnName="ID", updatable = false, insertable = false)
	private Payment payment; 
	
	// Услуга
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID")
	private Serv serv; 
	
	// Организация
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
	private Org org; 

	// Сумма
	@Column(name = "SUMMA")
	private Double summa;
	
	// Пеня
	@Column(name = "PEN")
	private Double pen;
	
	// За Период принято
	@Column(name = "PERIOD")
	private String period;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Serv getServ() {
		return serv;
	}

	public void setServ(Serv serv) {
		this.serv = serv;
	}

	public Org getOrg() {
		return org;
	}

	public void setOrg(Org org) {
		this.org = org;
	}

	public Double getSumma() {
		return summa;
	}

	public void setSumma(Double summa) {
		this.summa = summa;
	}

	public Double getPen() {
		return pen;
	}

	public void setPen(Double pen) {
		this.pen = pen;
	}

	public String getPeriod() {
		return period;
	}

	public void setPeriod(String period) {
		this.period = period;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof PaymentDet))
	        return false;

	    PaymentDet other = (PaymentDet)o;

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

