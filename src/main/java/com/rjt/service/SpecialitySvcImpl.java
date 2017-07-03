package com.rjt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rjt.dao.SpecialityDao;
import com.rjt.model.Speciality;

@Service
public class SpecialitySvcImpl implements SpecialitySvc{
	@Autowired
	SpecialityDao dao;

	public void saveOrUpdate(Speciality o) {
		// TODO Auto-generated method stub
		dao.saveOrUpdate(o);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}

	public List search(Speciality o) {
		// TODO Auto-generated method stub
		return dao.search(o);
	}

	public Speciality get(String id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}
}
