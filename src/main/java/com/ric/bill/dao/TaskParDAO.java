package com.ric.bill.dao;

import com.ric.bill.model.exs.TaskPar;


public interface TaskParDAO {


	public TaskPar getTask(Integer taskId, String parCd);
	
}
