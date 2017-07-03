package com.rjt.service;

import java.util.List;

import com.rjt.model.Speciality;

public interface SpecialitySvc {
	public void saveOrUpdate(Speciality o);
	public void delete(String id);
	public List search(Speciality o);
	public Speciality get(String id);
}
