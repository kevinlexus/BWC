package com.ric.bill.model.exs;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;


/**
 * Внешние объекты, созданные в результате выполнения задания
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TASKXEOL", schema="EXS")
public class TaskEolink implements java.io.Serializable  {

	public TaskEolink() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TASKEOLINK")
	@SequenceGenerator(name="SEQ_TASKEOLINK", sequenceName="EXS.SEQ_TASKXEOL", allocationSize=1)	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// Задание
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_TASK", referencedColumnName="ID")
	private Task task;
	
	// Внешний объект, связанный с заданием
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_EOLINK", referencedColumnName="ID")
	private Eolink eolink;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public Eolink getEolink() {
		return eolink;
	}

	public void setEolink(Eolink eolink) {
		this.eolink = eolink;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof TaskEolink))
	        return false;

	    TaskEolink other = (TaskEolink)o;

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

