package com.rjt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.rjt.model.Appointment;

public interface AppointmentSvc {
	public Appointment saveOrUpdate(Appointment o);
	public void saveOrUpdateReview(Appointment o);
	public void delete(String id);
	public List search(HttpServletRequest request);
	public Appointment get(String id);
	public Object browse(HttpServletRequest request);
}
