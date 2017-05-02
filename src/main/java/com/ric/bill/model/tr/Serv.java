package com.ric.bill.model.tr;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.EqualsAndHashCode;

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Type;

import com.ric.bill.Calc;
import com.ric.bill.Storable;
import com.ric.bill.model.bs.Base;
import com.ric.bill.model.mt.Meter;



/**
 * Услуга
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "SERV", schema="TR")
public class Serv extends Base implements java.io.Serializable, Storable {

	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; // id записи

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	@Column(name = "CD", updatable = false, nullable = false)
    private String cd;// CD 

	@Column(name = "NAME", updatable = false, nullable = true)
    private String name;// наименование 
	
	@Column(name = "NPP", updatable = false, nullable = true)
	private Integer npp;// № п.п.
	
	@Column(name = "NPP2", updatable = false, nullable = true)
	private Integer npp2;// № п.п. для распределения объема

	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "CHECK_ORG", updatable = false, nullable = true)
	private Boolean checkOrg;// проверять наличие организации в тарифе (да/нет)
	
	// услуга содержащая счётчик
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_MET", referencedColumnName="ID")
	private Serv servMet; 
	
	// услуга содержащая счётчик ОДН
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ODN", referencedColumnName="ID")
	private Serv servOdn; 

	// услуга содержащая норматив в тарифе
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ST_KEEP", referencedColumnName="ID")
	private Serv servStKeep; 

	// услуга по соцнорме
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ST", referencedColumnName="ID")
	private Serv servSt; 

	// услуга свыше соцнормы, к данной 
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_UPST", referencedColumnName="ID")
	private Serv servUpst; 

	// услуга без проживающих, к данной 
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_WOKPR", referencedColumnName="ID")
	private Serv servWokpr; 

	// услуга содержащая организацию 
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_FORG", referencedColumnName="ID")
	private Serv servOrg; 

	// основная услуга, для начисления
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_CHRG", referencedColumnName="ID")
	private Serv servChrg; 

	// виртуальная услуга
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "VRT", nullable = true)
	private Boolean vrt; 

	// услуга, на расчет которой повлияет разница от текущей и дополнительных услуг, участвующих в округлении (заполнено, если текущая - виртуальная услуга)
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_ROUND", referencedColumnName="ID")
	private Serv servRound;
	
	// виртуальная услуга, по отношению к текущей
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_VRT", referencedColumnName="ID")
	private Serv servVrt;
	
	// иерархия услуги
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_SERV", referencedColumnName="ID")
	@BatchSize(size = 50)
	private List<ServTree> servTree = new ArrayList<ServTree>(0);

	// родительская услуга, расчёт которой должен быть готов ранее текущей
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_DEP", referencedColumnName="ID")
	private Serv servDep;

	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "INCL_ABSN", nullable = true)
	private Boolean inclAbsn;// учитывать временно отсут (да / нет) при расчете услуги
	
	// учитывать временно зарегистр (да / нет) при расчете услуги
	@Type(type= "org.hibernate.type.NumericBooleanType")
	@Column(name = "INCL_PRSN", nullable = true)
	private Boolean inclPrsn;

	public String getCd() {
		return cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNpp() {
		return npp;
	}

	public void setNpp(Integer npp) {
		this.npp = npp;
	}

	public Integer getNpp2() {
		return npp2;
	}

	public void setNpp2(Integer npp2) {
		this.npp2 = npp2;
	}

	public Boolean getInclAbsn() {
		if (inclAbsn==null) {
			return false;
		} else {
			return inclAbsn;
		}
	}

	public void setInclAbsn(Boolean inclAbsn) {
		this.inclAbsn = inclAbsn;
	}

	public Boolean getInclPrsn() {
		if (inclPrsn==null) {
			return false;
		} else {
			return inclPrsn;
		}
	}

	public void setInclPrsn(Boolean inclPrsn) {
		this.inclPrsn = inclPrsn;
	}

	public Serv getServMet() {
		return servMet;
	}

	public void setServMet(Serv met) {
		this.servMet = met;
	}

	public Serv getServOdn() {
		return servOdn;
	}

	public void setServOdn(Serv odn) {
		this.servOdn = odn;
	}

	public Serv getServChrg() {
		return servChrg;
	}

	public void setServChrg(Serv chrg) {
		this.servChrg = chrg;
	}

	public Serv getServUpst() {
		return servUpst;
	}
	public void setServUpst(Serv upst) {
		this.servUpst = upst;
	}

	public Serv getServWokpr() {
		return servWokpr;
	}
	
	public void setServWokpr(Serv wokpr) {
		this.servWokpr = wokpr;
	}

	public Serv getServOrg() {
		return servOrg;
	}
	
	public void setServOrg(Serv org) {
		this.servOrg = org;
	}

	public Boolean getCheckOrg() {
		if (checkOrg==null) {
			return false;
		} else {
			return checkOrg;
		}
	}
	
	public void setCheckOrg(Boolean checkOrg) {
		this.checkOrg = checkOrg;
	}
	
	public Serv getServStKeep() {
		return servStKeep;
	}
	public void setServStKeep(Serv servStKeep) {
		this.servStKeep = servStKeep;
	}
	public Serv getServSt() {
		return servSt;
	}
	public void setServSt(Serv servSt) {
		this.servSt = servSt;
	}
	
	public Boolean getVrt() {
		if (vrt==null) {
			return false;
		} else {
			return vrt;
		}
	}
	public void setVrt(Boolean vrt) {
		this.vrt = vrt;
	}
	public Serv getServRound() {
		return servRound;
	}
	public void setServRound(Serv servRound) {
		this.servRound = servRound;
	}
	public Serv getServVrt() {
		return servVrt;
	}
	public void setServVrt(Serv servVrt) {
		this.servVrt = servVrt;
	}
		
	public List<ServTree> getServTree() {
		return servTree;
	}
	public void setServTree(List<ServTree> servTree) {
		this.servTree = servTree;
	}
	
	public Serv getServDep() {
		return servDep;
	}
	public void setServDep(Serv servDep) {
		this.servDep = servDep;
	}
	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Serv))
	        return false;

	    Serv other = (Serv)o;

	    if (id == other.getId()) return true;
	    if (id == null) return false;

	    //  equivalence by id
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

