package com.ric.bill.model.exs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ric.bill.model.hotora.scott.Usl;
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;


/**
 * Справочник соответствий услуг ГИС-Услугам Новой и Старой разработки 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SERVGIS", schema="EXS")
@Getter @Setter
public class ServGis implements java.io.Serializable  {

	public ServGis() {
	}

	@Id
    @Column(name = "id", unique=true, updatable = false, nullable = false)
	private Integer id;

	// не нужно это CD вроде бы ред. Лев. 08.02.2018 
	// CD услуги, обобщенное (чтобы не выбирать, из какой системы брать CD) - не нужно это CD вроде бы
    //@Column(name = "cd", unique=true, updatable = false, nullable = false)
	//private String cd;
	
    // услуга из Новой разработки
    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID")
	private Serv serv; 

    // услуга из Старой разработки (обычно смотрит либо в синоним по DBLINK, либо через этот же синоним в таблицу в схеме SCOTT)
    @OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USL", referencedColumnName="USL")
	private Usl usl; 
    	    
    // элемент услуги в справочнике ГИС ЖКХ, который содержит GUID
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_LIST", referencedColumnName="ID")
	private Ulist ulist;  

	// организация, для определения справочника №1 (доп.услуг)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private Eolink org;

	// родительская запись
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID")
	private ServGis parent; 
	
	// Тип услуги 0-жилищная (в т.ч. Усл.на ОИ), 1-коммунальная (напр.Х.В.), 2-дополнительная (напр Замок)
    @Column(name = "tp", updatable = false, nullable = false)
	private Integer tp;

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof ServGis))
	        return false;

	    ServGis other = (ServGis)o;

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

