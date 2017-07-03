package com.rjt.dao;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjt.model.Day;
@Service
public class DayDaoImpl implements DayDao{

	@Autowired
	SessionFactory session;
	
	@Transactional
	public Day get(String id) {
		return session.getCurrentSession().get(Day.class, Integer.parseInt(id));
	}

}
