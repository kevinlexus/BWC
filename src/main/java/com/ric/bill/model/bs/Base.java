package com.ric.bill.model.bs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.Storable;
import com.ric.bill.model.oralv.Ko;

/**
 * Базовый класс всех сущностей, параметры которых хранятся в dw
 * @author lev
 * @version 1.00
 *
 */
@MappedSuperclass
@EqualsAndHashCode(callSuper=false)
@Getter @Setter
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
	
	
}
