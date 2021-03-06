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
import com.ric.bill.model.bs.Lst;

import lombok.Getter;
import lombok.Setter;

/**
 * Заголовок платежа
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PAYMENT", schema="CASH")
@Getter @Setter
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
    
	// дата банковского платежа
    @Column(name = "DT_PAY_BANK")
    private Date dtPayBank;

    // Рабочее место, осуществившее сбор платежа
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_WP", referencedColumnName="ID")
	private Wp wp; 

	// Инкассация
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_COLLECT", referencedColumnName="ID")
	private WpCollection wpClct; 

	// Тип платежа
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_TP", referencedColumnName="ID")
	private TypePay tp; 

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

