package com.ric.bill.dao.hotora;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ric.bill.dto.SumSaldoRec;
import com.ric.bill.model.hotora.scott.Saldo;
import com.ric.bill.model.hotora.scott.SaldoId;

@Repository
public interface SaldoDAO extends JpaRepository<Saldo, SaldoId> {

	/**
	 * Получить сальдо по лицевому счету
	 * @param lsk лицевой счет
	 * @param period - период
	 * @return
	 */
	@Query(value = "select sum(t.indebet) as \"indebet\", sum(t.inkredit) as \"inkredit\", "
			+ "sum(t.outdebet) as \"outdebet\", sum(t.outkredit) as \"outkredit\", sum(t.payment) as \"payment\" "
			+ "from SCOTT.XITOG2_S_LSK@HP t "
			+ "where t.lsk = :lsk and t.mg = :period",
			nativeQuery = true)
	  SumSaldoRec getSaldoByLsk(@Param("lsk") String lsk, @Param("period") String period);

}
