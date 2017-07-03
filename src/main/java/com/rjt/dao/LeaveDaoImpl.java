package com.rjt.dao;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.rjt.model.Leave;

@Service
public class LeaveDaoImpl implements LeaveDao{
	@Autowired
	SessionFactory session;
	@Transactional
	public void saveOrUpdate(Leave o) {
		// TODO Auto-generated method stub
		session.getCurrentSession().saveOrUpdate(o);
	}
	@Transactional
	public void delete(String id) {
		Leave l=get(id);
		session.getCurrentSession().delete(l);
	}
	@Transactional
	public List search(Leave o, String id) {
		// TODO Auto-generated method stub
		Map map=new LinkedHashMap();
		
		map.put("id", o.getId()==null?"":o.getId());
		map.put("doc_id", id);
	
		Query query = session.getCurrentSession().getNamedQuery("leave_search");
		System.out.println(query);
		Set set=map.entrySet();
		
		Iterator iterator=set.iterator();
		
		while(iterator.hasNext()){
			Map.Entry<String, Object> entry=(Map.Entry)iterator.next();
			
			query.setParameter(entry.getKey(), entry.getValue());
		}

		return query.list();
	}
	@Transactional
	public Leave get(String id) {
		// TODO Auto-generated method stub
		return session.getCurrentSession().get(Leave.class, Integer.parseInt(id));
	}
}
