package com.ric.bill.dao;

import com.ric.bill.model.exs.Task;
import com.ric.bill.model.exs.TaskPar;


public interface TaskParDAO {


	public TaskPar getTaskPar(Task task, String parCd);
	
}
