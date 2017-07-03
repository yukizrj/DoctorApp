package com.rjt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.rjt.dao.LeaveDao;
import com.rjt.model.Leave;

@Service
public class LeaveSvcImpl implements LeaveSvc{
	@Autowired
	LeaveDao dao;

	public void saveOrUpdate(Leave o) {
		// TODO Auto-generated method stub
		dao.saveOrUpdate(o);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.delete(id);
	}

	public List search(Leave o, String id) {
		// TODO Auto-generated method stub
		return dao.search(o, id);
	}

	public Leave get(String id) {
		// TODO Auto-generated method stub
		return dao.get(id);
	}
}
