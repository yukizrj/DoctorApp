package com.rjt.service;

import java.util.List;

import com.rjt.model.Doctor;

public interface DoctorSvc {
	public Doctor login(Doctor o);
	public void saveOrUpdate(Doctor o);
	public void delete(String id);
	public List search(Doctor o);
	public Doctor get(String id);
	public void schedule(String id, String[] schedule);
}
