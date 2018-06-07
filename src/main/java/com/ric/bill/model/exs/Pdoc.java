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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;


/**
 * Платежный документ в ГИС
 * @author lev
 * @version 1.00
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PDOC", schema="EXS")
@Getter @Setter
public class Pdoc implements java.io.Serializable  {

	public Pdoc() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PDOC")
	@SequenceGenerator(name="SEQ_PDOC", sequenceName="EXS.SEQ_PDOC", allocationSize=1)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// GUID во внешней системе
	@Column(name = "GUID", updatable = true, nullable = true)
	private String guid;

	// Номер документа в биллинге
	@Column(name = "CD", updatable = true, nullable = true)
	private String cd;

	// Уникальный номер во внешней системе
	@Column(name = "UNIQNUM")
	private String un;

	// Лицевой счет в EOLINK к которому прикреплен ПД
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private Eolink eolink;

	// Транспортный GUID объекта
	@Column(name = "TGUID", updatable = true, nullable = true)
	private String tguid;

	// Статус загрузки в ГИС (0-добавлен на загрузку, 1-загружен, 2-отменён)
	@Column(name = "STATUS", updatable = true, nullable = false)
	private Integer status;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_PDOC", referencedColumnName="ID")
	private List<Notif> notif = new ArrayList<Notif>(0);

	// Дата ПД
	@Column(name = "DT")
	private Date dt;

	// Статус в ГИС (1-действующий, 0-отменён)
	@Column(name = "V", updatable = true, nullable = false)
	private Integer v;

	// Код ошибки, при загрузке ПД в ГИС (0-нет ошибки, 1-есть)
	@Column(name = "ERR", updatable = true, nullable = false)
	private Integer err;

	@Override
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Pdoc))
	        return false;

	    Pdoc other = (Pdoc)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    // equivalence by id
	    return id.equals(other.getId());
	}

	@Override
	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}


}

