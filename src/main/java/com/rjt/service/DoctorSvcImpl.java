package com.rjt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rjt.dao.DoctorDao;
import com.rjt.model.Doctor;

@Service
public class DoctorSvcImpl implements DoctorSvc{
	@Autowired
	DoctorDao dao;

	public Doctor login(Doctor o) {
		// TODO Auto-generated method stub
		return dao.login(o);
	}
	
	public void saveOrUpdate(Doctor o) {
		// TODO Auto-generated method stub
		dao.saveOrUpdate(o);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}

	public List search(Doctor o) {
		// TODO Auto-generated method stub
		return dao.search(o);
	}

	public Doctor get(String id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	public void schedule(String id, String[] schedule) {
		// TODO Auto-generated method stub
		/*dao.schedule(id, schedule);*/
	}
}
