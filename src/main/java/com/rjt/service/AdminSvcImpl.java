package com.rjt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rjt.dao.AdminDao;
import com.rjt.model.Admin;

@Service
public class AdminSvcImpl implements AdminSvc{
	@Autowired
	AdminDao dao;

	public Admin login(Admin o) {
		// TODO Auto-generated method stub
		return dao.login(o);
	}
}
