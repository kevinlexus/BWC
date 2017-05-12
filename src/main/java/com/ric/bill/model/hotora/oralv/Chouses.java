package com.ric.bill.model.hotora.oralv;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Справочник домов из hotora oralv.c_houses
 * @author lev
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "C_HOUSES@HP", schema="ORALV")
public class Chouses implements java.io.Serializable  {

	public Chouses() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_EXS")
	@SequenceGenerator(name="SEQ_EXS", sequenceName="EXS.SEQ_BASE", allocationSize=1)	
    @Column(name = "id", unique=true, updatable = false, nullable = false)
	private Integer id;

	// REU
	@Column(name = "REU", updatable = false, nullable = true)
	private String reu;
	
	// KUL
	@Column(name = "KUL", updatable = false, nullable = true)
	private String kul;

	// ND
	@Column(name = "ND", updatable = false, nullable = true)
	private String nd;
	
	// Общая площадь
	@Column(name = "OPL", updatable = false, nullable = true)
	private Double opl;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReu() {
		return reu;
	}

	public void setReu(String reu) {
		this.reu = reu;
	}

	public String getKul() {
		return kul;
	}

	public void setKul(String kul) {
		this.kul = kul;
	}

	public String getNd() {
		return nd;
	}

	public void setNd(String nd) {
		this.nd = nd;
	}

	public Double getOpl() {
		return opl;
	}

	public void setOpl(Double opl) {
		this.opl = opl;
	}
	
}

