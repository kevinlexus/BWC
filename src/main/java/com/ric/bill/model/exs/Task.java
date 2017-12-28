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
 * Задание на выполнение обмена с ГИС ЖКХ
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TASK", schema="EXS")
@DynamicUpdate
@Getter @Setter
public class Task implements java.io.Serializable  {

	public Task() {
	}

	
	public Task(Eolink eolink, Task parent, Task depTask, String state, Lst act, String guid, String msgGuid,
			String un, String result, Date crtDt, String tguid, Integer appTp, Integer fk_user) {
		super();
		this.eolink = eolink;
		this.parent = parent;
		this.depTask = depTask;
		this.state = state;
		this.act = act;
		this.guid = guid;
		this.msgGuid = msgGuid;
		this.un = un;
		this.result = result;
		this.crtDt = crtDt;
		this.tguid = tguid;
		this.appTp = appTp;
		this.fk_user = fk_user;
	}


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXS")
	@SequenceGenerator(name="SEQ_EXS", sequenceName="EXS.SEQ_TASK", allocationSize=1)	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// Связь с внешним объектом
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private Eolink eolink;
	
	// Родительское задание
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID")
	private Task parent; 
	
	// Дочерние задания
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID")
	private List<Task> child = new ArrayList<Task>(0);
	
	// Дочерние задания ссылаются на данное
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_PARENT", referencedColumnName="ID")
	private List<TaskToTask> inside = new ArrayList<TaskToTask>(0);

	// Данное задание ссылается на родительские TASKXTASK
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_CHILD", referencedColumnName="ID")
	private List<TaskToTask> outside = new ArrayList<TaskToTask>(0);
	
	// Дочерние задания, связанные через TASKXTASK - короче это всё работает, но как обработать тип связи?? TASKXTASK.FK_TP 
	// Возможный ответ -  @Filter and @FilterJoinTable ред.09.10.2017 почитать: http://www.concretepage.com/hibernate/hibernate-filter-and-filterjointable-annotation-example
	/*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "EXS.TASKXTASK", joinColumns = {
			@JoinColumn(name = "FK_PARENT", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "FK_CHILD",
					nullable = false, updatable = false) })
	private List<Task> childLinked = new ArrayList<Task>(0);*/
	
	// Родительские задания, связанные через TASKXTASK
	/*@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "EXS.TASKXTASK", joinColumns = {
			@JoinColumn(name = "FK_CHILD", nullable = false, updatable = false) },
			inverseJoinColumns = { @JoinColumn(name = "FK_PARENT",
					nullable = false, updatable = false) })
	private List<Task> parentLinked = new ArrayList<Task>(0);*/
	
	// Ведущее задание, после выполнения которого, в статус "ACP", начнёт выполняться текущее
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="DEP_ID", referencedColumnName="ID")
	private Task depTask; 

	// CD состояния
	@Column(name = "STATE")
	private String state;

	// Заданное действие
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ACT", referencedColumnName="ID")
	private Lst act;

	// GUID объекта присвоенный ГИС 
	@Column(name = "GUID", updatable = true, nullable = true)
	private String guid;

	// GUID Задания, присвоенный ГИС
	@Column(name = "MSGGUID", updatable = true, nullable = true)
	private String msgGuid;

	// Уникальный номер объекта во внешней системе
	@Column(name = "UNIQNUM")
	private String un;

	// Результат отправки
	@Column(name = "RESULT")
	private String result;

	// Дата создания
	@Column(name = "DT_CRT")
	private Date crtDt;

	// Дата обновления
	@Column(name = "DT_UPD")
	private Date updDt;

	// Транспортный GUID объекта
	@Column(name = "TGUID", updatable = true, nullable = true)
	private String tguid;

	// Тип информационной системы (0- "Квартплата", 1 - "Новая разработка")
	@Column(name = "APP_TP", updatable = true, nullable = true)
	private Integer appTp;

	// Пользователь (специально не стал делать MANY TO ONE - так как возможно не будет таблицы, куда TO ONE)
	@Column(name = "FK_USER", updatable = false, nullable = true)
	private Integer fk_user;
	
	// Параметры
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_TASK", referencedColumnName="ID")
	private List<TaskPar> taskPar = new ArrayList<TaskPar>(0);

	// Порядковый номер
	@Column(name = "npp")
	private String npp;
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Task))
	        return false;

	    Task other = (Task)o;

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

