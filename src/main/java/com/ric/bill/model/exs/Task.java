package com.ric.bill.model.exs;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;

import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.mt.Vol;


/**
 * Задание на выполнение обмена с ГИС ЖКХ
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TASK", schema="EXS")
public class Task implements java.io.Serializable  {

	public Task() {
	}

	@Id
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// Связь с внешним объектом
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private Eolink eolink;
	
	// Родительское задание
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PARENT_ID", referencedColumnName="ID")
	private Task parentTask; 
	
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

	// Дата обновления
	@Column(name = "DT1")
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
	
	public Date getUpdDt() {
		return updDt;
	}

	public void setUpdDt(Date updDt) {
		this.updDt = updDt;
	}

	public String getUn() {
		return un;
	}

	public void setUn(String un) {
		this.un = un;
	}

	public Lst getAct() {
		return act;
	}

	public void setAct(Lst act) {
		this.act = act;
	}

	public Task getParentTask() {
		return parentTask;
	}

	public void setParentTask(Task parentTask) {
		this.parentTask = parentTask;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Eolink getEolink() {
		return eolink;
	}

	public void setEolink(Eolink eolink) {
		this.eolink = eolink;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getGuid() {
		return guid;
	}

	public void setGuid(String guid) {
		this.guid = guid;
	}

	public String getTguid() {
		return tguid;
	}

	public void setTguid(String tguid) {
		this.tguid = tguid;
	}

	public Integer getAppTp() {
		return appTp;
	}

	public void setAppTp(Integer appTp) {
		this.appTp = appTp;
	}

	public Integer getFk_user() {
		return fk_user;
	}

	public void setFk_user(Integer fk_user) {
		this.fk_user = fk_user;
	}

	public String getMsgGuid() {
		return msgGuid;
	}

	public void setMsgGuid(String msgGuid) {
		this.msgGuid = msgGuid;
	}

	public List<TaskPar> getTaskPar() {
		return taskPar;
	}

	public void setTaskPar(List<TaskPar> taskPar) {
		this.taskPar = taskPar;
	}

	public Task getDepTask() {
		return depTask;
	}

	public void setDepTask(Task depTask) {
		this.depTask = depTask;
	}
	
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

