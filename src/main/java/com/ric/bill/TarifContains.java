package com.ric.bill;

import java.util.List;
import java.util.Set;

import com.ric.bill.model.tr.TarifKlsk;

public interface TarifContains extends Storable {

	public List<TarifKlsk> getTarifklsk();
	public void setTarifklsk(List<TarifKlsk> tarifklsk);

}
