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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

import com.ric.bill.model.bs.AddrTp;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.oralv.Ko;
import com.ric.bill.model.sec.User;


/**
 * Связанный объект 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EOLINK", schema="EXS")
public class Eolink implements java.io.Serializable  {

	public Eolink() {
	}


	// Конструктор
	public Eolink(String reu, String kul, String nd, String kw, String lsk,
			Integer entry, String usl, Integer idCnt, String guid, String un,
			String cdExt, AddrTp objTp, Integer appTp, Lst objTpx, Ko koObj, Eolink parEolink, User user) {
		super();
		this.reu = reu;
		this.kul = kul;
		this.nd = nd;
		this.kw = kw;
		this.lsk = lsk;
		this.entry = entry;
		this.usl = usl;
		this.idCnt = idCnt;
		this.guid = guid;
		this.un = un;
		this.cdExt = cdExt;
		this.objTp = objTp;
		this.appTp = appTp;
		this.objTpx = objTpx;
		this.koObj = koObj;
		this.parent = parEolink;
		this.user = user;
	}

	// Конструктор
	public Eolink(String guid, String un,
			String cdExt, AddrTp objTp, Integer appTp, Lst objTpx, Ko koObj, User user) {
		super();
		this.guid = guid;
		this.un = un;
		this.cdExt = cdExt;
		this.objTp = objTp;
		this.appTp = appTp;
		this.objTpx = objTpx;
		this.koObj = koObj;
		this.user = user;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXS")
	@SequenceGenerator(name="SEQ_EXS", sequenceName="EXS.SEQ_EOLINK", allocationSize=1)	
    @Column(name = "id", unique=true, updatable = false, nullable = false)
	private Integer id;

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
	@Column(name = "ENTRY", updatable = true, nullable = true)
	private Integer entry;
	
	// Услуга в системе "Квартплата" (для счетчика)
	@Column(name = "USL", updatable = true, nullable = true)
	private String usl;
	
	// ID Группового счетчика в системе "Квартплата" из таблицы a_flow.n1
	@Column(name = "ID_CNT", updatable = true, nullable = true)
	private Integer idCnt;
	
	// ID Группы счетчика в системе "Квартплата" из таблицы a_flow.n2
	@Column(name = "ID_GRP", updatable = true, nullable = true)
	private Integer idGrp;

	// GUID объекта во внешней системе
	@Column(name = "GUID", updatable = true, nullable = true)
	private String guid;
	
	// Уникальный номер объекта во внешней системе
	@Column(name = "UNIQNUM")
	private String un;

	// ID объекта во внешней системе (алфавитно-цифровой)
	@Column(name = "CD_EXT", updatable = true, nullable = true)
	private String cdExt;

	// Тип объекта (например "Договор") (используется для обмена с "Квартплатой") 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_OBJTP", referencedColumnName="ID", updatable = false)
	private AddrTp objTp;
	
	// Тип информационной системы (0- "Квартплата", 1 - "Новая разработка")
	@Column(name = "APP_TP", updatable = true, nullable = true)
	private Integer appTp;
	
	// Расширенный тип объекта (например "Договор управления") (используется для обмена с "Квартплатой")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_OBJTPX", referencedColumnName="ID")
	private Lst objTpx;

	// Идентификатор объекта связанного с EOLINK, в новой разработке
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="ID")
	private Ko koObj;

	// Родительский связанный объект 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID", updatable = false)
	private Eolink parent;

	// ОГРН Организации
	@Column(name = "OGRN", updatable = true, nullable = true)
	private String ogrn;

	// Пользователь создавший запись
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USER", referencedColumnName="ID")
	private User user;

	// Параметры
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private List<EolinkPar> eolinkPar = new ArrayList<EolinkPar>(0);
	
	// Дочерние объекты
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID")
	private List<Eolink> child = new ArrayList<Eolink>(0);

	// Дочерние объекты, связанные через EOLXEOL	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "EXS.EOLXEOL", joinColumns = {
			@JoinColumn(name = "FK_PARENT", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "FK_CHILD",
					nullable = false, updatable = false) })
	private List<Eolink> childLinked = new ArrayList<Eolink>(0);
	
	// Родительские объекты, связанные через EOLXEOL	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "EXS.EOLXEOL", joinColumns = {
			@JoinColumn(name = "FK_CHILD", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "FK_PARENT",
					nullable = false, updatable = false) })
	private List<Eolink> parentLinked = new ArrayList<Eolink>(0);

	// Дочерние объекты, связанные через внешнюю таблицу
/*	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_PARENT", referencedColumnName="ID")
	private List<EolinkToEolink> childLinked = new ArrayList<EolinkToEolink>(0);
*/
	// Родительские объекты, связанные через внешнюю таблицу
/*	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_CHILD", referencedColumnName="ID")
	private List<EolinkToEolink> parentLinked = new ArrayList<EolinkToEolink>(0);
*/
	// Статус, 0 - архивная запись, 1-активная запись
	@Column(name = "STATUS", updatable = true, nullable = true)
	private Integer status;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	public Integer getEntry() {
		return entry;
	}

	public void setEntry(Integer entry) {
		this.entry = entry;
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

	public Integer getAppTp() {
		return appTp;
	}

	public void setAppTp(Integer appTp) {
		this.appTp = appTp;
	}


	public AddrTp getObjTp() {
		return objTp;
	}

	public void setObjTp(AddrTp objTp) {
		this.objTp = objTp;
	}

	public Lst getObjTpx() {
		return objTpx;
	}

	public void setObjTpx(Lst objTpx) {
		this.objTpx = objTpx;
	}

	
	public Ko getKoObj() {
		return koObj;
	}

	public void setKoObj(Ko koObj) {
		this.koObj = koObj;
	}

	public Eolink getParent() {
		return parent;
	}

	public void setParent(Eolink parEolink) {
		this.parent = parEolink;
	}

	public String getOgrn() {
		return ogrn;
	}

	public void setOgrn(String ogrn) {
		this.ogrn = ogrn;
	}

	public List<EolinkPar> getEolinkPar() {
		return eolinkPar;
	}

	public void setEolinkPar(List<EolinkPar> eolinkPar) {
		this.eolinkPar = eolinkPar;
	}

	public List<Eolink> getChildLinked() {
		return childLinked;
	}

	public void setChildLinked(List<Eolink> childLinked) {
		this.childLinked = childLinked;
	}

	public List<Eolink> getParentLinked() {
		return parentLinked;
	}

	public void setParentLinked(List<Eolink> parentLinked) {
		this.parentLinked = parentLinked;
	}

	public List<Eolink> getChild() {
		return child;
	}

	public void setChild(List<Eolink> child) {
		this.child = child;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getIdGrp() {
		return idGrp;
	}

	public void setIdGrp(Integer idGrp) {
		this.idGrp = idGrp;
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

