package com.rjt.dao;

import java.util.List;

import com.rjt.model.Leave;

public interface LeaveDao {
	public void saveOrUpdate(Leave o);
	public void delete(String id);
	public List search(Leave o, String id);
	public Leave get(String id);
}
