package com.ric.bill.dao.hotora;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ric.bill.model.hotora.scott.Saldo;
import com.ric.bill.model.hotora.scott.SaldoId;

@Repository
public interface SaldoDAO extends JpaRepository<Saldo, SaldoId> {

	/**
	 * Получить совокупное сальдо по лицевому счету
	 * @param lsk лицевой счет
	 * @param period - период 
	 * @return
	 */
	@Query("select sum(t.summa) from Saldo t "
			+ "where t.kart.lsk = ?1 and t.mg = ?2")
	  BigDecimal getAmntByLsk(String lsk, String period);
	
}
