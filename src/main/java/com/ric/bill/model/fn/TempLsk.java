package com.ric.bill.model.fn;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Immutable;

import lombok.Getter;
import lombok.Setter;

/**
 * Лицевой, для осуществления расчета начисления
 * @author lev
 *
 */
@Entity
@Immutable
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="rrr1")
@Table(name = "TEMP_LSK", schema="FN")
@IdClass(TempLskId.class) // суррогантый первичный ключ
@Setter
@Getter
public class TempLsk implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @Column(name = "FK_ID")			
	private Integer fkId;
	
	@Id
    @Column(name = "LSK")			
	private Integer lsk;

}

