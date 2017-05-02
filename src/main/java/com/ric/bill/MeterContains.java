package com.ric.bill;

import java.util.List;
import java.util.Set;

import com.ric.bill.model.mt.MLogs;
import com.ric.bill.model.mt.MeterLog;

public interface MeterContains extends Storable {
	public List<MeterLog> getMlog();
	public void setMlog(List<MeterLog> mlog);
}
