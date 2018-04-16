package com.ric.bill.model.fn;


import java.math.BigDecimal;
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
import com.ric.bill.model.ar.Kart;
import com.ric.bill.model.bs.Lst;
import com.ric.bill.model.bs.Org;
import com.ric.bill.model.tr.Serv;

import lombok.Getter;
import lombok.Setter;

/**
 * Результат начисления
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
//@Cache(usage=CacheConcurrencyStrategy.READ_WRITE, region="rrr1")
@Table(name = "CHRG", schema="FN")
@Getter @Setter
public class Chrg implements java.io.Serializable, Simple {


	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHRG")
	@SequenceGenerator(name="SEQ_CHRG", sequenceName="FN.SEQ_CHRG", allocationSize=10)
    @Column(name = "ID", unique=true, updatable = false, nullable = false)			
	private Integer id;
	
    //даты начала и окончания произведенного начисления
    @Column(name = "DT1", updatable = false, nullable = true)
    private Date dt1;

    @Column(name = "DT2", updatable = false, nullable = true)
    private Date dt2;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="LSK", referencedColumnName="LSK", updatable = false)
	private Kart kart;

	//Статус, 0 - архивная запись, 1-текущее начисление, 2 - подготовка к архиву
	@Column(name = "status", nullable = true)
	private Integer status;

	@Column(name = "LSK", updatable = false, insertable = false)
	private Integer lsk;

	@Column(name = "PERIOD")
	private String period;

	@Column(name = "SUM_FULL")
	private Double sumFull;
	
	@Column(name = "SUM_PREF")
	private Double sumPref;

	@Column(name = "SUM_AMNT")
	private Double sumAmnt;

	@Column(name = "VOL")
	private Double vol;
	
	@Column(name = "PRICE")
	private Double price;

	@Column(name = "STDT")
	private Double stdt;
	
	// Фактическое кол-во проживающих на дату расчета
	@Column(name = "CNTPERS")
	private Integer cntFact; 

	// Кол-во собственников на дату расчета
	@Column(name = "CNTPERS2")
	private Integer cntOwn; 

	// Площадь на дату расчета
	@Column(name = "AREA")
	private Double area;

	// Наличие счетчика на дату расчета: 1 - имеется, null, 0 - нет
	@Column(name = "MET")
	private Integer met;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHRG_TP", referencedColumnName="ID")
	private Lst tp; 

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID")
	private Serv serv; 
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ORG", referencedColumnName="ID")
	private Org org; 

	// перерасчет
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHNG", referencedColumnName="ID")
	private Chng chng; 

	// номер ввода
	@Column(name = "ENTRY")
	private Integer entry;
	
    // default конструктор
	public Chrg() {
		
	}
			
	// конструктор для окончательно рассчитанных данных (умноженная расценка на объем)
	public Chrg(Kart kart, Serv serv, Org org, int status, String period,
			BigDecimal sumFull, BigDecimal sumPref, BigDecimal sumAmnt, BigDecimal vol,
			BigDecimal price, BigDecimal stdt, Integer cntFact, BigDecimal area, Lst tp, 
			Chng chng, Integer met, Integer entry, Date dt1, Date dt2, Integer cntOwn) {
		
		setKart(kart);
		setOrg(org);
		setServ(serv);
		setStatus(status);
		setPeriod(period);
		setSumFull(sumFull.doubleValue());
		setSumPref(sumPref.doubleValue());
		setSumAmnt(sumAmnt.doubleValue());
		if (vol!=null) {
			setVol(vol.doubleValue());
		}
		if (price!=null) {
			setPrice(price.doubleValue());
		}
		if (stdt != null) {
			setStdt(stdt.doubleValue());
		}
		setCntFact(cntFact);
		setCntOwn(cntOwn);
		setTp(tp);
		if (area != null) {
			setArea(area.doubleValue());
		}
		setDt1(dt1);
		setDt2(dt2);
		setChng(chng);
		setMet(met);
		setEntry(entry);
	}

	// конструктор для подготовительных данных, рассчитанных в потоке
	public Chrg(Kart kart, Serv serv, Org org, int status, String period,
			Double sumFull, Double sumPref, Double sumAmnt, Double vol,
			Double price, Double stdt, Integer cntFact, Double area, Lst tp, 
			Date dt1, Date dt2, Integer met, Integer entry, Chng chng, Integer cntOwn) {
		
		setKart(kart);
		setOrg(org);
		setServ(serv);
		setStatus(status);
		setPeriod(period);
		setSumFull(sumFull);
		setSumPref(sumPref);
		setSumAmnt(sumAmnt);
		setVol(vol);
		setPrice(price);
		setStdt(stdt);
		setCntFact(cntFact);
		setCntOwn(cntOwn);
		setTp(tp);
		setArea(area);
		setDt1(dt1);
		setDt2(dt2);
		setChng(chng);
		setMet(met);
		setEntry(entry);
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Chrg))
	        return false;

	    Chrg other = (Chrg)o;

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

