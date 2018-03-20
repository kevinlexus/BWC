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

import org.hibernate.annotations.Type;

import com.ric.bill.model.bs.AddrTp;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.oralv.Ko;
import com.ric.bill.model.sec.User;

import lombok.Getter;
import lombok.Setter;


/**
 * Связанный объект 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "EOLINK", schema="EXS")
@Getter @Setter
public class Eolink implements java.io.Serializable  {

	public Eolink() {
	}


	// Конструктор
	public Eolink(String reu, String kul, String nd, String kw, String lsk,
			Integer entry, String usl, Integer idCnt, String guid, String un,
			String cdExt, AddrTp objTp, Integer appTp, Lst objTpx, Ko koObj, Eolink parent, User user, Integer status) {
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
		this.parent = parent;
		this.user = user;
		this.status = status;
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
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EOLINK")
	@SequenceGenerator(name="SEQ_EOLINK", sequenceName="EXS.SEQ_EOLINK", allocationSize=1)	
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

	// Klsk (пришлось вынести сюда, так как нужен прямой доступ к полю, не через Ko)
	//@Column(name = "FK_KLSK_OBJ")
	//private Integer klsk;

	// Родительский объект 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID", nullable = true, updatable = true)
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
	
	// ID лиц.счета в системе "Квартплата" (Заполняется только для Лиц.счетов)
	@Column(name = "C_LSK_ID", updatable = true, nullable = true)
	private Integer cLskId;

	// Дата создания
	@Column(name = "DT_CRT")
	private Date crtDt;

	// Дата обновления
	@Column(name = "DT_UPD")
	private Date updDt;

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

