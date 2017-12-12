package com.ric.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ric.bill.model.fn.TempLsk;
import com.ric.bill.model.fn.TempLskId;


public interface TempLskDAO extends JpaRepository<TempLsk, TempLskId> {

	 	
}
