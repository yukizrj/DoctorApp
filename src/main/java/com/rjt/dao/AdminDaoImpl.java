package com.rjt.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjt.model.Admin;

@Service
public class AdminDaoImpl implements AdminDao{
	
	@Autowired
	SessionFactory session;
	@Transactional
	public Admin login(Admin o) {
		// TODO Auto-generated method stub
		Admin temp=session.getCurrentSession().get(Admin.class, o.getUsername());
		if(temp!=null && temp.getPassword().equals(o.getPassword())){
			return temp;
		}
		
		return null;
	}
	@Transactional
	public void save(Admin o) {
		// TODO Auto-generated method stub
		session.getCurrentSession().saveOrUpdate(o);
		
	}
}
