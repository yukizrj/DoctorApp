package com.rjt.dao;

import java.util.List;
import java.util.Map;

import com.rjt.model.Day;
import com.rjt.model.Doctor;

public interface DoctorDao {
	public Doctor login(Doctor o);
	public void saveOrUpdate(Doctor o);
	public void delete(String id);
	public List search(Doctor o);
	public List searchByName(Doctor o);
	public Doctor get(String id);
	public Map<Integer, Day> schedule(String id, String schedule);
	public Doctor getByLicense(String license);
}
