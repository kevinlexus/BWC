package com.ric.bill.model.bs;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.EqualsAndHashCode;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.ric.bill.Storable;
import com.ric.bill.model.ar.Kw;
import com.ric.bill.model.oralv.Ko;

/**
 * Базовый класс всех сущностей, параметры которых хранятся в dw
 * @author lev
 *
 */
@MappedSuperclass
@EqualsAndHashCode(callSuper=false)
public abstract class Base implements Storable {
	
    @Column(name = "FK_K_LSK", updatable = false, nullable = false)
	protected Integer klskId;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_K_LSK", referencedColumnName="FK_K_LSK")
	//@BatchSize(size = 20)
	@Fetch(FetchMode.SUBSELECT)
	protected List<Dw> dw = new ArrayList<Dw>(0);

	// Ko (На самом деле, здесь OneToOne, но не смог реализовать, оставил так)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_K_LSK", referencedColumnName="ID", updatable = false, insertable = false)
	private Ko ko;
	
	public List<Dw> getDw() {
		return dw;
	}
	public void setDw(List<Dw> dw) {
		this.dw = dw;
	}
	
	public Integer getKlskId() {
		return klskId;
	}
	
	public void setKlskId(Integer klsk) {
		this.klskId = klsk;
	}
	
	public Ko getKo() {
		return ko;
	}
	
	public void setKo(Ko klsk) {
		this.ko = klsk;
	}
	
	
}
