package com.rjt.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rjt.dao.AppointmentDao;
import com.rjt.model.Appointment;

@Service
public class AppointmentSvcImpl implements AppointmentSvc{
	@Autowired
	AppointmentDao dao;

	public Appointment saveOrUpdate(Appointment o) {
		// TODO Auto-generated method stub
		return dao.saveOrUpdate(o);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}

	public List search(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.search(request);
	}

	public Appointment get(String id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}

	public Object browse(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.browse(request);
	}

	public void saveOrUpdateReview(Appointment o) {
		// TODO Auto-generated method stub
		dao.saveOrUpdateReview(o);
	}
}
