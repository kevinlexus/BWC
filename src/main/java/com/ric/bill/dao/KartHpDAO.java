package com.ric.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ric.bill.model.hotora.scott.Kart;

@Repository
public interface KartHpDAO extends JpaRepository<Kart, String> {

	 	
}
