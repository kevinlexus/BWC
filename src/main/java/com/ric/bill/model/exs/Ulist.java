package com.ric.bill.model.exs;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import lombok.Getter;
import lombok.Setter;

/**
 * Справочник элементов
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "U_LIST", schema="EXS")
@Getter@Setter
public class Ulist implements java.io.Serializable  {

	public Ulist() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXS")
	@SequenceGenerator(name="SEQ_EXS", sequenceName="EXS.SEQ_BASE", allocationSize=1)	
    @Column(name = "id", unique=true, updatable = false, nullable = false)
	private Integer id;

	public Ulist(String cd, String name, String guid, Date dt1, Date dt2,
			Boolean actual, UlistTp ulistTp, Integer npp, String value, Ulist parent,
			String refCode, String refGuid, String valTp) {
		super();
		this.cd = cd;
		this.name = name;
		this.guid = guid;
		this.dt1 = dt1;
		this.dt2 = dt2;
		this.actual = actual;
		this.ulistTp = ulistTp;
		this.npp = npp;
		this.s1 = value;
		this.parent = parent;
		this.refCode = refCode;
		this.refGuid = refGuid;
		this.valTp = valTp;
	}

	// CD элемента
	@Column(name = "CD", updatable = true, nullable = true)
	private String cd;
	
	// Наименование элемента
	@Column(name = "NAME", updatable = true, nullable = true)
	private String name;
	
	// Значение элемента
	@Column(name = "S1", updatable = true, nullable = true)
	private String s1;
	
	// ИЗ ГИС ЖКХ: GUID элемента
	@Column(name = "GUID", updatable = true, nullable = true)
	private String guid;

	// ИЗ ГИС ЖКХ: Дата начала действия значения
	@Column(name = "DT1", updatable = true, nullable = true)
	private Date dt1;

	// ИЗ ГИС ЖКХ: Дата окончания действия значения
	@Column(name = "DT2", updatable = true, nullable = true)
	private Date dt2;
	
	// ИЗ ГИС ЖКХ: Признак актуальности элемента справочника
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "ACTUAL", nullable = true)
	private Boolean actual;

	// Тип справочника
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK_LISTTP", referencedColumnName="ID", updatable = false, nullable = false)
	private UlistTp ulistTp; 

	// Номер порядковый
	@Column(name = "NPP", updatable = true, nullable = false)
	private Integer npp;

	// Ref code
	@Column(name = "REF_CODE", updatable = true, nullable = true)
	private String refCode;

	// Ref GUID 
	@Column(name = "REF_GUID", updatable = true, nullable = true)
	private String refGuid;
	
	// ИЗ ГИС ЖКХ: [(NM)number;  (ST)string;  (DT)date;  (BL) boolean (RF) reference (OK) OkeiRefFieldType]
	@Column(name = "VAL_TP", updatable = true, nullable = true)
	private String valTp;

	// ЗАПОЛНЯТЬ ТОЛЬКО У УСЛУГ С GUID<>null! Тип услуги 0-жилищная, 1-коммунальная (напр.Х.В.), 2-дополнительная (напр Замок), 3 - в т.ч. усл.на ОИ
	@Column(name = "TP", updatable = true, nullable = true)
	private Integer tp;
	
	// Родительский элемент
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID", updatable = false, nullable = false)
	private Ulist parent; 

	// Связь записи услуги ОИ с основной услугой
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID2", referencedColumnName="ID")
	private Ulist parent2; 

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Ulist))
	        return false;

	    Ulist other = (Ulist)o;

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

