package com.ric.bill.dao;

import java.util.List;

import com.ric.bill.model.exs.Task;


public interface TaskDAO {

	public List<Task> getAllUnprocessed();
	public List<Task> getByTp(String tp);
	public List<Task> getByTaskAddrTp(Task task, String addrTp, String addrTpx, Integer appTp);
	public Task getByTguid(Task task, String tguid);
	public Boolean getChildAnyErr(Task task);
	public Task getByCd(String cd); // новый метод 2
}
