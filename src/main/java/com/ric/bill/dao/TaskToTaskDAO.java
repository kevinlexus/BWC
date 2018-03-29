package com.ric.bill.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ric.bill.model.exs.TaskToTask;
import com.ric.bill.model.sec.User;

public interface TaskToTaskDAO extends JpaRepository<TaskToTask, Integer> {
	
}
