package com.ric.bill.model.exs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

/**
 * Тип справочника 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "U_LISTTP", schema="EXS")
@DynamicUpdate
public class UlistTp implements java.io.Serializable  {

	public UlistTp() {
	}

	public UlistTp(String cd, Integer fkExt, String name, Date dt1, String grp,
			List<Ulist> ulist) {
		super();
		this.cd = cd;
		this.name = name;
		this.dt1 = dt1;
		this.grp = grp;
		this.ulist = ulist;
		this.fkExt = fkExt;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXS")
	@SequenceGenerator(name="SEQ_EXS", sequenceName="EXS.SEQ_BASE", allocationSize=1)	
    @Column(name = "id", unique=true, updatable = false, nullable = false)
	private Integer id;

	// CD элемента (ИЗ ГИС ЖКХ: С префиксом "GIS_" Реестровый номер справочника.)
	@Column(name = "CD", updatable = false, nullable = true)
	private String cd;
	
	// Наименование элемента (ИЗ ГИС ЖКХ: Наименование справочника.)
	@Column(name = "NAME", updatable = true, nullable = true)
	private String name;
	
	// ИЗ ГИС ЖКХ: Дата и время последнего изменения справочника.
	@Column(name = "DT1", updatable = true, nullable = true)
	private Date dt1;

	// ИЗ ГИС ЖКХ: Группа справочника: NSI - (по умолчанию) общесистемный NSIRAO - ОЖФ
	@Column(name = "GRP", updatable = true, nullable = true)
	private String grp;

	// ID элемента во внешней системе (ИЗ ГИС ЖКХ: Реестровый номер справочника.)
	@Column(name = "FK_EXT", updatable = false, nullable = true)
	private Integer fkExt;

	// Элементы соответствующие типу
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_LISTTP", referencedColumnName="ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<Ulist> ulist = new ArrayList<Ulist>(0);
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDt1() {
		return dt1;
	}

	public void setDt1(Date dt1) {
		this.dt1 = dt1;
	}

	public String getGrp() {
		return grp;
	}

	public void setGrp(String grp) {
		this.grp = grp;
	}

	public List<Ulist> getUlist() {
		return ulist;
	}

	public void setUlist(List<Ulist> ulist) {
		this.ulist = ulist;
	}

	public Integer getFkExt() {
		return fkExt;
	}

	public void setFkExt(Integer fkExt) {
		this.fkExt = fkExt;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof UlistTp))
	        return false;

	    UlistTp other = (UlistTp)o;

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

