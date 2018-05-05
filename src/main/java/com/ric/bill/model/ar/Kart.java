package com.ric.bill.model.ar;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.FilterDefs;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.ParamDef;

import com.ric.bill.MeterContains;
import com.ric.bill.TarifContains;
import com.ric.bill.model.bs.Dw;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.fn.Chrg;
import com.ric.bill.model.fn.PrivilegeChrg;
import com.ric.bill.model.mt.MeterLog;
import com.ric.bill.model.oralv.Ko;
import com.ric.bill.model.ps.Reg;
import com.ric.bill.model.ps.RegState;
import com.ric.bill.model.tr.TarifKlsk;

import lombok.Getter;
import lombok.Setter;
/**
 * Принадлежность лицевого к управляющей компаниии
 * 
 * Лев (10:57:37 26/09/2016) 
 * но это же не лицевой счет)) это таблица связей лиц.счета (которого у нас теперь нет) с УК
 * ***KnяZь'**** (10:57:59 26/09/2016) 
 * это два в одном
 * Лев (11:00:33 26/09/2016) 
 * это отступление от корректной схемы, ну ладно, фиг с ним
 * 
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "KART", schema="AR")
@FilterDefs({
//фильтр, на партицию и статус записи
@FilterDef(name = "FILTER_CHRG1", defaultCondition = "(STATUS=:STATUS AND PERIOD=:PERIOD)", 
parameters = {@ParamDef(name = "STATUS", type = "integer"),
			  @ParamDef(name = "PERIOD", type = "string")
}
)
})
@Getter @Setter
public class Kart implements java.io.Serializable, MeterContains, TarifContains, Comparable<Kart>  {  /* extends Base не может наследовать Base, так как свой FK_KLSK_OBJ*/
																								 /* пришлось сделать что метод getKlsk ссылается на klskObj из за тупости в архитектуре таблиц*/

	public Kart() {
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "lsk", updatable = false, nullable = false)
	private Integer lsk; //id записи
	
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_K_LSK", referencedColumnName="FK_KLSK_OBJ")
	@BatchSize(size = 20)
	private List<Dw> dw = new ArrayList<Dw>(0);

	// Ko (На самом деле, здесь OneToOne, но не смог реализовать, оставил так)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="ID", updatable = false, insertable = false)
	private Ko ko;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KW", referencedColumnName="ID", updatable = false, insertable = false)
	private Kw kw;

	// Обслуживающая УК
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_UK", referencedColumnName="ID", updatable = false, insertable = false)
	private Org uk;

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="FK_KLSK_OBJ")
	@BatchSize(size = 50)
	private List<MeterLog> mlog = new ArrayList<MeterLog>(0);

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="FK_KLSK_OBJ")
	@BatchSize(size = 50)
	private List<TarifKlsk> tarifklsk = new ArrayList<TarifKlsk>(0);

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="FK_KLSK_OBJ")
	//@BatchSize(size = 500)
	@Fetch(FetchMode.SUBSELECT)
	private List<Reg> reg = new ArrayList<Reg>(0);

	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_KLSK_OBJ", referencedColumnName="FK_KLSK_OBJ")
	//@BatchSize(size = 500)
	@Fetch(FetchMode.SUBSELECT)
	private List<RegState> regState = new ArrayList<RegState>(0);

	@Column(name = "FK_KW", nullable = true)
	private Integer fkKw;

	// Лиц.счет из квартплаты - для поиска
	@Column(name = "FLSK", nullable = true, updatable = false, insertable = false)
	private String flsk;

	// Записи начисления
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false)
//	@BatchSize(size = 50)
	@Fetch(FetchMode.SUBSELECT)
	@Filters({
	    @Filter(name = "FILTER_CHRG1")})
	private List<Chrg> chrg = new ArrayList<Chrg>(0);

	// Записи возмешений по льготе
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval=true)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false)
	@Fetch(FetchMode.SUBSELECT)
	private List<PrivilegeChrg> privilegeChrg = new ArrayList<PrivilegeChrg>(0);

	// даты начала и окончания обслуживания лиц.счета
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;

    /**
     * Сравнить лицевые счета
     */
	@Override
	public int compareTo(Kart other) {
		return Integer.compare(this.lsk, other.lsk);
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Kart))
	        return false;

	    Kart other = (Kart)o;

	    if (lsk == other.getLsk()) return true;
	    if (lsk == null) return false;

	    // equivalence by id
	    return lsk.equals(other.getLsk());
	}

	public int hashCode() {
	    if (lsk != null) {
	        return lsk.hashCode();
	    } else {
	        return super.hashCode();
	    }
	}

	
}

