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

import com.ric.bill.model.bs.Lst;


/**
 * Связь заданий друг с другом
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "TASKXTASK", schema="EXS")
public class TaskToTask implements java.io.Serializable  {

	// Конструктор
	public TaskToTask() {
	}
	
	public TaskToTask(Task parent, Task child, Lst tp) {
		super();
		this.parent = parent;
		this.child = child;
		this.tp = tp;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXS")
	@SequenceGenerator(name="SEQ_EXS", sequenceName="EXS.SEQ_TASKXTASK", allocationSize=1)	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)
	private Integer id;

	// Родительский Внешний объект 
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PARENT", referencedColumnName="ID", nullable = false)
	private Task parent;
	
	// Дочерний Внешний объект
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHILD", referencedColumnName="ID", nullable = false)
	private Task child;

	// Тип связи
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_TP", referencedColumnName="ID", nullable = false)
	private Lst tp; 
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Task getParent() {
		return parent;
	}

	public void setParent(Task parent) {
		this.parent = parent;
	}

	public Task getChild() {
		return child;
	}

	public void setChild(Task child) {
		this.child = child;
	}

	public Lst getTp() {
		return tp;
	}

	public void setTp(Lst tp) {
		this.tp = tp;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof TaskToTask))
	        return false;

	    TaskToTask other = (TaskToTask)o;

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

