package com.ric.bill.model.exs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Generated;
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

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TASK")
	@SequenceGenerator(name="SEQ_TASK", sequenceName="EXS.SEQ_TASK", allocationSize=1)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// связь с внешним объектом
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private Eolink eolink;

	// родительское задание
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID")
	private Task parent;

	// дочерние задания
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID")
	private List<Task> child = new ArrayList<Task>(0);

	// зависимые задания ссылаются на данное
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_PARENT", referencedColumnName="ID")
	private List<TaskToTask> inside = new ArrayList<TaskToTask>(0);

	// данное задание ссылается на ведущее TASKXTASK
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_CHILD", referencedColumnName="ID")
	private List<TaskToTask> outside = new ArrayList<TaskToTask>(0);

	// ведущее задание по DEP_ID, после выполнения которого, в статус "ACP", начнёт выполняться текущее
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="DEP_ID", referencedColumnName="ID")
	private Task master;

	// ведомые задания по DEP_ID, по отношению к текущему
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="DEP_ID", referencedColumnName="ID")
	private List<Task> slave = new ArrayList<Task>(0);

	// кол-во ошибок при запросе ACK
    @Column(name = "ERRACKCNT", updatable = true, nullable = false)
	private Integer errAckCnt;

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


	// CD
	@Column(name = "CD")
	private String cd;

	// статус обработки
	@Column(name = "STATE")
	private String state;

	// заданное действие
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ACT", referencedColumnName="ID")
	private Lst act;

	// GUID объекта присвоенный ГИС
	@Column(name = "GUID", updatable = true, nullable = true)
	private String guid;

	// GUID Задания, присвоенный ГИС
	@Column(name = "MSGGUID", updatable = true, nullable = true)
	private String msgGuid;

	// уникальный номер объекта во внешней системе
	@Column(name = "UNIQNUM")
	private String un;

	// результат отправки
	@Column(name = "RESULT")
	private String result;

	// дата создания
	@Column(name = "DT_CRT")
	private Date dtCrt;

	// дата обновления
	@Column(name = "DT_UPD")
	private Date updDt;

	// транспортный GUID объекта
	@Column(name = "TGUID", updatable = true, nullable = true)
	private String tguid;

	// пользователь (специально не стал делать MANY TO ONE - так как возможно не будет таблицы, куда TO ONE)
	@Column(name = "FK_USER", updatable = false, nullable = true)
	private Integer fk_user;

	// параметры
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="FK_TASK", referencedColumnName="ID")
	private List<TaskPar> taskPar;;

	// порядковый номер
	@Column(name = "npp")
	private String npp;

	// приоритет - больше значение - выше приоритет обработки
	@Column(name = "priority", updatable = false, nullable = true)
	private Integer priority;

	// уровень трассировки (0 - не трассировать в лог, 1 - только XML)
	@Column(name = "trace", updatable = false, nullable = false)
	private Integer trace;

	@Generated("SparkTools")
	private Task(Builder builder) {
		this.id = builder.id;
		this.eolink = builder.eolink;
		this.parent = builder.parent;
		this.child = builder.child;
		this.inside = builder.inside;
		this.outside = builder.outside;
		this.master = builder.master;
		this.slave = builder.slave;
		this.errAckCnt = builder.errAckCnt;
		this.cd = builder.cd;
		this.state = builder.state;
		this.act = builder.act;
		this.guid = builder.guid;
		this.msgGuid = builder.msgGuid;
		this.un = builder.un;
		this.result = builder.result;
		this.dtCrt = builder.crtDt;
		this.updDt = builder.updDt;
		this.tguid = builder.tguid;
		this.fk_user = builder.fk_user;
		this.taskPar = builder.taskPar;
		this.npp = builder.npp;
		this.priority = builder.priority;
		this.trace = builder.trace;
	}

	@Override
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

	@Override
	public int hashCode() {
	    if (id != null) {
	        return id.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

	/**
	 * Creates builder to build {@link Task}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Task}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer id;
		private Eolink eolink;
		private Task parent;
		private List<Task> child = new ArrayList<Task>(0);
		private List<TaskToTask> inside = new ArrayList<TaskToTask>(0);
		private List<TaskToTask> outside = new ArrayList<TaskToTask>(0);
		private Task master;
		private List<Task> slave = new ArrayList<Task>(0);
		private Integer errAckCnt;
		private String cd;
		private String state;
		private Lst act;
		private String guid;
		private String msgGuid;
		private String un;
		private String result;
		private Date crtDt;
		private Date updDt;
		private String tguid;
		private Integer fk_user;
		private List<TaskPar> taskPar = new ArrayList<TaskPar>(0);//= Collections.emptyList();
		private String npp;
		private Integer priority;
		private Integer trace;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withEolink(Eolink eolink) {
			this.eolink = eolink;
			return this;
		}

		public Builder withParent(Task parent) {
			this.parent = parent;
			return this;
		}

		public Builder withChild(List<Task> child) {
			this.child = child;
			return this;
		}

		public Builder withInside(List<TaskToTask> inside) {
			this.inside = inside;
			return this;
		}

		public Builder withOutside(List<TaskToTask> outside) {
			this.outside = outside;
			return this;
		}

		public Builder withMaster(Task master) {
			this.master = master;
			return this;
		}

		public Builder withSlave(List<Task> slave) {
			this.slave = slave;
			return this;
		}

		public Builder withErrAckCnt(Integer errAckCnt) {
			this.errAckCnt = errAckCnt;
			return this;
		}

		public Builder withCd(String cd) {
			this.cd = cd;
			return this;
		}

		public Builder withState(String state) {
			this.state = state;
			return this;
		}

		public Builder withAct(Lst act) {
			this.act = act;
			return this;
		}

		public Builder withGuid(String guid) {
			this.guid = guid;
			return this;
		}

		public Builder withMsgGuid(String msgGuid) {
			this.msgGuid = msgGuid;
			return this;
		}

		public Builder withUn(String un) {
			this.un = un;
			return this;
		}

		public Builder withResult(String result) {
			this.result = result;
			return this;
		}

		public Builder withCrtDt(Date crtDt) {
			this.crtDt = crtDt;
			return this;
		}

		public Builder withUpdDt(Date updDt) {
			this.updDt = updDt;
			return this;
		}

		public Builder withTguid(String tguid) {
			this.tguid = tguid;
			return this;
		}

		public Builder withFk_user(Integer fk_user) {
			this.fk_user = fk_user;
			return this;
		}

		public Builder withTaskPar(List<TaskPar> taskPar) {
			this.taskPar = taskPar;
			return this;
		}

		public Builder withNpp(String npp) {
			this.npp = npp;
			return this;
		}

		public Builder withPriority(Integer priority) {
			this.priority = priority;
			return this;
		}

		public Builder withTrace(Integer trace) {
			this.trace = trace;
			return this;
		}

		public Task build() {
			return new Task(this);
		}
	}


}

