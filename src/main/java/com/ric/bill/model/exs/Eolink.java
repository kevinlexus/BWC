package com.ric.bill.model.exs;

import java.util.ArrayList;
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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.model.bs.AddrTp;


/**
 * Действия над объектами 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EOLINK", schema="EXS")
public class Eolink implements java.io.Serializable  {

	public Eolink() {
	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXS")
	@SequenceGenerator(name="SEQ_EXS", sequenceName="EXS.SEQ_EOLINK", allocationSize=1)	
    @Column(name = "id", unique=true, updatable = false, nullable = false)
	private Integer id;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID", updatable = false)
	@Fetch(FetchMode.SUBSELECT)
	private List<Action> action = new ArrayList<Action>(0);
	
	// РЭУ в системе "Квартплата"
	@Column(name = "REU", updatable = true, nullable = true)
	private String reu;
	
	// Улица в системе "Квартплата"
	@Column(name = "KUL", updatable = true, nullable = true)
	private String kul;
	
	// Дом в системе "Квартплата"
	@Column(name = "ND", updatable = true, nullable = true)
	private String nd;
	
	// Квартира в системе "Квартплата"
	@Column(name = "KW", updatable = true, nullable = true)
	private String kw;

	// Лиц.счет в системе "Квартплата"
	@Column(name = "LSK", updatable = true, nullable = true)
	private String lsk;
	
	// Подъезд в  системе "Квартплата"
	@Column(name = "PODEZD", updatable = true, nullable = true)
	private Integer podezd;
	
	// Услуга в системе "Квартплата" (для счетчика)
	@Column(name = "USL", updatable = true, nullable = true)
	private String usl;
	
	// Групповой счетчик в системе "Квартплата" (для группового счетчика, из таблицы)
	@Column(name = "ID_CNT", updatable = true, nullable = true)
	private Integer idCnt;
	
	// GUID объекта во внешней системе
	@Column(name = "GUID", updatable = true, nullable = true)
	private String guid;
	
	// ID объекта во внешней системе (алфавитно-цифровой)
	@Column(name = "CD_EXT", updatable = true, nullable = true)
	private String cdExt;

	// Идентификатор объекта в новой разработке
	@Column(name = "FK_KLSK", updatable = true, nullable = true)
	private Integer fkKlsk;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_OBJTP", referencedColumnName="ID", updatable = false)
	private AddrTp addrTp;
	
	// Тип информационной системы (0- "Квартплата", 1 - "Новая разработка")
	@Column(name = "APP_TP", updatable = true, nullable = true)
	private Integer appTp;
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public List<Action> getAction() {
		return action;
	}

	public void setAction(List<Action> action) {
		this.action = action;
	}

	public String getReu() {
		return reu;
	}

	public void setReu(String reu) {
		this.reu = reu;
	}

	public String getKul() {
		return kul;
	}

	public void setKul(String kul) {
		this.kul = kul;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public String getKw() {
		return kw;
	}

	public void setKw(String kw) {
		this.kw = kw;
	}

	public String getLsk() {
		return lsk;
	}

	public void setLsk(String lsk) {
		this.lsk = lsk;
	}

	public Integer getPodezd() {
		return podezd;
	}

	public void setPodezd(Integer podezd) {
		this.podezd = podezd;
	}

	public String getUsl() {
		return usl;
	}

	public void setUsl(String usl) {
		this.usl = usl;
	}

	public Integer getIdCnt() {
		return idCnt;
	}

	public void setIdCnt(Integer idCnt) {
		this.idCnt = idCnt;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getCdExt() {
		return cdExt;
	}

	public void setCdExt(String cdExt) {
		this.cdExt = cdExt;
	}

	public Integer getFkKlsk() {
		return fkKlsk;
	}

	public void setFkKlsk(Integer fkKlsk) {
		this.fkKlsk = fkKlsk;
	}

	public Integer getAppTp() {
		return appTp;
	}

	public void setAppTp(Integer appTp) {
		this.appTp = appTp;
	}

	public AddrTp getAddrTp() {
		return addrTp;
	}

	public void setAddrTp(AddrTp addrTp) {
		this.addrTp = addrTp;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Eolink))
	        return false;

	    Eolink other = (Eolink)o;

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

