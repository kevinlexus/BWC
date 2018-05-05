package com.ric.bill.model.mt;


import java.util.Date;

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
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import com.ric.bill.Simple;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.fn.Chng;
import com.ric.bill.model.sec.User;

import lombok.Getter;
import lombok.Setter;
import javax.annotation.Generated;

/**
 * Объемы счетчика 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="rrr1")
@Table(name = "METER_VOL", schema="MT")
@Getter @Setter
public class Vol implements java.io.Serializable, Simple {

	public Vol (){
		
	}

/*	public Vol (MeterLog ml, Lst tp, Double vol1, Double vol2, Date dt1, Date dt2, Integer status, Chng chng){
		setMLog(ml);
		setTp(tp);
		setVol1(vol1);
		setVol2(vol2);
		setDt1(dt1);
		setDt2(dt2);
		setStatus(status);
		setChng(chng);
	}

*/	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VOL")
	@SequenceGenerator(name="SEQ_VOL", sequenceName="MT.SEQ_METER_VOL", allocationSize=10) //делал allocationSize=100 тогда надо increment by делать 100, работает быстрее, на 10%.. но тогда гэп большой от других инсертов 	
    @Column(name = "ID", unique=true, updatable = false, nullable = false)					
	private Integer id;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="FK_TP", referencedColumnName="ID")
	private Lst tp; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_METER_LOG", referencedColumnName="ID")
	private MeterLog mLog; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_METER", referencedColumnName="ID")
	private Meter met; 

	@Column(name = "VOL1", updatable = true, nullable = true)
	private Double vol1; 

    @Column(name = "VOL2", updatable = true, nullable = true)
	private Double vol2; 

    // даты начала и окончания произведенного объема
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;

	// Статус, 0 - текущий расчет, 1 - перерасчет
	@Column(name = "STATUS", nullable = true)
	private Integer status;
	
	// Перерасчет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID")
	private Chng chng; 

	// Пользователь создавший запись
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_USER", referencedColumnName="ID")
	private User user;

	@Generated("SparkTools")
	private Vol(Builder builder) {
		this.id = builder.id;
		this.tp = builder.tp;
		this.mLog = builder.mLog;
		this.met = builder.met;
		this.vol1 = builder.vol1;
		this.vol2 = builder.vol2;
		this.dt1 = builder.dt1;
		this.dt2 = builder.dt2;
		this.status = builder.status;
		this.chng = builder.chng;
		this.user = builder.user;
	}
	
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Vol))
	        return false;

	    Vol other = (Vol)o;

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
	 * Creates builder to build {@link Vol}.
	 * @return created builder
	 */
	@Generated("SparkTools")
	public static Builder builder() {
		return new Builder();
	}

	/**
	 * Builder to build {@link Vol}.
	 */
	@Generated("SparkTools")
	public static final class Builder {
		private Integer id;
		private Lst tp;
		private MeterLog mLog;
		private Meter met;
		private Double vol1;
		private Double vol2;
		private Date dt1;
		private Date dt2;
		private Integer status;
		private Chng chng;
		private User user;

		private Builder() {
		}

		public Builder withId(Integer id) {
			this.id = id;
			return this;
		}

		public Builder withTp(Lst tp) {
			this.tp = tp;
			return this;
		}

		public Builder withMLog(MeterLog mLog) {
			this.mLog = mLog;
			return this;
		}

		public Builder withMet(Meter met) {
			this.met = met;
			return this;
		}

		public Builder withVol1(Double vol1) {
			this.vol1 = vol1;
			return this;
		}

		public Builder withVol2(Double vol2) {
			this.vol2 = vol2;
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

		public Builder withStatus(Integer status) {
			this.status = status;
			return this;
		}

		public Builder withChng(Chng chng) {
			this.chng = chng;
			return this;
		}

		public Builder withUser(User user) {
			this.user = user;
			return this;
		}

		public Vol build() {
			return new Vol(this);
		}
	}

	
}

