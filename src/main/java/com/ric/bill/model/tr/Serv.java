package com.ric.bill.model.tr;

import java.util.ArrayList;
import java.util.List;

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

import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

import com.ric.bill.CntPers;
import com.ric.bill.Storable;
import com.ric.bill.model.bs.Base;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;



/**
 * Услуга
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
////@Cache(usage=CacheConcurrencyStrategy.READ_ONLY, region="rrr1")
@Table(name = "SERV", schema="TR")
@Getter @Setter
public class Serv extends Base implements java.io.Serializable, Storable {
	
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; // id записи

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
	
	// услуга содержащая расценку
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="FK_PRICE", referencedColumnName="ID")
	private Serv servPrice;

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


/*	@Override
	public void finalize() throws Throwable {
		super.finalize();
		log.info("============================= FINALIZED Serv! ==============================");
		
	}
*/	
}

