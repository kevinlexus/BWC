package com.ric.bill.model.fn;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.ric.bill.Simple;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;
import javax.annotation.Generated;
import java.util.Collections;

/**
 * Заголовочная таблица - перерасчеты
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "CHNG", schema="FN")
@Getter @Setter
public class Chng implements java.io.Serializable, Simple {

	public Chng() {
		
	}

	@Id
    @Column(name = "ID", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHNG")
	@SequenceGenerator(name="SEQ_CHNG", sequenceName="FN.SEQ_CHNG", allocationSize=1) 	
	private Integer id;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<Chrg> chrg = new ArrayList<Chrg>(0);

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID")
	@Fetch(FetchMode.SUBSELECT)
	private List<ChngLsk> chngLsk = new ArrayList<ChngLsk>(0);
	
	// текущий период для партицирования
	@Column(name = "PERIOD")
	private Integer period;

	// период перерасчета
	@Column(name = "MG")
	private Integer mg;

	// документ-основание перерасчета
	@Column(name = "ACT_NAME")
	private String actName;

	// даты начала и окончания произведенного перерасчета
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;
	
	// Тип перерасчета (Корректировка показаний ИПУ, Недопоставка услуги, Изменение расценки (тарифа))
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG_TP", referencedColumnName="ID")
	private Lst tp; 
	
	// Организация по перерасчету (для замены)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
	private Org org; 
	
	// Главная услугу по перерасчету
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID")
	private Serv serv;

	@Generated("SparkTools")
	private Chng(Builder builder) {
		this.id = builder.id;
		this.chrg = builder.chrg;
		this.chngLsk = builder.chngLsk;
		this.period = builder.period;
		this.mg = builder.mg;
		this.actName = builder.actName;
		this.dt1 = builder.dt1;
		this.dt2 = builder.dt2;
		this.tp = builder.tp;
		this.org = builder.org;
		this.serv = builder.serv;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Chng))
	        return false;

	    Chng other = (Chng)o;

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

	/**
	 * Creates builder to build {@link Chng}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Chng}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer id;
		private List<Chrg> chrg = Collections.emptyList();
		private List<ChngLsk> chngLsk = Collections.emptyList();
		private Integer period;
		private Integer mg;
		private String actName;
		private Date dt1;
		private Date dt2;
		private Lst tp;
		private Org org;
		private Serv serv;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withChrg(List<Chrg> chrg) {
			this.chrg = chrg;
			return this;
		}

		public Builder withChngLsk(List<ChngLsk> chngLsk) {
			this.chngLsk = chngLsk;
			return this;
		}

		public Builder withPeriod(Integer period) {
			this.period = period;
			return this;
		}

		public Builder withMg(Integer mg) {
			this.mg = mg;
			return this;
		}

		public Builder withActName(String actName) {
			this.actName = actName;
			return this;
		}

		public Builder withDt1(Date dt1) {
			this.dt1 = dt1;
			return this;
		}

		public Builder withDt2(Date dt2) {
			this.dt2 = dt2;
			return this;
		}

		public Builder withTp(Lst tp) {
			this.tp = tp;
			return this;
		}

		public Builder withOrg(Org org) {
			this.org = org;
			return this;
		}

		public Builder withServ(Serv serv) {
			this.serv = serv;
			return this;
		}

		public Chng build() {
			return new Chng(this);
		}
	}
	
}

