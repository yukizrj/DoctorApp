package com.rjt.dao;

import java.util.List;

import com.rjt.model.Speciality;

public interface SpecialityDao {
	public void saveOrUpdate(Speciality o);
	public void delete(String id);
	public List search(Speciality o);
	public Speciality get(String id);
	public Speciality getSpecialityByName(String name);
}
