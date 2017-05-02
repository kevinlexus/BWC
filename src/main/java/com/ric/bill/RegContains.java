package com.ric.bill;

import java.util.List;
import java.util.Set;

import com.ric.bill.model.ps.Reg;
import com.ric.bill.model.ps.RegState;

/**
 * Интерфейс объектов содержащих регистрации проживающих
 * @author lev
 *
 */
public interface RegContains extends Storable {

	public List<Reg> getReg();
	public void setReg(List<Reg> reg);
	public List<RegState> getRegState();
	public void setRegState(List<RegState> regState);

}
