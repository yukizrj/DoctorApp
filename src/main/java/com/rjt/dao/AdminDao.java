package com.rjt.dao;

import com.rjt.model.Admin;

public interface AdminDao {
	public Admin login(Admin o);
	public void save(Admin o);
}
