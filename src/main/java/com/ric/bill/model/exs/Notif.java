package com.ric.bill.model.exs;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.ric.bill.model.bs.Lst;

import lombok.Getter;
import lombok.Setter;


/**
 * Извещение исполнителя о принятии к исполнению распоряжения
 * @author lev
 * @version 1.00
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "NOTIF", schema="EXS")
@Getter @Setter
public class Notif implements java.io.Serializable  {

	public Notif() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTIF")
	@SequenceGenerator(name="SEQ_NOTIF", sequenceName="EXS.SEQ_NOTIF", allocationSize=1)	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// GUID во внешней системе
	@Column(name = "GUID", updatable = true, nullable = true)
	private String guid;
	
	// Уникальный номер во внешней системе
	@Column(name = "UNIQNUM")
	private String un;

	// ПД
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PDOC", referencedColumnName="ID")
	private Pdoc pdoc;
	
	// FK на платеж из L_PAY (на составляющую, MN или PP, где разбито только по периодам)
	@Column(name = "FK_L_PAY", updatable = false)
	private Integer fLpay;

	// Сумма
	@Column(name = "SUMMA", updatable = false)
	private Double summa;

	// Дата внесения оплаты (в случае отсутствия: дата поступления средств)
	@Column(name = "DT", updatable = false)
	private Date dt;

	// Транспортный GUID объекта
	@Column(name = "TGUID", updatable = true, nullable = true)
	private String tguid;
	
	// Статус загрузки в ГИС (0-добавлено на загрузку, 1-загружено, 2-отменёно)
	@Column(name = "STATUS", updatable = true, nullable = false)
	private Integer status;
	
	// Статус в ГИС (1-действующее, 0-отменено)
	@Column(name = "V", updatable = true, nullable = false)
	private Integer v;

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Notif))
	        return false;

	    Notif other = (Notif)o;

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

