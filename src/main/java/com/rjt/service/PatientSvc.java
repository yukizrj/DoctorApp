package com.rjt.service;

import java.util.Date;
import java.util.List;

import com.rjt.model.Appointment;
import com.rjt.model.Doctor;
import com.rjt.model.Patient;

public interface PatientSvc {
	public Patient login(Patient o);
	public void saveOrUpdate(Patient o);
	public Patient save(Patient o);
	public void delete(String id);
	public List search(Patient o);
	public Patient get(String id);
	
	public Object displaytable(Date dt, Doctor doctor);
	public List<Appointment> listOfPatientByDoctor(Doctor doctor);
}
