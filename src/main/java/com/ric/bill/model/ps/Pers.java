package com.ric.bill.model.ps;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ric.bill.Calc;
import com.ric.bill.Simple;
import com.ric.bill.model.ar.House;

/**
 * Персона
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "PERS", schema="PS")
public class Pers implements java.io.Serializable, Simple {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

/*    @Column(name = "NM_LAST", updatable = false, nullable = true)
	private String lastname; //Фамилия 

    @Column(name = "NM_FIRST", updatable = false, nullable = true)
	private String firstname; //Имя 
    
    @Column(name = "NM_PATR", updatable = false, nullable = true)
	private String middlename; //Отчество 
*/
    @Column(name = "DT_BORN")
	private Date dtBorn; //дата рождения 

    public Integer getId() {
		return this.id;
	}
    
	public void setId(Integer id) {
		this.id = id;
	}
	
/*	public String getLastname() {
		return lastname;
	}
	
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	
	public String getFirstname() {
		return firstname;
	}
	
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	
	public String getMiddlename() {
		return middlename;
	}
	
	public void setMiddlename(String middlename) {
		this.middlename = middlename;
	}*/

	public Date getDtBorn() {
		return dtBorn;
	}

	public void setDtBorn(Date dtBorn) {
		this.dtBorn = dtBorn;
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof Pers))
	        return false;

	    Pers other = (Pers)o;

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

