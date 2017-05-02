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
import com.ric.bill.model.mt.MeterLog;

/**
 * Заголовок платежа
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PAYMENT", schema="CASH")
public class Payment implements java.io.Serializable, Simple {


	public Payment() {
		
	}

	public Payment(Kart kart, Date dtClct) {
		super();
		this.kart = kart;
		this.dtClct = dtClct;
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)					
	private Integer id;

	// Лиц.счет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false, insertable = false)
	private Kart kart; 
	
	// Дата инкассации ?? DMI - (веременное поле для сверки с квтп)
	@Column(name = "DT_COLLECT", updatable = false, nullable = true)
    private Date dtClct;

	// дата платежа
    @Column(name = "DTF")
    private Date dtf;
    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Kart getKart() {
		return kart;
	}

	public void setKart(Kart kart) {
		this.kart = kart;
	}

	public Date getDtClct() {
		return dtClct;
	}

	public void setDtClct(Date dtClct) {
		this.dtClct = dtClct;
	}

	public Date getDtf() {
		return dtf;
	}

	public void setDtf(Date dtf) {
		this.dtf = dtf;
	}
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Payment))
	        return false;

	    Payment other = (Payment)o;

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

