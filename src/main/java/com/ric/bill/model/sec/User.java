package com.ric.bill.model.sec;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.ric.bill.Simple;

import lombok.Getter;
import lombok.Setter;

/**
 * Пользователь
 * 
 *
 */
@SuppressWarnings("serial")
@Entity
@Table(name = "T_USER", schema="SEC")
@Getter @Setter
public class User implements java.io.Serializable, Simple {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", updatable = false, nullable = false)
	private Integer id; //id

    @Column(name = "CD")
	private String cd; // CD пользователя 

    @Column(name = "IP")
	private String ip; // Ip 
    
	public User() {
		super();
	}

	public boolean equals(Object o) {
	    if (this == o) return true;
	    if (o == null || !(o instanceof User))
	        return false;

	    User other = (User)o;

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

