package com.rjt.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rjt.dao.PatientDao;
import com.rjt.model.Appointment;
import com.rjt.model.Doctor;
import com.rjt.model.Patient;

@Service
public class PatientSvcImpl implements PatientSvc{
	@Autowired
	PatientDao dao;

	public Patient login(Patient o) {
		// TODO Auto-generated method stub
		return dao.login(o);
	}
	
	public void saveOrUpdate(Patient o) {
		// TODO Auto-generated method stub
		dao.saveOrUpdate(o);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}

	public List search(Patient o) {
		// TODO Auto-generated method stub
		return dao.search(o);
	}

	public Patient get(String id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	public Patient save(Patient o) {
		// TODO Auto-generated method stub
		return dao.save(o);
	}

	
	public Object displaytable(Date dt, Doctor doctor) {
		// TODO Auto-generated method stub
		return dao.displaytable(dt, doctor);
	}

	public List<Appointment> listOfPatientByDoctor(Doctor doctor) {
		// TODO Auto-generated method stub
		return dao.listOfPatientByDoctor(doctor);
	}

	/*public void schedule(String id, String[] schedule) {
		// TODO Auto-generated method stub
		dao.schedule(id, schedule);
	}*/
}
